
import ij.process.ImageProcessor;


public class LSBdecoding extends LSBstego {
	boolean foundMessage;
	private String stegoMessage;

	public LSBdecoding(ImageProcessor ip) {
		super(ip);

		this.setupPixels();

		this.foundMessage=false;

		this.decodify();
	}

	protected void setupPixels(){
		//setup pixels pixels, hat, header
		if(PixelStructure.PROD)
			System.out.println("+ setup pixels for decodify, searching hat.. (•̀ᴗ•́)و ̑̑");
		Hats=new Hat();
		int messageLength=0;
		pixels = new Pixel[height*width];
		for(int i=0;i<height*width;i++)
			pixels[i]=new Pixel(red_channel[i],green_channel[i],blue_channel[i],i);
		this.searchHat();
	}


	public String decodify(){
		// if there are hats then there is a message
		if(this.Hats==null || this.Hats.isEmpty())return "";
		//this.Hats.getFirst().getHeader().print();
		this.Hats.getFirst().getHeader().print();
		System.out.println("Position First HAT:"+(this.Hats.getFirst().getPosition()));
		this.property=this.Hats.getFirst().getHeader().getProperty();

		if(PixelStructure.PROD)
			System.out.println("Starting decodify with (cap,rob,sec)=("+property[0]+","+property[1]+","+property[2]+")");
		int capacity = LSBstego.capacity(property[0]);
		if(this.Hats.getFirst()!=null){
			String msg=new String();
			msg=this.read(capacity, this.Hats.getFirst().getHeader().getMessageLength(), this.Hats.getFirst().getHeader().getMessagePosition());
			if(msg!=null){
				this.foundMessage=true;
				this.stegoMessage=msg;
			}

			if(msg!=null && PixelStructure.PROD){
				System.out.println("Found stego message: "+msg+" of "+this.Hats.getFirst().getHeader().getMessageLength()+" characters");
			}
			return msg;
		}

		return null;
	}

	public boolean hasMessage() {
		// TODO Auto-generated method stub
		return this.foundMessage;
	}

	public String getStegoMessage() {
		// TODO Auto-generated method stub
		return this.stegoMessage;
	}

	public int[] getStegoProperty() {
		// TODO Auto-generated method stub
		return this.property;
	}

	public int[] getProperty() {
		// TODO Auto-generated method stub
		return this.property;
	}

}
