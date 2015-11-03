
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
		ImageProperty imageProperty=new ImageProperty();	
		ImageOperation imageOperation=new ImageOperation(imageProperty,img);		
		
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        imageProperty.setMaximumSize(new Dimension(300,200));
		imageOperation.setMaximumSize(new Dimension(670,700));
        imageProperty.setAlignmentY(TOP_ALIGNMENT);
        imageOperation.setAlignmentY(TOP_ALIGNMENT);
        
        add(imageProperty);
        add(imageOperation);
        
	}
}
