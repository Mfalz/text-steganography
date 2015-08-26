package steganography;


import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderAction implements ChangeListener{
	JSlider sliders[];
	JTextField counts[];
	
	int tmp_capacity;
	int tmp_robustness;
	int tmp_security;
	JLabel[] slider_counts;
	
	public SliderAction(JSlider[] ref_sliders,JTextField[] ref_counts){
		this.sliders=new JSlider[3];
		this.counts=new JTextField[3];
		
		for(int i=0;i<3;i++){
			this.sliders[i]=ref_sliders[i];
			this.counts[i]=ref_counts[i];
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int sum=0;
		for(int i=0;i<3;i++)
			sum+=this.sliders[i].getValue();
		
		if(sum<100){
			//event
			int i=100-sum-1;
			int count_capacity=this.sliders[ImageProperty.CAPACITY].getValue();
			if(i>0){
				count_capacity+=i;
				this.sliders[ImageProperty.CAPACITY].setValue(count_capacity);
			}
			this.counts[ImageProperty.CAPACITY].setText(Integer.toString(count_capacity));
			for(int index=1;index<3;index++)
				this.counts[index].setText(Integer.toString(this.sliders[index].getValue()));
			
		}else{
			for(int index=0;index<3;index++)
				if(this.sliders[index].getValue()>33)this.sliders[index].setValue(this.sliders[index].getValue()-1);
		}
	}
	
}
