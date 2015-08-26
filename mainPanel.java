package steganography;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


//questa classe costruira' il pannello di sfondo

public class mainPanel{
	public JPanel mainPane;
	
	mainPanel(BufferedImage img){
		//Create a JPanel, and add the ConversionPanels to it.
        //mainPane.setPreferredSize(new Dimension(400,400));
		mainPane=new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.setOpaque(true);
        mainPane.setBackground(new Color(0, 0, 255));
        
        subPanel s=new subPanel(img);
        
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//padding
        
        //mainPane.add(Box.createRigidArea(new Dimension(800, 800)));
        mainPane.setPreferredSize(new Dimension(1000,500));
        
        mainPane.add(s);
        
        
        //mainPane.add(Box.createGlue());
	}
}
