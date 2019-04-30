import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;


public class Graph extends GUI{
	//need to change to a location object

	private double scale = 2;
	private Location origin;
    
	private Map<Integer, Node>nodesMap = new HashMap<Integer, Node>();
	private Map<Integer, Road> roadsMap = new HashMap<Integer, Road>();
    private ArrayList<Segment>segments = new ArrayList<Segment>();//may need to be a 2d array
	
	//used to find the origin
    private double smallX = Double.POSITIVE_INFINITY; 
    private double smallY = Double.POSITIVE_INFINITY;
    private double bigX = Double.NEGATIVE_INFINITY;
    private double bigY = Double.NEGATIVE_INFINITY;
    List<Integer> searchRE = new ArrayList<Integer>();

    List<Integer> searchRoadList = new ArrayList<Integer>(); //List of Road object IDs part of the same road.

    
    private int clickNodeId = 0; //id of the clicked node
    
    
	
	@Override
	public void redraw(Graphics g) {//drawing the nodes
		for(Node n : nodesMap.values()) {
			int nodeSize =3;
			int x = (int) n.getLocation().asPoint(this.origin, scale).getX();
			int y = (int) n.getLocation().asPoint(this.origin, scale).getY();
		    g.fillOval((int)x,y,nodeSize  , nodeSize);
		}
		for (Segment seg : this.segments) { //drawing the segments
		    Location loc = seg.cords.get(0);//makes it equal the the first position in the list of location objects
		    for (int pos = 1; pos < seg.cords.size(); pos++) { 


		    	
		    	int x1 = (int) loc.asPoint(this.origin, this.scale).getX(); 
				int y1 = (int) loc.asPoint(this.origin, this.scale).getY();
				int x2 = (int) seg.cords.get(pos).asPoint(this.origin, this.scale).getX(); //these two get the valriables that are one possition ahead of the x1 and y1
				int y2 = (int) seg.cords.get(pos).asPoint(this.origin, this.scale).getY();
		    	g.drawLine(x1,y1,x2,y2);
		    	loc = seg.cords.get(pos);
		    }
		    
		}
		
		if(searchRE.size()>0) {
			g.setColor(Color.red);
			clickNodeId = 0;
			for(int i : searchRE){
				Road r = roadsMap.get(i);
				for(Segment segm : r.getRoadSegs()){
					Location loca = segm.cords.get(0);
				    for (int j = 1; j < segm.cords.size(); j++) {
				    	int x1 = (int) loca.asPoint(this.origin, this.scale).getX(); 
						int y1 = (int) loca.asPoint(this.origin, this.scale).getY();
						int x2 = (int) segm.cords.get(j).asPoint(this.origin, this.scale).getX();
						int y2 = (int) segm.cords.get(j).asPoint(this.origin, this.scale).getY();
				    	g.drawLine(x1,y1,x2,y2);
				    	loca = segm.cords.get(j);
				    }
				}
			}
			g.setColor(Color.gray);
		}
		
		
		if (this.clickNodeId != 0) {//if there is an id in the feild
			int xPos = (int) this.nodesMap.get(clickNodeId).getLocation().asPoint(this.origin, scale).getX();
			int yPos = (int) this.nodesMap.get(clickNodeId).getLocation().asPoint(this.origin, scale).getY();
		    g.setColor(Color.red);
		    g.fillOval(xPos, yPos, 7, 7);
		    
		  //bug within this block
		    Node n = nodesMap.get(clickNodeId);
		    
		    ArrayList<String> roads = new ArrayList<String>();
		    String roadsList = "";
		    for(Segment s : n.getSegments()){
		    int ID = s.getRoadId();
		   	Road r = roadsMap.get(ID);
		   	String name = r.getRoadName();
		   	if(!roads.contains(name)){
		   	roads.add(name); // seems that nothing is being added to this list 
	 		roadsList = roadsList + (name+", "); 
	 		System.out.println("roadsList");
		    	}
		    }
		    getTextOutputArea().setText("ID of the intersection is= "+n.getNodeID()+"\n roads connected to the intersection= "+roadsList);
		}
		
	}
	@Override
	protected void onClick(MouseEvent e) {
	    Location Mouseclick = Location.newFromPoint(e.getPoint(), this.origin, this.scale);
	    Node nearNode = null; 
	    
	    double Lowdist = Double.MAX_VALUE; //used to find the node with the shortest distance to the click
	    
			for (Node n : this.nodesMap.values()) { //loop though the values of nodesmap
			    if (Mouseclick.distance(n.getLocation()) < 0.30) {
			    	
	    if (Mouseclick.distance(n.getLocation()) < Lowdist) { // if the distance of current node is closser than the dist of the previously closed node
	   			Lowdist = Mouseclick.distance(n.getLocation());
	    	    nearNode = n;
	    	   
	    	    if(this.c) {
	    	    this.clickNodeId = nearNode.getNodeID();
	    	    }
	    			}
			    }
			}
	    redraw();
	}
	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub
		String name = getSearchBox().getText();
		String RoadName = "";
		boolean exists = false;

