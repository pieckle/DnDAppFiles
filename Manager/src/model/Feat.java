package model;

import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

public class Feat implements CompendiumObject {

    public static Feat parse(Node node) throws ParseException {
        BetterNodeList feat = new BetterNodeList(node.getChildNodes());
        try {
            String name = feat.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : feat.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            List<Modifier> modifiers = new ArrayList<>();
            for (Node mod : feat.getNodes("modifier")){
                modifiers.add(Modifier.parse(mod));
            }
            return new Feat(name, text.toString(), modifiers);
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Feat definition: " + e.getMessage());
        }
    }

    private String name;
    private String text;

    public Feat(String name, String text, List<Modifier> modifiers) {
        this.name = name;
        this.text = text;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    private List<Modifier> modifiers;

    @Override
    public Node toXML() {
        return null;
    }

}
