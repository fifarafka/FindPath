import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.HashMap;

public class SaxHandler extends DefaultHandler {
    private HashMap<String,Node> nodeList;
    private HashMap <String,Way> wayList;
    private String wayId;
    private boolean startToReadWay;

    public SaxHandler() {
        this.nodeList = new HashMap<String,Node>();
        this.wayList = new HashMap<String,Way>();
        this.startToReadWay = false;
    }

    public HashMap<String,Node> getNodeList() {
        return this.nodeList;
    }

    public HashMap<String,Way> getWayList() {
        return this.wayList;
    }

    public void startDocument() {
        System.out.println("Parsing. Started...");
    }

    public void endDocument() {
        System.out.println("Parsing. End.");
    }

    public void startElement(String namespaceURL, String localName, String qName, Attributes atts) {
        if (qName.equals("node")) {
            String nodeId = new String(atts.getValue("id"));
            Double lalitude = new Double(Double.parseDouble(atts.getValue("lat")));
            Double longitude = new Double(Double.parseDouble(atts.getValue("lon")));
            Node w = new Node(nodeId, lalitude, longitude);
            nodeList.put(nodeId,w);
        }
        if (qName.equals("way")) {
            wayId = new String(atts.getValue("id"));
            wayList.put(wayId, new Way(wayId));
            startToReadWay = true;
        }
        if (qName.equals("nd")) {
            String nodeId = new String(atts.getValue("ref"));
            wayList.get(wayId).getNodeList().add(nodeId);
        }
        if (qName.equals("tag"))
            if (startToReadWay) {
                String k_tag = new String(atts.getValue("k"));
                String v_tag = new String(atts.getValue("v"));
                wayList.get(wayId).getTagList().put(k_tag, v_tag);
            }
    }

    public void endElement(String namespaceURL, String localName, String qName, Attributes atts) {
        if (qName.equals("way"))
            startToReadWay = false;
    }

}
