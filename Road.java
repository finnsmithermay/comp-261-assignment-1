import java.util.ArrayList;
import java.util.List;

public class Road {

	int roadId;	
	int type;
	String label;    
	String city ;   
	int oneway;
	int speed	;
	int roadclass;
	int notforcar;	
	int notforpede;	
	int notforbicy; 
	ArrayList<Segment> Roadsegs = new ArrayList<Segment>();

	
	public Road(int roadId,int type, String label,String city,int oneway,int speed,int roadclass,int notforcar,	int notforpede,	int notforbicy){
		
		this.roadId =roadId;	
		this.type = type;
		this. label= label;    
		this. city = city;   
		this .oneway = oneway;
		this. speed	= speed;
		this.roadclass = roadclass;
		this.notforcar = notforcar;	
		this.notforpede = notforpede;	
		this.notforbicy = notforbicy;
		
	}
	
	//road methods
	
	 public int getRoadId(){
			return this.roadId;
		    }
	 public String getRoadName() {
			return this.label;
		    }
	 
	 public ArrayList<Segment> getRoadSegs() {
			
			return Roadsegs;
			
		}
	 public void addSegment(Segment seg) {
		 
		 Roadsegs.add(seg);
	 }
	 
	 
}