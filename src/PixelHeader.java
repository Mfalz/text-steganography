

public class PixelHeader extends Pixel{

	public PixelHeader(byte red,byte green, byte blue, int position) {
		super(red, green, blue, position);
	}

	public void print(){
		System.out.print("Pixel #" + String.format("%8s", this.position) + ": ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.red & 0xFF)).replace(' ', '0') + " | ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.green & 0xFF)).replace(' ', '0') + " | ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.blue & 0xFF)).replace(' ', '0') + " | ");
		System.out.print("Value: " + (char)this.red + " " + (char)this.green + " " + (char)this.blue);
		System.out.print("---------- is header");
		System.out.println();
	}
}
