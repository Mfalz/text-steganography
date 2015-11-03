

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.processing.FilerException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Save implements ActionListener {
	CoverImage cover;
	public Save(CoverImage c){
		this.cover=c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// prendo l'immagine bufferizzata dal processor corrente
		BufferedImage img = this.cover.getProcessor().getBufferedImage();
		ImagePlus ip = new ImagePlus("stego_lena.tif",this.cover.getProcessor());
		ip.show();
				
		//chiedo l'input
	    //JFrame dialogFrame = new JFrame("InputDialog Example #1");
		//String in_path = JOptionPane.showInputDialog(dialogFrame, "Path assoluto dove salvare");
		
		//TODO fare in modo che venga preso l'input
		/*
		System.out.println("Il file verra' salvato in ~/Fiji.app/image.jpg");	
		String in_path="/home/seppho/Fiji.app/image.jpg";
		
		try {
			File file = new File(in_path);
		    ImageIO.write(img, "jpg", file);
		  } catch (IOException e1) {
		    e1.printStackTrace();
		  }
		*/  
	}

}
