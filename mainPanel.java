
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class mainPanel{
	public JPanel mainPane;
	
	mainPanel(BufferedImage img){
		mainPane=new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        mainPane.setOpaque(true);
        mainPane.setBackground(new Color(0, 0, 255));
        
        subPanel s=new subPanel(img);
        
        mainPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//padding
        
        mainPane.setPreferredSize(new Dimension(1024,800));
        mainPane.add(s);
	}
}
