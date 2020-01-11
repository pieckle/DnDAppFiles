package util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class BetterNodeList extends ArrayList<Node> {

    private String name;
    private Map<String, List<Node>> nodes;
    private Set<String> keys;

    public BetterNodeList(Node node){
        this.name = node.getNodeName();
        NodeList list  = node.getChildNodes();
        nodes = new TreeMap<>();
        for (int i = 0; i < list.getLength(); i++){
            Node entry = list.item(i);
            if (entry.getNodeType() == Node.ELEMENT_NODE){
                if (!nodes.containsKey(entry.getNodeName())){
                    nodes.put(entry.getNodeName(), new ArrayList<Node>());
                }
                nodes.get(entry.getNodeName()).add(entry);
                add(entry);
            }
        }
        keys = new TreeSet<>();
        keys.addAll(nodes.keySet());
    }

    public int size(){
        return nodes.size();
    }

    public boolean hasNode(String tag){
        return nodes.containsKey(tag);
    }

    public List<Node> getNodes(String tag){
        keys.remove(tag);
        if (hasNode(tag)) {
            return nodes.get(tag);
        }
        else {
            return Collections.emptyList();
        }
    }

    public BetterNodeList getFirstChild(String tag){
        keys.remove(tag);
        if (hasNode(tag)){
            return new BetterNodeList(getNodes(tag).get(0));
        }
        throw new IllegalArgumentException("Node \"" + tag + "\" not found");
    }

    public String getFirstValue(String tag){
        keys.remove(tag);
        if (hasNode(tag)){
            return getNodes(tag).get(0).getTextContent();
        }
        throw new IllegalArgumentException("Node \"" + tag + "\" not found");
    }

    public String getFirstValue(String tag, String def){
        keys.remove(tag);
        if (hasNode(tag)){
            return getNodes(tag).get(0).getTextContent();
        }
        else {
            return def;
        }
    }

    public String getName(){
        return name;
    }

    public Set<String> getUnusedNodes(){
        return Collections.unmodifiableSet(keys);
    }

}
