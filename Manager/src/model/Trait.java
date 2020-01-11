package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import static model.Parser.addTextNode;

public class Trait implements CompendiumObject {

    public static Trait parse(Node node) throws ParseException {
        BetterNodeList trait = new BetterNodeList(node);
        try {
            String name = trait.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : trait.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            return new Trait(name, text.toString());
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Trait definition: " + e.getMessage());
        }
    }

    private String name;
    private String text;

    public Trait(String name, String text){
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
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("trait");
        addTextNode(doc, out, "name", this.name);
        for (String text : this.text.split("\n")){
            addTextNode(doc, out, "text", text);
        }
        return out;
    }

}
