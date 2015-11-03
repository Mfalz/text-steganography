
import ij.process.ImageProcessor;


public class Pixel{
	protected int position;
	protected byte red;
	protected byte green;
	protected byte blue;
	protected int bit_used; // how much of the pixel is used
	private Pixel next;
	private Pixel prev;

	//position is an index
	public Pixel(byte red,byte green,byte blue,int position){
		this.position=position;
		this.red=red;
		this.green=green;
		this.blue=blue;
		this.bit_used=0;
		this.next=null;
		this.prev=null;
	}

	public boolean isFull(){return this.bit_used>=24;}

	/* Il metodo getBits deve restituire i bit conservati all'interno del pixel.
	 * Per ogni canale, legge #bit_length a partire dal canale rosso
	 * */

	public int getBits(int capacity,int bit_length){
		int []buffer=new int[3];
		int stream=0;
		if(bit_length>32)bit_length=32;
		//byte MSBmask=(byte)(~(0xFF >> capacity)); // 0b11100000 - eg with capacity = 3
		byte LSBmask=(byte)(~((byte)0xFF << capacity)); // 0b00000111

		buffer[0]=this.red & 0xFF;
		buffer[1]=this.green & 0xFF;
		buffer[2]=this.blue & 0xFF;
		// set information on the LSB

		for(int i=0;i<3;i++)
			buffer[i]=(buffer[i]&LSBmask)<<(32-capacity-(capacity*i));

		debug("+ getBits() on pixel #"+this.position);


		// make the stream
		for(int i=0;i<3;i++){
			stream+=buffer[i];
			if(PixelStructure.DEBUG){
				System.out.print("int buffer["+i+"]: ");
				System.out.println(String.format("%32s", Integer.toBinaryString(buffer[i])).replace(' ', '0')+"\n");
				System.out.println(String.format("%8s", Integer.toBinaryString(LSBmask)).replace(' ', '0')+"\n");
				}
		}
		// cut #bit_stream
		stream=(int)(stream&(0xFFFFFFFF<<(32-bit_length)));
		debug("\n");
		return stream;
	}

	/* Il metodo setLSB prende in input la capacita' della codifica, lo stream e il numero di bit effettivamente al suo interno
	 * Scorre lo stream, inserendo #capacity bits nei #capacity bit meno significativi del canale.
	 * ogni volta che si satura un canale di colore, viene incrementata la variabile used, cosi da tener conto di quale canale
	 * e' effettivamente usato.
	 * */
	public int setLSB(int capacity, int bit_length,int stream){
		int writed=0;
		// this method read capacity*3 bits from stream
		// bit length = # bit in stream

		if(this.isFull())return 0;

		byte []channel=new byte[3];
		channel[0]=this.red;
		channel[1]=this.green;
		channel[2]=this.blue;

		byte MSBmask=(byte)(~(0xFF >> capacity)); // 0b11100000 - eg with capacity = 3
		byte LSBmask=(byte)(~(0xFF << capacity)); // 0b00000111

		debug("+ writing stream into pixel #"+this.position + " used: "+this.bit_used+" bits");
		debug("\nstream length: "+bit_length);

		for(int index=(this.bit_used/8);bit_length>0 && index<3;index++){
			// for each channel, if exists bit in the stream
			int now_writed=0;
			//debug("\n channel["+index+"]:  ");
			//this.printBits(channel[index]);

			if(bit_length>=capacity){
				// perfectly aligned with capacity
				byte replacement = (byte)((byte)(stream>>(32-capacity))&LSBmask);//only the LSB

				//debug(" replacement: ");
				//this.printBits(replacement);

				channel[index]=(byte)(channel[index]&(~LSBmask));
				channel[index]=(byte)(channel[index]+replacement);

				writed+=capacity;
				now_writed=capacity;
				//System.out.print(" result:      ");this.printBits(channel[index]);

			}else{
				// less bit then capacity
				int diff=0;
				while(((bit_length+diff)%capacity )!=0)diff++;
				// diff is the number of missing bits
				byte replacement = (byte)((byte)(stream>>(32-capacity))&LSBmask);// the LSB

				//debug(" replacement: ");
				//this.printBits(replacement);

				// make the mask for select only the real number of bits
				byte LSBdiff=(byte)(0xFF>>(8-bit_length));
				// shift the mask and set the bit set in the right position
				LSBdiff=(byte)(LSBdiff<<diff);

				//this.printBits((byte)LSBdiff);
				// current channel = channel, in the position of the bit should be inserted, there are 0
				channel[index]=(byte)(channel[index]&(~LSBdiff));
				channel[index]=(byte)(channel[index]+replacement);

				//debug(" result:      ");this.printBits(channel[index]);

				// set the effective writed number
				writed+=capacity-diff;
				// now writed is only for local purpose
				now_writed=capacity-diff;
			}
			// shift the stream, delete the bit readed yet
			stream=stream<<now_writed;
			// stream length will be decrease
			bit_length-=now_writed;
			// this pixel bit setted
			this.bit_used+=(8-capacity)+writed;
		}

		this.red=channel[0];
		this.green=channel[1];
		this.blue=channel[2];
		return writed;
	}

	// getting value
	public void set_red(byte r){this.red=r;}
	public void set_green(byte g){this.green=g;}
	public void set_blue(byte b){this.blue=b;}
	public byte get_red(){return this.red;}
	public byte get_green(){return this.green;}
	public byte get_blue(){return this.blue;}
	public int get_position(){return this.position;}
	public Pixel getNext(){return this.next;}
	public Pixel getPrev(){return this.prev;}
	public void setNext(Pixel n){this.next=n;}
	public void setPrev(Pixel p){this.next=p;}

	public void print(){
		System.out.print("Pixel #" + String.format("%8s", this.position) + ": ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.red & 0xFF)).replace(' ', '0') + " | ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.green & 0xFF)).replace(' ', '0') + " | ");
		System.out.print(String.format("%8s", Integer.toBinaryString(this.blue & 0xFF)).replace(' ', '0') + " | ");
		System.out.print("Value: " + (char)this.red + " " + (char)this.green + " " + (char)this.blue);
		System.out.println();
	}

	public void printBits(byte b){
		System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " | ");
	}


	protected void debug(String str){
		if(!PixelStructure.DEBUG || 1==1)return;

		if(str.charAt(0)=='+'){
			// method info
			System.out.println("++++++++++++++++++++++++++++++"+str);
		}else
			System.out.print(str);
	}
}
