import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Segment {

int roadId;

double length;
int NodeFrom;
int NodeTo;
List<Location> cords = new ArrayList<Location>();//list of the lat lons as location objects


public Segment(int roadId,double length,int toNode, int fromNode,List<Location> cords) {
	this.roadId = roadId;
	this.length = length;
	this.NodeFrom = fromNode;
	this.NodeTo = toNode;
	this.cords = cords;
	
	


}

//segment methods

public List getLocations() {
	return this.cords;
    }

public int getRoadId() {
	return this.roadId;
  }

}


