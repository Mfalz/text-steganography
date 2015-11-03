


import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderAction implements ChangeListener{
	JSlider sliders[];	// ref sliders
	JTextField counts[];// ref sliders count
	int []slider_counts=new int[3];
	boolean forceValue = false;
	
	
	public SliderAction(JSlider[] ref_sliders,JTextField[] ref_counts){
		this.sliders=new JSlider[3];
		this.counts=new JTextField[3];
		
		for(int i=0;i<3;i++){
			this.sliders[i]=ref_sliders[i];
			this.slider_counts[i]=ref_sliders[i].getValue();
			this.counts[i]=ref_counts[i];
		}
	}
	
	public void setCounts(int[] c){
		//System.out.println("SliderAction.setCounts("+ c[0] +","+ c[1] +","+ c[2] + ")");
		for(int i=0;i<3;i++)
			this.slider_counts[i]=c[i];
		this.forceValue=true;
		this.sliders[0].setValueIsAdjusting(true);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		int sum=0;
		
		for(int i=0;i<3;i++)
			sum+=this.sliders[i].getValue();
		
		if(this.forceValue == true){
			//System.out.println("+++ Slider.stateChanged() - Value forced "+this.slider_counts[0] + ", " + this.slider_counts[1] + ", " + this.slider_counts[2]);
			//reflesh
			for(int i=0;i<3;i++){
				this.sliders[i].setValue(this.slider_counts[i]);
				this.sliders[i].setVisible(true);
				this.counts[i].setText(Integer.toString(this.sliders[i].getValue()));
				this.sliders[i].repaint();
			}
			
			this.forceValue=false;
			return;
		}		
			//System.out.println("StateChanged " + this.sliders[0].getValue()+ " - " +this.sliders[1].getValue() + " - " + this.sliders[2].getValue());
		
		int difference = Math.abs(100 - sum - 1);
		
		int count_capacity=this.sliders[ImageProperty.CAPACITY].getValue();
		int count_robustness=this.sliders[ImageProperty.ROBUSTNESS].getValue();
		int count_security=this.sliders[ImageProperty.SECURITY].getValue();			
		
		JSlider max;
		JSlider middle;
		JSlider min;
				
		JSlider current;
		if(e.getSource().equals(this.sliders[ImageProperty.CAPACITY])){
			current = this.sliders[ImageProperty.CAPACITY];
			if(count_security > count_robustness){
				max = this.sliders[ImageProperty.SECURITY];
				min = this.sliders[ImageProperty.ROBUSTNESS];
			}else{
				min = this.sliders[ImageProperty.SECURITY];
				max = this.sliders[ImageProperty.ROBUSTNESS];
			}				
		}else if(e.getSource().equals(this.sliders[ImageProperty.ROBUSTNESS])){
			current = this.sliders[ImageProperty.ROBUSTNESS];
			if(count_capacity > count_security){
				max = this.sliders[ImageProperty.CAPACITY];
				min = this.sliders[ImageProperty.SECURITY];
			}else{
				min = this.sliders[ImageProperty.CAPACITY];
				max = this.sliders[ImageProperty.SECURITY];
			}
		}else{ 
			current = this.sliders[ImageProperty.SECURITY];
			if(count_capacity > count_robustness){
				max = this.sliders[ImageProperty.CAPACITY];
				min = this.sliders[ImageProperty.ROBUSTNESS];
			}else{
				min = this.sliders[ImageProperty.CAPACITY];
				max = this.sliders[ImageProperty.ROBUSTNESS];
			}
		}
		
		
		// lo slider che ha generato l'evento non va modificato!
		if(sum<100){//devo aumentare qualche proprieta'
			
			//System.out.println("[slider.action] sum<100");			
			// l'evento e' generato dalla rottura dell'equilibrio, diminuendo una proprieta'
			
			int actual_value = min.getValue();
			min.setValue(actual_value + difference);
			min.setVisible(true);			
		}else{//devo diminuire qualche proprieta'
			
			//System.out.println("[slider.action] sum>100");
			// l'evento e' generato dalla rottura dell'equilibrio, aumentando una proprieta'
			
			int actual_value = max.getValue();
			max.setValue(actual_value - difference);
			max.setVisible(true);
		}
		//reflesh
		for(int i=0;i<3;i++){
			this.sliders[i].setVisible(true);
			this.counts[i].setText(Integer.toString(this.sliders[i].getValue()));
		}
		
	}
	
}
