import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;
public class proj3 extends JComponent {
    //Global Variables
    Graph graph = new Graph();
	double minX = 500;
    double maxY = 0;
    double maxX = -500;
    double minY = 500;
    //Constructor
    public proj3(String file) {
        try { 
            //Initialize buffered reader in order to read text file
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String input;
            while((input=br.readLine())!=null) {
                StringTokenizer tokenizedInput = new StringTokenizer(input);
                //List to hold each line as its split up
                ArrayList<String> splitParts = new ArrayList<String>();
                while((tokenizedInput.hasMoreTokens()) && (tokenizedInput != null)) {
                    splitParts.add(tokenizedInput.nextToken());
                }
                String type = splitParts.get(0);
                String id = splitParts.get(1);
                String strLatitude = splitParts.get(2);
                String strLongitude = splitParts.get(3);
                //If line starts with i, its an intersection, followed by unique string ID, and decimal representation of longitude and latitude
                if(type.equals("i")) {
                    //Add unique string ID, and decimal representatin of longitude and latitude
                    graph.addNode(id, (Double.parseDouble(splitParts.get(2))), (Double.parseDouble(splitParts.get(3))));
                    //If statements to set max or min height to inputted intersection
                    if(minX > (Double.parseDouble(splitParts.get(3)))) {
                        minX = (Double.parseDouble(splitParts.get(3))); 
                    }
					if(maxX < (Double.parseDouble(splitParts.get(3)))) {
                        maxX = (Double.parseDouble(splitParts.get(3)));
                    }
	                if(maxY < (Double.parseDouble(splitParts.get(2)))) {
                        maxY = (Double.parseDouble(splitParts.get(2)));
	                }
	                if(minY > (Double.parseDouble(splitParts.get(2)))) {
	                	minY = (Double.parseDouble(splitParts.get(2)));
	                }
				}
                //If line starts with r, its an road, followed by a unique string ID, and the two intersections it connects
				else if(type.equals("r")) {
					graph.addEdge(id, strLatitude, strLongitude);
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
        for(Edge e : graph.edges){
            boolean fromHighlight = (graph.nodes.get(e.getFrom()).isHighlighted());
            boolean toHighlight = (graph.nodes.get(e.getTo()).isHighlighted());
        	if((fromHighlight) && (toHighlight)){
        		g2.setColor(Color.BLUE);
        		g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 12.0f));
        	}
        	else{
        		g2.setColor(Color.BLACK);
        		g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 11.0f));
        	}
            //Math Variables
            double hundred = 100;
            double xCalc = (720 / (maxX - minX));
            double xFromCalc = ((graph.nodeCoordinates.get(e.getFrom())[0]) - minX);
            double xToCalc = ((graph.nodeCoordinates.get(e.getTo())[0]) - minX);
            double yCalc = (-720 / (maxY - minY));
            double yFromCalc = ((graph.nodeCoordinates.get(e.getFrom())[1]) - maxY);
            double yToCalc = ((graph.nodeCoordinates.get(e.getTo())[1]) - maxY);
            //Math
        	double x1 = (xCalc) * (xFromCalc) + hundred;
        	double x2 = (xCalc) * (xToCalc) + hundred;
        	double y1 = (yCalc) * (yFromCalc) + hundred;
        	double y2 = (yCalc) * (yToCalc) + hundred;
        	Line2D line = new Line2D.Double(x1, y1, x2, y2);
        	g2.draw(line);
        }
    }
    public Dimension getPreferredSize(){
        return new Dimension(1080,1080);
    }
    public static void main(String[] args){
        String fileName = "ur.txt"; //default file
        //File reading for command line operations
        if(Arrays.asList(args).contains("ur.txt")){
            fileName = "ur.txt";
        }
        if(Arrays.asList(args).contains("monroe.txt")){
            fileName = "monroe.txt";
        }
        if(Arrays.asList(args).contains("nys.txt")){
            fileName = "nys.txt";
        }
        //Create street mapping object
    	proj3 test1 = new proj3(fileName);
    	test1.graph.modifyGraph();
        //If show then create frame to display
        if(Arrays.asList(args).contains("show")){
            if(Arrays.asList(args).contains("directions")){
                String from = args[3];
                String to = args[4];
                ArrayList<String> temp = (ArrayList<String>) test1.graph.getShortestPath(from, to);
                if(temp.size() == 0){
                	System.out.println("No path between " + from + " and " + to +  ".");
                }
                else{
                	System.out.println("The shortest path " + from + " to " + to + " is " + temp);
                    System.out.println("Total distance traveled is " + test1.graph.nodes.get(to).getDistance() + " miles.");
                }
            }
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new JFrame("Street Mapping");
            frame.add(test1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        //If directions then just print directions to console
        else if(Arrays.asList(args).contains("directions")){
            String from = args[2];
            String to = args[3];
            ArrayList<String> temp = (ArrayList<String>) test1.graph.getShortestPath(from, to);
            if(temp.size() == 0){
            	System.out.println("No path between " + from + " and " + to + ".");
            }
            else{
            	System.out.println("The shortest path " + from + " to " + to + " is " + temp);
                System.out.println("Total distance traveled is " + test1.graph.nodes.get(to).getDistance() + " miles.");
            }
        }
        /* Test Run. Uncomment to show map of input file.
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Street Mapping");
        frame.add(test1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
    }
}

