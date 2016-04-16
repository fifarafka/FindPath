import java.util.*;

public class Graph {
    private List<Node> nodes;
    private List<Node> startNodes;
    private List<Node> endNodes;
    private PriorityQueue<Node> dijkstraQueue;

    public Graph(List<Node> nodes) {
        this.nodes = nodes;
        this.startNodes = new ArrayList<Node>();
        this.endNodes = new ArrayList<Node>();
        this.dijkstraQueue = new PriorityQueue<Node>();
    }

    public void findPath(String start, String end) {
        setStartEndNodes(start,end);
        Node source = null;
        Node target=null;
        boolean pathFinded = false;
        if ((!startNodes.isEmpty())&&(!endNodes.isEmpty())) {
            for (Node n : startNodes) {
                source  = n;
                dijkstraAlgorythm(n);
                for (Node z : endNodes) {
                    target = z;
                    if (isPathExists(z)) {
                        pathFinded = true;
                        break;
                    }
                }
                if (pathFinded)
                    break;
            }
        }

        System.out.println(start+" -> "+end);
        if (pathFinded) {
            System.out.println("Path exists");
            showPath(start,end,source ,target);
        }
        else
            System.out.println("Path or entered street doesn't exists");
    }

    private void setStartEndNodes(String startStreet, String endStreet) {
        startNodes.clear();
        endNodes.clear();
        for (Node node : nodes) {
            if (node.isOnStreet(startStreet))
                startNodes.add(node);
            if (node.isOnStreet(endStreet))
                endNodes.add(node);
        }
    }

    private double calculateDistance(Node n1, Node n2){
        double lalitudeDifference=+(n1.getLalitude()-n2.getLalitude());
        lalitudeDifference*=(111.1);
        double longitudeDifference=+(n1.getLongitude()-n2.getLongitude());
        longitudeDifference*=(111.1);
        return Math.sqrt((lalitudeDifference*lalitudeDifference)+(longitudeDifference*longitudeDifference));
    }

    public Node getNode(String id) {
        Node node = null;
        for (Node n : nodes) {
            if (n.getNodeId().equals(id))
                node = n;
        }
        return node;
    }

    private void clear() {
        for (Node w : nodes) {
            w.setDistance(Double.MAX_VALUE);
            w.setPredecessor(null);
        }


    }

    public void dijkstraAlgorythm(Node source) {
        clear();
        source.setDistance(0.0);
        dijkstraQueue.add(source);
        while (!dijkstraQueue.isEmpty()) {
            Node w = dijkstraQueue.poll();
            for (Node neighbour : w.getNeighbours()) {
                Double edgeWeight = new Double(calculateDistance(w,neighbour));
                if ((w.getDistance()+edgeWeight)<(neighbour.getDistance())) {
                    neighbour.setDistance(edgeWeight+w.getDistance());
                    neighbour.setPredecessor(w);
                    dijkstraQueue.add(neighbour);
                }
            }
        }
    }

    private List<String> setPath(Node target) {
        List<String> path = new ArrayList<String>();
        List<String> ways = new ArrayList<String>();
        List<String> ways2 = new ArrayList<String>();
        Node current = target;
        while(current.getPredecessor()!=null) {
            ways.addAll(current.getWayList());
            ways2.addAll(current.getPredecessor().getWayList());
            ways.retainAll(ways2);
            if (!path.contains(ways.get(0)))
                path.add(ways.get(0));
            current=current.getPredecessor();
            ways.clear();
            ways2.clear();
        }
        Collections.reverse(path);
        return path;
    }

    private boolean isPathExists(Node target) {
        if (target.getDistance()<Double.MAX_VALUE)
            return true;
        else
            return false;
    }

    private void showPath(String start, String end, Node source, Node target) {
        List<String> path = setPath(target);
        System.out.println("Distance: "+target.getDistance()+"km");
        System.out.println("Path:");
        if (!path.get(0).equals(start))
            System.out.println(start);
        for(int i=0;i<path.size();i++)
            System.out.println(path.get(i));
        if(!path.get(path.size()-1).equals(end))
            System.out.println(end);
    }

}