

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageOperation extends JPanel{
	/* Questa classe e' la parte destra della frame, contiene l'immagine ed il testo da inserire
	 * */
	public ImageOperation(ImageProperty refProperty,BufferedImage img){
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor=GridBagConstraints.NORTH;
		
		HiddenMessage message=new HiddenMessage();
		
		//immagine
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		CoverImage image = new CoverImage(img,refProperty,message);
		this.add(image,gbc);	
		
		//messaggio
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=1;
		
		this.add(message,gbc);
		
		
		
		//this.setAlignmentY(TOP_ALIGNMENT);
		
				
		
		
	}
}
