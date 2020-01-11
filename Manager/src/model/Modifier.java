package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Modifier implements CompendiumObject {

    public static Modifier parse(Node node) throws ParseException {
        if (node.getAttributes().getNamedItem("category") == null){
            throw new ParseException("Failed to parse Modifier: no defined category");
        }
        String cat = node.getAttributes().getNamedItem("category").getTextContent();
        String text = node.getTextContent();
        return new Modifier(cat, text);
    }

    private String category;
    private String text;

    public Modifier(String category, String text){
        this.category = category;
        this.text = text;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("modifier");
        out.setAttribute("category", category);
        out.appendChild(doc.createTextNode(text));
        return out;
    }

}
