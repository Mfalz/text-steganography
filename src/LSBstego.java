

import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;


class LSBstego{
	protected byte []red_channel;
	protected byte []green_channel;
	protected byte []blue_channel;

	protected ImageProcessor processor;
	static int HEADER_SIZE = 6; // #pixels
	static int HAT_SIZE = 3; // #pixels -- 3
	static int METADATA_SIZE = 10;
	static int START = 1;
	static int METATADA_LSB=3;

	static String HAT = "SEP";
	protected int []property;

	static int RED_CHANNEL = 0;
	static int GREEN_CHANNEL = 1;
	static int BLUE_CHANNEL = 2;

	protected Pixel pixels[];
	protected Header headers[];
	protected int height;
	protected int width;

	protected Hat Hats;

	protected void init(ImageProcessor ip){
		this.processor=ip;
		ColorProcessor cp=(ColorProcessor)ip;
		this.red_channel=cp.getChannel(1);
		this.green_channel=cp.getChannel(2);
		this.blue_channel=cp.getChannel(3);
		this.height=cp.getHeight();
		this.width=cp.getWidth();
	}

	public LSBstego(ImageProcessor ip){
		init(ip);
	}

	protected void printPixels(int from, int to) {
		for(;from<to;from++)
			this.pixels[from].print();
	}

	protected void printHats(){
		hatNode cursor = Hats.getFirst();
		while(cursor != null){
			cursor.print();
			cursor.printHeader();
			cursor=cursor.getNext();
		}
	}

	protected void searchHat(){
		// searching HAT
		Header header;
		int HAT_length=LSBstego.HAT.length();
		int messageLength=0;
		int p_hat=LSBstego.START;
		if(PixelStructure.PROD)
			System.out.println("Searching hats..");

		int iteration=0;
		while(p_hat<((width*height)-(8*HAT_length))){
			if(p_hat%20000==0)
				System.out.print(". ");

			String payload=this.read(3,LSBstego.HAT.length(),p_hat);

			if(payload.compareTo(LSBstego.HAT)==0){
				iteration++;
				header=new Header(this.pixels,p_hat+LSBstego.HAT_SIZE);

				if(!header.isCorrupt()){
					//System.out.println("Found header at "+p_hat+"!");
					PixelHat hat = new PixelHat(this.pixels[p_hat]);
					Hats.insert(hat,header);
					messageLength=header.getMessageLength();
					//p_hat+=LSBstego.HEADER_SIZE+LSBstego.HAT_SIZE;
					p_hat+=hat.nextMinHop()+messageLength*messageLength;
				}else
					p_hat+=1;
			}else{
				//System.out.println("Probably HAT at "+p_hat+" : "+payload);
				p_hat+=1;
			}

		}
		System.out.println();
		if(PixelStructure.PROD)
			System.out.println("Found " + Hats.length() + " hats, you can accces them by this.Hats\n");
	}

	public byte[] getRedChannel(){return this.red_channel;}
	public byte[] getGreenChannel(){return this.green_channel;}
	public byte[] getBlueChannel(){return this.blue_channel;}

	protected void debug(String str){
		if(!PixelStructure.DEBUG || 1==1)return;
		if(str.charAt(0)=='+'){
			// method info
			System.out.println("++++++++++++++++++++++++++++++"+str);
		}else
			System.out.print("\t"+str);
	}


	/* Il metodo write prende una stringa e la capacita' ( numero di bit meno significativi da utilizzare per la codifica )
	 * Salva come stream continuo all'interno del pixel a partire da un dato pixel di partenza,
	 * qualsiasi altro bit non viene modificato all'interno del pixel.
	 * */

