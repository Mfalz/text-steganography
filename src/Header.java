
/*
 * Set of pixelHeader, this is the header
 */
public class Header{
	private Pixel header[] = new Pixel[LSBstego.HEADER_SIZE+1];
	private int position;
	private int property[];
	byte red;
	byte green;
	byte blue;
	private int size;
	private int messageLength;
	private Pixel pixels[];

	// h is the first pixel of the header
	public Header(Pixel h[],int pos) {
		this.position=pos;
		this.red=h[pos].get_red();
		this.green=h[pos].get_green();
		this.blue=h[pos].get_blue();
		this.pixels=h;

		this.size=0;
		this.property=new int[3];
		for(int i=position,j=0;i<position+LSBstego.HEADER_SIZE;i++,j++){// each pixel of the header
			header[j]=pixels[position+j];
			switch(j){
				case 0:
					property[0]=header[j].getBits(LSBstego.METATADA_LSB, 8);
					property[0]=property[0]>>24;
					//System.out.println(String.format("%8s", Integer.toBinaryString(property[0] & 0xFF)).replace(' ', '0') + " | ");
					break;
				case 1:
					property[1]=header[j].getBits(LSBstego.METATADA_LSB, 8);
					property[1]=property[1]>>24;
					break;
				case 2:
					property[2]=header[j].getBits(LSBstego.METATADA_LSB, 8);
					property[2]=property[2]>>24;
					break;
				default: //TODO messageLength
			}

			this.size++;
		}
	}

	public void print(){
		System.out.println("HEADER at position: "+this.position+" | size: "+this.size);
		System.out.println("property: "+property[0]+","+property[1]+","+property[2]);
		for(int i=0;i<size+2;i++)
			this.pixels[position+i].print();
			//this.header[i].print();
	}

	public int getMessageLength(){
		//int ret=header[4];
		//System.out.println(String.format("%8s", Integer.toBinaryString(this.red[position+1] & 0xFF)).replace(' ', '0') + " | ");
		return (int)(header[3].getBits(LSBstego.METATADA_LSB, 8)>>24);
		//return 8;
		//return 3;
	}

	public void setMessageLength(int msgl){
		this.messageLength=msgl;
	}

	public int getMessagePosition(){
		return this.position+LSBstego.HEADER_SIZE;
	}

	public void setProperty(int[] property){
		this.property=property;
	}

	public int[] getProperty(){
		return this.property;
	}

	public boolean isCorrupt(){
		int cap=this.property[0];
		int rob=this.property[1];
		int sec=this.property[2];
		if(cap<0 || rob<0 || sec<0)return true;
		if((cap+rob+sec)>=99 && (cap+rob+sec)<=100)
			return false;
		return true;
	}
}
