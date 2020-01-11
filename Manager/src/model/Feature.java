package model;

import org.w3c.dom.Node;
import util.BetterNodeList;

public class Feature implements CompendiumObject {

    public static Feature parse(Node node) throws ParseException {
        BetterNodeList trait = new BetterNodeList(node.getChildNodes());
        try {
            String name = trait.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : trait.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            return new Feature(name, text.toString());
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Trait definition: " + e.getMessage());
        }
    }

    private String name;
    private String text;

    public Feature(String name, String text){
        this.name = name;
        this.text = text;
    }

    public String getName(){
        return name;
    }

    public String getText(){
        return text;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public Node toXML() {
        return null;
    }

}