	public int write(int capacity,String s,int p_index){
		// it should see a continued stream of byte
		// n LSB.len()
		this.debug("+ write procedure on pixel #"+p_index);
		this.debug("writing '"+s+"' using "+capacity+" LSB bits\n");

		Integer stream=new Integer(0);
		int left_bits=0;
		int stream_length=0;
		int i;

		int bit_writed=0;

		for(i=0;i<s.length() || stream_length>0;){// for each character in the string
			if(stream_length<capacity*3 && i<s.length()){
				char character = s.charAt(i);
				stream+=(character<<24-stream_length);
				stream_length+=8;
				i++;
			}
			//debug("\nstream post adding: " + String.format("%32s", Integer.toBinaryString(stream)).replace(' ', '0'));
			//debug(" | stream_length: " + stream_length);

			if(stream_length>=capacity*3 || i>=s.length()){
				// it's time to set pixel
				bit_writed=this.pixels[p_index].setLSB(capacity,stream_length,stream);
				stream_length-=bit_writed;
				stream=(stream<<(bit_writed));
				if(this.pixels[p_index].isFull()){
					red_channel[p_index]=this.pixels[p_index].get_red();
					green_channel[p_index]=this.pixels[p_index].get_green();
					blue_channel[p_index]=this.pixels[p_index].get_blue();

					p_index++;
				}

				debug("Writed correctly "+bit_writed+" bit\n");
				debug("+ END write procedure");
			}
		}
		return i;
	}

	/* Il metodo read, prende in input la capacita' utilizzata per codificare, il pixel di partenza e la lunghezza della codifica. Restituisce una stringa,
	 * tale stringa sara' il messaggio codificato all'interno dei pixel a partire dal pixel iniziale.
	 *  */

	public String read(int capacity,int length,int p_index){
		String message=new String();
		int pixel_stream=0;
		int character_index=0;
		int stream=0;

		int bit_count=0;//bit i've read from pixel
		//int bit_length=(int)(8/capacity)*(length-character_index);//bit i want to read, capacity*3 is the max
		int bit_length=capacity*3;
		if(length==4)
		debug("+ read procedure");

		while(character_index<length){
			pixel_stream=this.pixels[p_index].getBits(capacity,bit_length);
			int mask=~(((0x1<<31)>>bit_count)<<1);
			stream+=((pixel_stream>>bit_count)&mask);
			//debug("mask: "+String.format("%32s", Integer.toBinaryString(mask)).replace(' ', '0')+"\n");

			bit_count+=bit_length;
			// pixel stream has at most bit_length bits and at least capacity bits
			if(length==4)
			debug("Stream of the pixel #"+String.format("%8s",p_index)+": "+String.format("%32s", Integer.toBinaryString(pixel_stream)).replace(' ', '0')+"\n");

			// pop from the stream
			if(length==4)
			debug("pop from the stream: "+String.format("%32s", Integer.toBinaryString(stream)).replace(' ', '0')+"\tbit_count: "+bit_count+"\n");
			while(bit_count>=8 && character_index<length){
				// at least a character is ready to pop
				char c=(char)(((stream>>24)&0xFF)<<8);

				c=Character.reverseBytes(c);

				message+=c;
				stream=stream<<8;
				bit_count-=8;
				character_index++;
			}
			if(length==4)
			debug("result stream:        "+String.format("%32s", Integer.toBinaryString(stream)).replace(' ', '0')+"\tbit_count: "+bit_count+"\n");
			p_index++;
		}

		debug("+ END read procedure");
		return message;
	}

	/* PROPERTY FUNCTIONS */
	public static int capacity(int c) {
		if(c<12)return 1;
		return c/12;
	}

	public static int robustness(int r,int img_length,int m_length) {
		img_length/=2;
		if(r<=0)r=1;
		int max=((img_length/LSBstego.METADATA_SIZE)+(m_length*8)+1);
		float ret = 100+(r*max)/100;
		System.out.println("r: "+ r +" robustness:" + ret + " -- max: " + max);
		return (int)ret;
	}

	public static int security(int s) {
		// TODO Auto-generated method stub
		return 0;
	}
}