		for (Road rd : this.roadsMap.values()) {
		    if (getSearchBox().getText().equals(rd.getRoadName())) { // if there is a road with the name matching the search add it to the array
		
		    	for(int i : searchRE) {
		    		
		    		if(roadsMap.get(i).getRoadName().equals(name)) {}
		    		exists = true;
		    	}
		    	
		    	if(exists = false) {
		    		
		    		searchRE.add(rd.getRoadId());
		    		RoadName = RoadName + rd.getRoadName();
		    		
		    	}
		    	
			
		    	
		    	
		    }   }
		if(exists) { //if there is a road with the same name
			//fix
		
			
			
		}
		
		
	
	}

	@Override
	protected void onMove(Move m) {
		// TODO Auto-generated method stub
		
		//zooming in and out
		if(m.equals(GUI.Move.ZOOM_IN)) {
			
		    this.scale += 2;

	}
		
		if(m.equals(GUI.Move.ZOOM_OUT)) {
			
			this.scale -= 2;	
			
	}
	//moving the map
		if(m.equals(GUI.Move.NORTH)) {
			this.origin = this.origin.moveBy(0, -7);
			
			
		}if(m.equals(GUI.Move.SOUTH)) {
			
			this.origin = this.origin.moveBy(0, 7);
		}
		
		if(m.equals(GUI.Move.EAST)) {
			this.origin = this.origin.moveBy(-7, 0);
			
			
		} if(m.equals(GUI.Move.WEST)) {
			
			this.origin = this.origin.moveBy(7, 0);
		}
		
		
	}
		

	@Override
	protected void onLoad(File nodes, File roads, File segments, File polygons) {
		// TODO Auto-generated method stub
		
		try {
			
			  BufferedReader re = new BufferedReader(new FileReader(nodes));
			    for (String newString = re.readLine(); newString != null; newString = re.readLine()) { //reads the current line 
			    	
				String[] currentLine = newString.split("\t"); //this will split the three parts of each line seperated by tabs and add it to the array
				
				Node currentNode = new Node(Integer.parseInt(currentLine[0]), Double.parseDouble(currentLine[1]), Double.parseDouble(currentLine[2])); // making a new node object parsing the first second and third values
				
				nodesMap.put(Integer.parseInt(currentLine[0]), currentNode); // Add the new node to the map

				//used to find the origin and scale by finding the node closest to the possition 0,0
				if (currentNode.getLocation().x < this.smallX) {
				    this.smallX = currentNode.getLocation().x;
				}
 
				if (currentNode.getLocation().y < this.smallY) {
				    this.smallY = currentNode.getLocation().y;
				}

				if (currentNode.getLocation().x > this.bigX) {
				    this.bigX = currentNode.getLocation().x;
				}

				if (currentNode.getLocation().y > this.bigY) {
				    this.bigY = currentNode.getLocation().y;
				}

				this.origin = new Location(this.smallX, this.bigY); // creates the origin location from the node closest to the position 0,0 
				this.scale = Math.min(getDrawingAreaDimension().getWidth() / (this.bigX - this.smallX), getDrawingAreaDimension().getHeight() / (this.bigY - this.smallY)); //math to figue out the scale
			    }
			    re.close();
			} catch (FileNotFoundException exception) {
			    getTextOutputArea().setText("Node File not found");
			} catch (IOException exception) {
			    System.out.println(exception);
			}
		
		
	
		
		
		try{
			Scanner sc = new Scanner(roads);
			sc.nextLine();
			while(sc.hasNext()) {
				int bike = 0;
				int ped = 0;
				String line = sc.nextLine();
				String[] spLine = line.split("\t");
				int roadId = Integer.parseInt(spLine[0]);
				int type = Integer.parseInt(spLine[1]);
				String label= spLine[2];   
				String city = spLine[3];   
				int oneway = Integer.parseInt(spLine[4]);
				int speed = Integer.parseInt(spLine[5]);
				int roadclass = Integer.parseInt(spLine[6]);
				int car= Integer.parseInt(spLine[7]);
				if(spLine.length > 7) {
					ped = Integer.parseInt(spLine[8]);
				}else {
					ped = 0;
				}
				if(spLine.length > 8) {
					bike = Integer.parseInt(spLine[9]);	
				}else {
					bike = 0;
				}
				this.roadsMap.put(roadId, new Road(roadId, type, label, city, oneway, speed, roadclass, car, ped, bike));
				
			}
			sc.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		
	
	
		
		try {
			BufferedReader re = new BufferedReader(new FileReader(segments)); 
			re.readLine(); // this will skip passed the line of  titles of the variables
			
		    for (String newString = re.readLine(); newString != null; newString = re.readLine()) {
		    
				String[] currentLine = newString.split("\t"); //this will split the three parts of each line seperated by tabs
		    	
		    	//Variables for making a segment
		    	
		    	
				int roadID = Integer.parseInt(currentLine[0]); //need to parse all the variables becaouse they are currently strings
				double length = Double.parseDouble(currentLine[1]);
				int firstNode = Integer.parseInt(currentLine[2]);
				int secondNode = Integer.parseInt(currentLine[3]);
				List<Location> cords = new ArrayList<Location>(); //array of each segments cords list 
		    	
				
				
				for (int i = 2; i + 2 < currentLine.length; i += 2) {
				    Location location = Location.newFromLatLon(Double.parseDouble(currentLine[i + 2]), Double.parseDouble(currentLine[i + 3])); //adds the corirdinateds to the two at a time as lcation objects
				    cords.add(location);
				    
					Segment segment = new Segment(roadID, length, firstNode, secondNode, cords);

					this.segments.add(segment);
				    this.roadsMap.get(roadID).addSegment(segment);
				    
				}
				
		    }

			
			
		} catch (FileNotFoundException exception) {
		    getTextOutputArea().setText("Segments File not found");
		} catch (IOException exception) {
		    System.out.println(exception);
		}
		
		
		
		
	}
	
	public static void main(String[] args){
		
		new Graph();
	}

}

