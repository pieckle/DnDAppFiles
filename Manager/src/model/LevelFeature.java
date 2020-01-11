package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

public class LevelFeature implements CompendiumObject {

    public static LevelFeature parse(Node node) throws ParseException {
        if (node.getAttributes().getNamedItem("level") == null){
            throw new ParseException("Failed to parse LevelFeature: no defined level");
        }
        int level = Integer.parseInt(node.getAttributes().getNamedItem("level").getTextContent());
        BetterNodeList lvlFeat = new BetterNodeList(node);
        List<Integer> slots = null;
        if (lvlFeat.hasNode("slots")){
            slots = new ArrayList<>();
            for (String slot : lvlFeat.getFirstValue("slots").split(",")){
                slots.add(Integer.parseInt(slot.trim()));
            }
        }
        List<Feature> features = null;
        if (lvlFeat.hasNode("feature")){
            features = new ArrayList<>();
            for (Node feat : lvlFeat.getNodes("feature")){
                try {
                    features.add(Feature.parse(feat));
                }
                catch (ParseException e){
                    throw new ParseException("Bad Feature in levelFeature: " + e.getMessage());
                }
            }
        }
        return new LevelFeature(level, features, slots);
    }

    private int level;
    private List<Integer> slots;
    private List<Feature> features;

    public LevelFeature(int level, List<Feature> features, List<Integer> slots) {
        this.level = level;
        this.features = features;
        this.slots = slots;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean hasSlots(){
        return slots != null;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }

    public boolean hasFeatures(){
        return features != null;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString(){
        return "autolevel" + level;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("autolevel");
        out.setAttribute("level", level + "");
        if (slots != null){
            Element slots = doc.createElement("slots");
            String txt = "";
            for (int i : this.slots){
                txt += i + ",";
            }
            if (!txt.isEmpty()) {
                txt = txt.substring(0, txt.length() - 1);
            }
            slots.appendChild(doc.createTextNode(txt));
        }
        else if (features != null){
            for (Feature feature : features){
                out.appendChild(feature.toXML(doc));
            }
        }
        return out;
    }

}
