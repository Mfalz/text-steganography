package steganography;
import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


class ImageActions extends JPanel{
	CoverImage ref;
	
	public ImageActions(CoverImage r,ImageProperty property,HiddenMessage msg){
		this.ref=r;
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		JButton coderButton = new JButton("Codifica");
		JButton deCoderButton = new JButton("DeCodifica");
		coderButton.addActionListener(new SteCoder(r,msg,property.getStegoProperty()));
		deCoderButton.addActionListener(new StegoDeCoder(r));
		
		//pannello dei bottoni
		JPanel buttons=new JPanel();
		buttons.add(coderButton);
		buttons.add(deCoderButton);
		this.add(buttons);
	}
}


/* Questa classe implementa la cover su cui dovra' avvenire il processo steganografico 
 * La cover puo' essere in bianco e nero ( 8 bit )
 * 						in rgb ( 24 bit )
 * */

public class CoverImage extends JPanel{
	public BufferedImage coverImage; // la cover
	public ImageProcessor processor; // processore sulla cover 
	private JLabel imageLabel;       // il campo immagine 
	ImageActions imageAct;           // il campo delle azioni sull'immagine
	
	
	public ImageProcessor getProcessor(){//restituisce il processore su cui e' basata la cover
		return this.processor;
	}
	public void setProcessor(ImageProcessor ip){// setta un processore per la cover, magari dopo una modifica..
		this.setImage(ip.getBufferedImage());
		// TODO l'immagine risulta modificata?
		this.processor=ip;
	}
		
	private void setImage(BufferedImage im){//questo metodo setta l'immagine sul pannello a partire da un buffer
		BufferedImage img = this.processor.getBufferedImage();
		
		//l'immagine e' scalata per motivi visuali
		BufferedImage scaledImage = resize(img,300,300);
		ImageIcon imgIcon=new ImageIcon(scaledImage);
		
		this.imageLabel.setIcon(null);
		this.imageLabel.setIcon(new ImageIcon(scaledImage));		
		// mostro le informazioni riguardante l'immagine
		this.showImageInfo();
		
		System.out.println("CoverImage.setImage() all right");
	}
	
	public void setImage(){//metodo di accesso per settare l'immagine in funzione del suo processore
		this.setImage(this.processor.getBufferedImage());
	}
	
	// costruttore
	// una cover ha bisogno di un'immagine, delle proprieta' e di un messaggio da nasconderci dentro
	public CoverImage(BufferedImage img,ImageProperty refProperty,HiddenMessage msg){
		//TODO il processore dovrebbe cambiare in funzione della tipologia di immagine
		//questa immagine ha questo processore
		this.processor = new ColorProcessor(img);
		this.imageLabel=new JLabel();// dove mettere l'immagine
		
		// setto il campo con le azioni, passandogli le informazioni utili
		this.imageAct=new ImageActions(this,refProperty,msg);
		
		this.setImage();//setImage necessita di imageLabel istanziato
		
		this.add(imageLabel);
		this.add(imageAct);	
		
	}
	
	// grazie a MadProgrammer
	// http://stackoverflow.com/questions/14548808/scale-the-imageicon-automatically-to-label-size
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
		
	private void showChannelBits(){
		int width = this.processor.getWidth();
		int height = this.processor.getHeight();
		
		byte[] red=new byte[height*width];
		byte[] green=new byte[height*width];
		byte[] blue=new byte[height*width];
		byte[] nothing=new byte[height*width];
		for(int i=0;i<height*width;i++)
				nothing[i]=0;

		ColorProcessor myImage=this.processor.convertToColorProcessor();
		
		ColorProcessor redImage=(ColorProcessor)myImage.duplicate();
		ColorProcessor greenImage=(ColorProcessor)myImage.duplicate();
		ColorProcessor blueImage=(ColorProcessor)myImage.duplicate();
		
		myImage.getRGB(red,green,blue);//restituisce i 3 bit plane
		
		redImage.setRGB(red,nothing,nothing);//setta i 3 bit plane
		greenImage.setRGB(nothing,green,nothing);//setta i 3 bit plane
		blueImage.setRGB(nothing,nothing,blue);//setta i 3 bit plane
		
		BufferedImage red_component = redImage.getBufferedImage();
		BufferedImage green_component = greenImage.getBufferedImage();
		BufferedImage blue_component = blueImage.getBufferedImage();
		
		BufferedImage r_thumbnail=resize(red_component,50,50);
		BufferedImage g_thumbnail=resize(green_component,50,50);
		BufferedImage b_thumbnail=resize(blue_component,50,50);
		
		JLabel rgb[]=new JLabel[3];
		
		rgb[ImageProperty.RED_CHANNEL]=new JLabel();
		rgb[ImageProperty.RED_CHANNEL].setIcon(new ImageIcon(r_thumbnail));
		
		rgb[ImageProperty.GREEN_CHANNEL]=new JLabel();
		rgb[ImageProperty.GREEN_CHANNEL].setIcon(new ImageIcon(g_thumbnail));
		
		rgb[ImageProperty.BLUE_CHANNEL]=new JLabel();
		rgb[ImageProperty.BLUE_CHANNEL].setIcon(new ImageIcon(b_thumbnail));
		
		//aggiungo i 3 canali ad un unico pannello
		JPanel channels_pane=new JPanel();
		if(this.imageAct.getComponentCount()>1){
			this.imageAct.remove(1);
		}
		for(int i=0;i<3;i++)
			channels_pane.add(rgb[i]);
		this.imageAct.add(channels_pane);
	}
	
	
	public void showImageInfo(){
		ImageProcessor ip = this.getProcessor();
		JPanel infoPane= new JPanel();
		
		//setto il layout in modo che sia dall'alto in basso
		infoPane.setLayout(new GridLayout(4,1));
				
		//aggiungo i campo contenenti le info 
		infoPane.add(new JTextField("Width= "+ip.getWidth()));
		infoPane.add(new JTextField("Height= "+ip.getHeight()));
		infoPane.add(new JTextField("Depth= "+ip.getBitDepth()));
		infoPane.add(new JTextField("#Pixel= "+ip.getPixelCount()));
		
		//setto le proprieta' dei campi di testo
		for(int i=0;i<infoPane.getComponentCount();i++)
			try{
				JTextField asd = (JTextField)infoPane.getComponent(i);
				asd.setEditable(false);
				asd.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			}catch(ArrayIndexOutOfBoundsException ex){
				break;
			}
		this.showChannelBits();
		//se gia' esiste un infoPane
		if(this.imageAct.getComponentCount()>2)
			this.imageAct.remove(1);
		this.imageAct.add(infoPane);		
	}
}
