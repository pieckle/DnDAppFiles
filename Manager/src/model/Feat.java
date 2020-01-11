package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.addTextNode;
import static model.Parser.checkUnused;

public class Feat implements CompendiumObject {

    public static Feat parse(Node node) throws ParseException {
        BetterNodeList feat = new BetterNodeList(node);
        try {
            String name = feat.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : feat.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            String prerequisite = feat.getFirstValue("prerequisite", "");
            List<Modifier> modifiers = new ArrayList<>();
            for (Node mod : feat.getNodes("modifier")){
                modifiers.add(Modifier.parse(mod));
            }
            checkUnused(feat);
            return new Feat(name, text.toString(), prerequisite, modifiers);
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Feat definition: " + e.getMessage());
        }
    }

    private String name;
    private String text;
    private String prerequisite;
    private List<Modifier> modifiers;

    public Feat(String name, String text, String prerequisite, List<Modifier> modifiers) {
        this.name = name;
        this.text = text;
        this.prerequisite = prerequisite;
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

    public String getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(String prerequisite) {
        this.prerequisite = prerequisite;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("feat");
        addTextNode(doc, out, "name", this.name);
        if (!prerequisite.isEmpty()){
            addTextNode(doc, out, "prerequisite", prerequisite);
        }
        for (String text : this.text.split("\n")){
            addTextNode(doc, out, "text", text);
        }
        return out;
    }

}
