package steganography;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class Sliders extends JSlider {
	public JSlider[] Sliders(){
		JSlider sliders[]=new JSlider[3];
		for(int i=0;i<3;i++)
			sliders[i]=new JSlider(JSlider.HORIZONTAL, 0, 100, 33);
	return sliders;	
	}
}
