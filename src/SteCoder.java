import ij.ImagePlus;
import ij.gui.Roi;
import ij.io.FileSaver;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SteCoder implements ActionListener {
	public CoverImage cover;			// la cover su cui lavorare
	private HiddenMessage message;		// il messaggio da applicare all'interno
	private ImageProcessor ip;
	private ImageProperty stegoProperty;

	public SteCoder(CoverImage cover,HiddenMessage msg,ImageProperty steProperty){
		this.message=msg;
		this.cover=cover;
		this.stegoProperty=steProperty;
		}

	private void makeThumbnail(){
		//MAKE THUMBNAIL
		ImageProcessor thumb = this.cover.getProcessor();
		int newWidth = thumb.getWidth()/2;
		int newHeight = thumb.getHeight()/2;
		thumb=thumb.resize(newWidth,newHeight);

		//setto il nuovo processore
		this.cover.setProcessor(thumb);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(this.message.getText());
		String stegoMessage=this.message.getText();
		//this.makeThumbnail();
		if(this.message.getText()=="")
			this.message.setText("I'm Batman");

		this.ip=this.cover.getProcessor();

		LSBembedding cod = new LSBembedding(this.ip,this.cover.getImageProperty().getStegoProperty(),this.message.getText());
		ImageProcessor stego = cod.getProcessor();

		/* only demo */
		double minX=stego.getWidth()/4 + Math.random()*stego.getWidth()/3;
		double minY=stego.getHeight()/4 + Math.random()*stego.getHeight()/3;
		double maxH=100 + Math.random()*stego.getHeight()/2+this.message.getText().length()*8;
		double maxW=100 + Math.random()*stego.getWidth()/2+this.message.getText().length()*8;

		Roi roi=new Roi(minX,minY,maxH,maxW);
		stego.setRoi(roi);
		ImageProcessor cropped = stego.crop();
		ImagePlus demo = new ImagePlus();
		demo.setProcessor(cropped);
		FileSaver saver=new FileSaver(demo);
		saver.saveAsTiff("/home/seppho/lenas/cropped_lena.tif");
		// -------------

		this.cover.setProcessor(stego);
		// only demo
		ImagePlus imp=new ImagePlus();
		imp.setProcessor(stego);
		FileSaver save_stego=new FileSaver(imp);
		save_stego.saveAsTiff("/home/seppho/lenas/stego_lena.tif");
		// --------------
	}

}
