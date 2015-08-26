package steganography;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;



public class ImageProperty extends JPanel{
	/* Questa classe e' la parte sinistra della frame, contiene le proprieta' settabili
	 * */
	public static int CAPACITY=0;
	public static int ROBUSTNESS=1;
	public static int SECURITY=2;
	
	public static short RED_CHANNEL = 0;
	public static short GREEN_CHANNEL = 1;
	public static short BLUE_CHANNEL = 2;
	
	
	JSlider sliders[];
	
	public int[] getStegoProperty(){
		int[] result= new int[3];
		result[this.CAPACITY]=this.sliders[this.CAPACITY].getValue();
		result[this.ROBUSTNESS]=this.sliders[this.ROBUSTNESS].getValue();
		result[this.SECURITY]=this.sliders[this.SECURITY].getValue();
		return result;
	}
	
	public ImageProperty(){
		this.setOpaque(false);
		
		JTextField slider_labels[]=new JTextField[3];
		JTextField slider_counts[]=new JTextField[3];
		this.sliders=new JSlider[3];
		
		for(int i=0;i<3;i++){
			sliders[i]=new JSlider(JSlider.HORIZONTAL, 0, 100, 33);
			
			slider_counts[i]=new JTextField("33");
			slider_labels[i]=new JTextField();
			
			slider_labels[i].setHorizontalAlignment(JTextField.RIGHT);
			slider_counts[i].setHorizontalAlignment(JTextField.LEFT);
			
			slider_counts[i].setEditable(false);
			slider_labels[i].setEditable(false);
			
			slider_labels[i].setBorder(null);
			slider_counts[i].setBorder(null);
			//set actions
		}
		for(int i=0;i<3;i++)sliders[i].addChangeListener(new SliderAction(sliders,slider_counts));
		
		slider_labels[CAPACITY].setText("Capacita'");
		slider_labels[ROBUSTNESS].setText("Invisibilita'");
		slider_labels[SECURITY].setText("Sicurezza");
		
		//this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		//setto grid layout in modo da avere 3 colonne e 3 righe
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.NORTH;
		
		c.ipady = 10;
		for(int i=0;i<3;i++){		
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = i;
			this.add(slider_labels[i], c);
		
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = i;
			this.add(sliders[i],c);
		
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = i;
			this.add(slider_counts[i],c);
		}
	}
}
