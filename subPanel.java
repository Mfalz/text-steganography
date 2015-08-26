package steganography;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class subPanel extends JPanel{	
	public subPanel(BufferedImage img){		
		//Create a JPanel, and add the ConversionPanels to it.
        //setPreferredSize(new Dimension(200,100));
		ImageProperty imageProperty=new ImageProperty();	
		ImageOperation imageOperation=new ImageOperation(imageProperty,img);		
		
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        imageProperty.setMaximumSize(new Dimension(300,500));
		imageOperation.setMaximumSize(new Dimension(670,500));
		
        add(imageProperty);
        add(imageOperation);
        
        imageProperty.setAlignmentY(TOP_ALIGNMENT);
        imageOperation.setAlignmentY(TOP_ALIGNMENT);
        
	}
}
