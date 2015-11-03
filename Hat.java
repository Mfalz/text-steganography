//linked list of hats

class hatNode{
	private hatNode next;
	protected PixelHat reference;
	private Header header;


	public hatNode(PixelHat p,Header h){
		this.reference = p;
		this.next=null;
		this.header=h;
	}

	public hatNode getNext(){return next;}
	public void setNext(hatNode nxt){this.next=nxt;}
	public Header getHeader(){return header;}
	public void print(){this.reference.print();}
	public void printHeader(){this.header.print();}
	public int getPosition(){return this.reference.get_position();}
	public void setHeader(Header h){header=h;}
	//public String getMessage(){
	//	return "";
	//}
}

public class Hat {
	private int length;
	private hatNode head;

	public Hat() {
		this.length=0;
		this.head= null;
	}

	public int insert(PixelHat newPixel,Header h){
		if(isEmpty())this.head=new hatNode(newPixel,h);
		else{
			// insert last
			hatNode cursor=this.head;
			while(cursor.getNext()!=null)
				cursor=cursor.getNext();
			//cursor is last element
			cursor.setNext(new hatNode(newPixel,h));
		}
		this.length++;
		return 0;
	}
	public int[] getProperty(){
		int[] zero=new int[3];
		for(int i=0;i<3;i++)zero[i]=0;
		
		if(this.getFirst()!=null)
			return this.getFirst().getHeader().getProperty();
		return zero;
	}
	public boolean isEmpty(){return this.length==0;}
	public hatNode getFirst(){return head;}
	public int length(){return this.length;}

}
