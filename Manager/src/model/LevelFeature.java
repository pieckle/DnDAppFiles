package model;

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
        BetterNodeList lvlFeat = new BetterNodeList(node.getChildNodes());
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
    public Node toXML() {
        return null;
    }

}
