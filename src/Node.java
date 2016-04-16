import java.util.*;
import java.io.*;

public class Node implements Comparable<Node> {

    private String nodeId;
    private List<String> wayList;
    private double lalitude;
    private double longitude;
    private List<Node> neighbours;
    private Node predecessor = null;
    private double distance = new Double(Double.MAX_VALUE);

    public Node() {
        this.wayList = new ArrayList<String>();
        this.neighbours = new ArrayList<Node>();
    }

    public Node(String id) {
        this.nodeId = id;
        this.wayList = new ArrayList<String>();
        this.neighbours = new ArrayList<Node>();
    }

    public Node(String id, double lalitude, double longitude) {
        this.nodeId = new String(id);
        this.lalitude = lalitude;
        this.longitude = longitude;
        this.wayList = new ArrayList<String>();
        this.neighbours = new ArrayList<Node>();
    }

    public void addWay(String way) {
        wayList.add(way);
    }

    public boolean isOnStreet(String way) {
        return wayList.contains(way);
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setLalitude(double lalitude) {
        this.lalitude = lalitude;
    }

    public double getLalitude() {   return this.lalitude; }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() { return this.longitude; }

    public void setPredecessor(Node n) { this.predecessor = n; }

    public Node getPredecessor() {  return this.predecessor; }

    public void setDistance(double distance) { this.distance = distance; }

    public double getDistance() {return this.distance; }

    public List<Node> getNeighbours() { return this.neighbours; }

    public List<String> getWayList() {  return this.wayList; }

    public int compareTo(Node node) {
        if (this.distance < node.distance)
            return -1;
        else {
            if (this.distance > node.distance)
                return 1;
            else
                return 0;
        }
    }

    public String toString() {
        String node = new String();
        node = node + nodeId + "\n" + lalitude + "\n" + longitude + "\n";
        for (int i = 0; i < wayList.size(); i++)
            node = node + wayList.get(i) + " ";
        return node;
    }
}