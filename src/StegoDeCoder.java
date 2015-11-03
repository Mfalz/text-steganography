import ij.IJ;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class StegoDeCoder implements ActionListener{
	private HiddenMessage message;
	private ImageProcessor ip;
	private ImageProperty stegoProperty;
	CoverImage cover;
	
	private ColorProcessor colorProcessor;
	int capacity;
	private int robustness;
	private int security;
	private ImageProcessor processor;
	private int height;
	private int width;
	private byte[] red,green,blue;
	int header_start;
	
	public StegoDeCoder(CoverImage cover,HiddenMessage textBox){
		//System.out.println("\n+++++ new StegoDeCoder(cover,hiddenMessage)");
		this.cover=cover;
		this.message=textBox;
		this.ip=cover.getProcessor();
		
		LSBdecoding decoded = new LSBdecoding(this.ip);
		if(decoded.hasMessage()){
			System.out.println("Found side effect of steganography");
			String text=decoded.getStegoMessage();
			cover.setProperty(decoded.getProperty());
			this.message.setText(text);
		}else
			System.out.println("This is image is empty");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//BufferedImage image=this.cover.coverImage;
		//System.out.println("Ok si dovrebbe cominciare la decodifica");		
	}

}
