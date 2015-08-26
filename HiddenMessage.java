package steganography;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class HiddenMessage extends JPanel{
	protected JTextArea hiddenMessage;
	
	public String getText(){
		return this.hiddenMessage.getText();
	}
	
	public HiddenMessage(){
		this.hiddenMessage = new JTextArea("",10,50);
		hiddenMessage.setBackground(new Color(0xEE,0xEE,0xEE));
		
		hiddenMessage.setBorder(BorderFactory.createLineBorder(new Color(0x41,0x41,0x41)));
		this.add(hiddenMessage);
	}
}
