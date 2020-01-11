package model;

import org.w3c.dom.Node;
import util.BetterNodeList;

public class Action implements CompendiumObject {

    public static Action parse(Node node) throws ParseException {
        BetterNodeList action = new BetterNodeList(node.getChildNodes());
        try {
            String name = action.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : action.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            return new Action(name, text.toString());
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Action definition: " + e.getMessage());
        }
    }

    private String name;
    private String text;

    public Action(String name, String text){
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
