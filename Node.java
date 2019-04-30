import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Node {
	int nodeId;
	double lat;
	double lon;
	private Location location;
	ArrayList<Segment> Nodesegs = new ArrayList<Segment>();
    
public Node(int nodeId, double lat, double lon) {
	this.nodeId = nodeId;
	this.lat = lat;
	this.lon = lon;
	this.location = this.location.newFromLatLon(this.lat, this.lon); 
 
}




public void setNode(Segment s) {
	
	
	Nodesegs.add(s);
	
}

//node methods

public Location getLocation() {
	return this.location;
    }

// fix id
public int getNodeID() {
	return this.nodeId;
   }
public ArrayList<Segment> getSegments() {
	
	return Nodesegs;
	
}

}