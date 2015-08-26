package steganography;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StegoDeCoder implements ActionListener {
	CoverImage cover;
	
	public StegoDeCoder(CoverImage cover){
		this.cover=cover;	
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		BufferedImage image=this.cover.coverImage;
		System.out.println("Avvia la decodifica");
		System.out.println(image.getWidth());
	}

}
