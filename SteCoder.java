package steganography;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SteCoder implements ActionListener {
	public CoverImage cover;			// la cover su cui lavorare
	private HiddenMessage message;		// il messaggio da applicare all'interno
	
	public SteCoder(CoverImage cover,HiddenMessage msg,int []stegoProperty){
		this.message=msg;
		this.cover=cover;
		
		int capacity=stegoProperty[ImageProperty.CAPACITY];
		int robustness=stegoProperty[ImageProperty.ROBUSTNESS];
		int security=stegoProperty[ImageProperty.SECURITY];
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
		
		this.makeThumbnail();
		this.cover.showImageInfo();
	}

}
