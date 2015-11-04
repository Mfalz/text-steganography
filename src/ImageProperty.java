
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

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
import javax.swing.event.ChangeListener;



public class ImageProperty extends JPanel{
	/* Questa classe e' la parte sinistra della frame, contiene le proprieta' settabili
	 * */
	public static int CAPACITY=0;
	public static int ROBUSTNESS=1;
	public static int SECURITY=2;

	public static short RED_CHANNEL = 0;
	public static short GREEN_CHANNEL = 1;
	public static short BLUE_CHANNEL = 2;

	JTextField slider_labels[];
	JTextField slider_counts[];

	JSlider sliders[];
	private SliderAction action;

	private boolean new_value = false;
	private int[] new_property;

	public static int capacity(int c){
		int result=0;
		result=(8*c)/100;
		if(result<1)
			result=1;
		return result;
	}



	public static int security(int s){
		return s;
	}

	public int[] getStegoProperty(){
		int[] result= new int[3];
		result[this.CAPACITY]=this.sliders[this.CAPACITY].getValue();
		result[this.ROBUSTNESS]=this.sliders[this.ROBUSTNESS].getValue();
		result[this.SECURITY]=this.sliders[this.SECURITY].getValue();
		return result;
	}

	public void setProperty(int []property){

		//for(int i=0;i<3;i++)sliders[i].removeChangeListener((ChangeListener)l);
		this.new_property=property;
		this.new_value=true;
		this.action.setCounts(property);
		//System.out.println("+++ ImageProperty.setPropertyValue("+property[0]+","+property[1]+","+property[2]+")");
		//this.sliders[0].setValue(property[0]);this.sliders[1].setValue(property[1]);this.sliders[2].setValue(property[2]);

	}

	public ImageProperty(){
		this.setOpaque(false);

		this.slider_labels=new JTextField[3];
		this.slider_counts=new JTextField[3];
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
		/*
		int[] p=new int[3];
		p[0]=33;p[1]=33;p[2]=33;
		this.setProperty(p);
		*/
		this.action=new SliderAction(this.sliders,this.slider_counts);
		for(int i=0;i<3;i++)sliders[i].addChangeListener(this.action);

		slider_labels[CAPACITY].setText("Capacita'");
		slider_labels[ROBUSTNESS].setText("Robustezza");
		slider_labels[SECURITY].setText("Sicurezza");

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.NORTH;

		c.ipady = 10;
		c.fill = GridBagConstraints.PAGE_START;
		JTextField text = new JTextField("Proprieta'");
		text.setEditable(false);
		text.setBorder(null);

		this.add(text,c);
		for(int i=1;i<4;i++){
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = i;
			this.add(slider_labels[i-1], c);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = i;
			this.add(sliders[i-1],c);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = i;
			this.add(slider_counts[i-1],c);
		}
	}
}
