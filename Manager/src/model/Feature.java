package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.addTextNode;
import static model.Parser.checkUnused;

public class Feature implements CompendiumObject {

    public static Feature parse(Node node) throws ParseException {
        BetterNodeList feature = new BetterNodeList(node);
        try {
            String name = feature.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : feature.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            boolean optional = node.getAttributes().getNamedItem("optional") != null &&
                    node.getAttributes().getNamedItem("optional").getTextContent().equalsIgnoreCase("YES");
            List<String> proficiencies = new ArrayList<>();
            if (feature.hasNode("proficiency")){
                for (String prof : feature.getFirstValue("proficiency").split(", ")){
                    proficiencies.add(prof);
                }
            }
            List<Modifier> modifiers = new ArrayList<>();
            for (Node mod : feature.getNodes("modifier")){
                modifiers.add(Modifier.parse(mod));
            }
            checkUnused(feature);
            return new Feature(name, text.toString(), optional, proficiencies, modifiers);
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Trait definition: " + e.getMessage());
        }
    }

    private String name;
    private String text;
    private boolean optional;
    private List<String> proficiencies;
    private List<Modifier> modifiers;

    public Feature(String name, String text, boolean optional, List<String> proficiencies, List<Modifier> modifiers){
        this.name = name;
        this.text = text;
        this.optional = optional;
        this.proficiencies = proficiencies;
        this.modifiers = modifiers;
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

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public List<String> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<String> proficiencies) {
        this.proficiencies = proficiencies;
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
        Element out = doc.createElement("feature");
        addTextNode(doc, out, "name", this.name);
        for (String text : this.text.split("\n")){
            addTextNode(doc, out, "text", text);
        }
        if (optional){
            out.setAttribute("optional", "YES");
        }
        if (!proficiencies.isEmpty()) {
            String profs = "";
            for (String prof : proficiencies) {
                profs += prof + ", ";
            }
            profs = profs.substring(0, profs.length() - 2);
            addTextNode(doc, out, "proficiency", profs);
        }
        for (Modifier mod : modifiers){
            out.appendChild(mod.toXML(doc));
        }
        return out;
    }

}
