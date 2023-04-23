import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;
public class proj3 extends JComponent {
    //Global Variables
    Graph graph = new Graph();
	double minX = 200;
    double maxY = 0;
    double maxX = -200;
    double minY = 100;
    //Constructor
    public proj3(String file) {
        try { 
            //Initialize buffered reader in order to read text file
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String s;
            while((s=br.readLine())!=null) {
                StringTokenizer st = new StringTokenizer(s);
                //List to hold each line as its split up
                ArrayList<String> splitted = new ArrayList<String>();
                while(st.hasMoreTokens()) {
                    splitted.add(st.nextToken());
                }
                //If line starts with i, its an intersection, followed by unique string ID, and decimal representation of longitude and latitude
                if(splitted.get(0).equals("i")) {
                    //Add unique string ID, and decimal representatin of longitude and latitude
                    graph.addNode(splitted.get(1), Double.parseDouble(splitted.get(2)), Double.parseDouble(splitted.get(3)));
                    //If statements to set max or min height to inputted intersection
                    if(minX > Double.parseDouble(splitted.get(3))) {
                        minX = Double.parseDouble(splitted.get(3)); 
                    }
					if(maxX < Double.parseDouble(splitted.get(3))) {
                        maxX = Double.parseDouble(splitted.get(3));
                    }
	                if(maxY < Double.parseDouble(splitted.get(2))) {
                        maxY = Double.parseDouble(splitted.get(2));
	                }
	                if(minY > Double.parseDouble(splitted.get(2))) {
	                	minY = Double.parseDouble(splitted.get(2));
	                }
				}
                //If line starts with r, its an road, followed by a unique string ID, and the two intersections it connects
				else if(splitted.get(0).equals("r")) {
					graph.addEdge(splitted.get(1), splitted.get(2), splitted.get(3));
				}
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
    }
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        for(Edge e:graph.edges){
        	if((graph.nodes.get(e.getFrom()).isHighlighted())&&(graph.nodes.get(e.getTo()).isHighlighted())){
        		g2.setColor(Color.BLUE);
        		g2.setStroke(new BasicStroke(3.0f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        12.0f));
        	}
        	else{
        		g2.setColor(Color.BLACK);
        		g2.setStroke(new BasicStroke(1.5f,
                        BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER,
                        11.0f));
        	}
        	double x1 = (720/(maxX-minX))*((graph.nodeCoordinates.get(e.getFrom())[0])-minX)+100;
        	double x2 = (720/(maxX-minX))*((graph.nodeCoordinates.get(e.getTo())[0])-minX)+100;
        	double y1 = (-720/(maxY-minY))*((graph.nodeCoordinates.get(e.getFrom())[1])-maxY)+100;
        	double y2 = (-720/(maxY-minY))*((graph.nodeCoordinates.get(e.getTo())[1])-maxY)+100;
        	Line2D line = new Line2D.Double(x1,y1,x2,y2);
        	g2.draw(line);
        }
    }
    public Dimension getPreferredSize(){
        return new Dimension(1080,1080);
    }
    public static void main(String[] args){
        String fileName = "ur.txt";
        if(Arrays.asList(args).contains("ur.txt")){
            fileName = "ur.txt";
        }
        if(Arrays.asList(args).contains("monroe.txt")){
            fileName = "monroe.txt";
        }
        if(Arrays.asList(args).contains("nys.txt")){
            fileName = "nys.txt";
        }
    	proj3 test1 = new proj3(fileName);
    	test1.graph.modifyGraph();
        if(Arrays.asList(args).contains("show")){
            if(Arrays.asList(args).contains("directions")){
                String from = args[3];
                String to = args[4];
                ArrayList<String> temp = (ArrayList<String>) test1.graph.getShortestPath(from, to);
                if(temp.size()==0){
                	System.out.println("No path between "+from+" and "+to+ ".");
                }
                else{
                	System.out.println("Shortest path: "+temp);
                    System.out.println("Total distance: "+ test1.graph.nodes.get(to).getDistance()+" miles.");
                }
            }
            JFrame frame = new JFrame("test");
            frame.add(test1);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        else if(Arrays.asList(args).contains("directions")){
            String from = args[2];
            String to = args[3];
            ArrayList<String> temp = (ArrayList<String>) test1.graph.getShortestPath(from, to);
            if(temp.size()==0){
            	System.out.println("No path between "+from+" and "+to+ ".");
            }
            else{
            	System.out.println("Shortest path: "+temp);
                System.out.println("Total distance: "+ test1.graph.nodes.get(to).getDistance()+" miles.");
            }
        }
        /* Test Run. Uncomment to show map of input file.
        JFrame frame = new JFrame("test");
        frame.add(test1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
    }
}

