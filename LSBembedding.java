

import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.plugin.frame.PlugInFrame;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



public class LSBembedding extends LSBstego implements ActionListener{
	String stego_message;
	public LSBembedding(ImageProcessor ip,int []prop,String stego_message){
		super(ip);
		this.setupPixels();
		
		this.property=new int[3];
		if(prop==null){
			property[0]=33;
			property[1]=33;
			property[2]=33;
		}else{
			property[0]=prop[0];
			property[1]=prop[1];
			property[2]=prop[2];
		}
		this.stego_message=stego_message;
		
		this.codify(property, stego_message);
		System.out.println("done");
	}

	protected void setupPixels(){
		//setup pixels pixels, hat, header
		if(PixelStructure.PROD)
			System.out.println("+ setup pixels for codify, searching hat.. (•̀ᴗ•́)و ̑̑");
		int messageLength=0;

		if(pixels != null){
			// exists the structure
			debug("Exist pixel structure, I need to reflesh\n");
			for(int i=0;i<height*width;i++){
				red_channel[i]=pixels[i].get_red();
				green_channel[i]=pixels[i].get_green();
				blue_channel[i]=pixels[i].get_blue();
			}
		}else{
			pixels = new Pixel[height*width];
			for(int i=0;i<height*width;i++){
				pixels[i]=new Pixel(red_channel[i],green_channel[i],blue_channel[i],i);
				if(i!=0){
					pixels[i].setPrev(pixels[i-1]);
					pixels[i-1].setNext(pixels[i]);
				}
			}
		}
		//this.searchHat();
	}

	public void reflesh(){
		for(int i=0;i<this.height*this.width;i++){
			this.red_channel[i]=this.pixels[i].get_red();
			this.green_channel[i]=this.pixels[i].get_green();
			this.blue_channel[i]=this.pixels[i].get_blue();
		}
	}

	public void codify(int[] property,String message){
		//setupPixels();

		int capacity=LSBstego.capacity(property[0]);
		int robustness=LSBstego.robustness(property[1],this.height*this.width,message.length());
		int security=LSBstego.security(property[2]);
		int gap=message.length()*8+LSBstego.HEADER_SIZE;

		this.Hats = new Hat();
		if(PixelStructure.PROD)
			System.out.println("Start codify with (cap,rob,sec)=("+property[0]+","+property[1]+","+property[2]+")");

		int p_index=LSBstego.START;
		Header h;
		if(PixelStructure.PROD)
			System.out.print("Writing.. ");

		for(int i=0;i<robustness && p_index<((this.height*this.width)-2*(LSBstego.METADATA_SIZE+message.length()));i++){

			this.write(LSBstego.METATADA_LSB, LSBstego.HAT, p_index);

			//refleshing
			this.pixels[p_index+LSBstego.HAT_SIZE].setLSB(LSBstego.METATADA_LSB, 8, property[0]<<24);
			this.pixels[p_index+LSBstego.HAT_SIZE+1].setLSB(LSBstego.METATADA_LSB, 24, property[1]<<24);
			this.pixels[p_index+LSBstego.HAT_SIZE+2].setLSB(LSBstego.METATADA_LSB, 24, property[2]<<24);
			this.pixels[p_index+LSBstego.HAT_SIZE+3].setLSB(LSBstego.METATADA_LSB, 24, message.length()<<24);

			h = new Header(this.pixels,p_index+LSBstego.HAT_SIZE);
			h.setProperty(property);
			h.setMessageLength(message.length());

			if(!h.isCorrupt()){
				if(PixelStructure.DEBUG)System.out.println((i+1)+") inserting new header at "+p_index+" ==> ok");
				// TODO pixel hat is not only 1 pixel now
				Hats.insert(new PixelHat(this.pixels[p_index]), h);

				this.write(capacity,message,p_index+LSBstego.HEADER_SIZE+LSBstego.HAT_SIZE);
			}else
				if(PixelStructure.DEBUG)System.out.println((i+1)+") inserting new header at "+p_index+" ==> corrupted");
			p_index+=gap;
		}

		if(PixelStructure.PROD)
			System.out.println("Inserted correctly "+this.Hats.length()+" headers into the image");
	this.reflesh();
	}


	public ColorProcessor getProcessor(){
		ColorProcessor cp=(ColorProcessor)this.processor;
		cp.setRGB(red_channel, green_channel, blue_channel);
		return cp;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("uhm,,");
		for(int i=0;i<3;i++){
			System.out.println(this.property[i]);
			this.property[i]+=1;
		}
	}
}
