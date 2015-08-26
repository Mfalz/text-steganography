package steganography;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.frame.PlugInFrame;
import ij.plugin.*;

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
		System.out.println("Steganography.createAndShowGUI() all right");
        /*
        	//creo il menuBar
      		JMenuBar mainMenuBar = new JMenuBar();
      		mainMenuBar.setOpaque(true);
      		mainMenuBar.setBackground(new Color(0x41, 0x41, 0x41));
      		mainMenuBar.setPreferredSize(new Dimension(100, 40));
      		
      		//creo item menuBar
      		JMenu fileMenu,stegoMenu;
      		fileMenu=new JMenu("Immagine");
      		fileMenu.setForeground(new Color(0xFF,0xFF,0xFF));
      		//creo i sottomenu per file
      		JMenuItem sub_fileMenu1=new JMenuItem("Carica Immagine",null);     		
      		sub_fileMenu1.setText("Carica Immagine");

      		//ancoro i sottomenu
      		fileMenu.add(sub_fileMenu1);

      		//ancoro i due menu al menubar
      		mainMenuBar.add(fileMenu);

      		//ancoro il menuBar al frame
      		frame.setJMenuBar(mainMenuBar);

      		// setto le azioni
      		sub_fileMenu1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    JFileChooser fc = new JFileChooser();
                    
                    int result = fc.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        try {
                        	//immagine
                        	img = ImageIO.read(file);
                        	
                        } catch (IOException e) {
                        	try {
								img = ImageIO.read(new File("/home/seppho/lena.jpg"));
								m=new mainPanel(img);
						        
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                            e.printStackTrace();
                        }
                    }
                }
            });
      	*/
        
        
      	
    }

	public void run(String arg) {
		ImagePlus imp = null;
		while(imp==null)
			imp=WindowManager.getCurrentImage();
		
		BufferedImage img = imp.getBufferedImage();
		imp.close();
		createAndShowGUI(img);
		System.out.println("Steganography.run() all right");
	}

	
  public Steganography_() {
	  //imposta il titolo
	  super("Steganography");
    }


}
