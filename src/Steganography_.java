


import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.plugin.frame.PlugInFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Steganography_ extends PlugInFrame implements PlugIn{
    BufferedImage img;

	public void createAndShowGUI(BufferedImage img) {
        //Create and set up the window.
        JFrame frame = new JFrame("Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel m = new mainPanel(img);
        m.mainPane.setOpaque(false);
        frame.setContentPane(m.mainPane);
        frame.pack();
        frame.setVisible(true);
		//System.out.println("Steganography.createAndShowGUI() all right");
    }

	public void run(String arg) {
		ImagePlus imp = null;
		while(imp==null)
			imp=WindowManager.getCurrentImage();
		
		BufferedImage img = imp.getBufferedImage();
		imp.close();
		createAndShowGUI(img);
		//System.out.println("Steganography.run() all right");
	}

	
  public Steganography_() {
	  //imposta il titolo
	  super("Steganography");
    }


}
