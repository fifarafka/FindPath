import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.*;

public class Parser {
    private Map <String,Node> nodeMap;
    private Map <String,Way> allWaysList;
    private Map <String,Way> wayList;
    private List<Node> nodeList;

    public Parser() {
        try {
            Serializer serializer = new Serializer();
            XMLReader reader = XMLReaderFactory.createXMLReader();
            SaxHandler handler = new SaxHandler();
            reader.setContentHandler(handler);
            reader.parse((String)serializer.deserialize("CracowMap"));
            this.nodeMap = handler.getNodeList();
            this.allWaysList = handler.getWayList();
            this.wayList = new HashMap<String, Way>();
            this.nodeList = new ArrayList<Node>();
            makeNodeList();
        } catch (SAXException e) {
            System.out.println("SAXException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    public List<Node> getNodeList() { return this.nodeList; }

    private void makeNodeList() {
        deleteUnnesessaryElements();
        addAllStreetNames();
        String startNodeID;
        String endNodeID;
        Set<String> keys= wayList.keySet();
        for(String key : keys) {
            Node startNode;
            Node endNode;
            startNodeID = wayList.get(key).getNodeList().get(0);
            endNodeID = wayList.get(key).getNodeList().get(wayList.get(key).getNodeList().size() - 1);
            startNode = nodeMap.get(startNodeID);
            endNode = nodeMap.get(endNodeID);
            startNode.getNeighbours().add(endNode);
            endNode.getNeighbours().add(startNode);
            nodeList.add(startNode);
            nodeList.add(endNode);
        }
    }

    private void deleteUnnesessaryElements() {
        boolean ifHighway;
        Set<String> keys = allWaysList.keySet();
        for(String key : keys){
            ifHighway = allWaysList.get(key).ifHighway();
            if (ifHighway) {
                wayList.put(key, allWaysList.get(key));
            }
        }
    }

    private void addAllStreetNames() {
        Set<String> keys = wayList.keySet();
        for(String key : keys){
            String name = wayList.get(key).getTagList().get("name");
            addStreetName(name, key);
        }
    }

    private void addStreetName(String name, String streetID) {
        for (int i=0;i<wayList.get(streetID).getNodeList().size();i++) {
            String nodeID = wayList.get(streetID).getNodeList().get(i);
            if (!nodeMap.get(nodeID).getWayList().contains(name))
                nodeMap.get(nodeID).addWay(name);
        }
    }

}
