import java.util.*;

public class Way {

    private String id;
    private List<String> nodeList;
    private Map<String,String> tagList;

    public Way() {
        this.nodeList = new ArrayList<String>();
        this.tagList = new HashMap<String,String>();
    }

    public Way(String id) {
        this.id = id;
        this.nodeList = new ArrayList<String>();
        this.tagList = new HashMap<String,String>();
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public List<String> getNodeList() { return this.nodeList; }

    public void setNodeList(List<String> nodeList) { this.nodeList = nodeList; }

    public Map<String,String> getTagList() { return this.tagList; }

    public void setTagList(Map<String,String> tagList) { this.tagList = tagList; }

    public boolean ifHighway() {
        if(!tagList.containsKey("name"))
            return false;
        Set<String> keys = tagList.keySet();
        for(String key : keys) {
            if ((key.equals("building")) && (tagList.get(key).equals("bridge")))
                return true;
            else if ((key.equals("highway")) || (key.equals("bridge"))) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String way = new String();
        for (int i=0;i<nodeList.size();i++) {
            way = way + "nd ref="+nodeList.get(i)+"\n";
        }
        Set<String> keys = tagList.keySet();
        for(String key : keys){
            way = way +"k="+key+" v="+tagList.get(key)+"\n";
        }
        return way;
    }

}
