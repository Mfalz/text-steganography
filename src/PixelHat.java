
class PixelHat extends Pixel {
	private boolean hat;

	public PixelHat(Pixel p){
		// TODO Auto-generated constructor stub
		super(p.get_red(), p.get_green(), p.get_blue(), p.get_position());
	}

	public int nextMinHop(){
		return this.position + LSBstego.METADATA_SIZE;
	}
	public void setHat(boolean flag){this.hat=flag;}

	public void print(){
		System.out.print("Pixel #" + String.format("%8s", this.position) + ": ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.red & 0xFF)).replace(' ', '0') + " | ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.green & 0xFF)).replace(' ', '0') + " | ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.blue & 0xFF)).replace(' ', '0') + " | ");
		System.out.print("Value: " + (char)this.red + " " + (char)this.green + " " + (char)this.blue);
		System.out.print("---------- has hat");
		System.out.println();
	}
}
