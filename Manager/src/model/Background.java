package model;

import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

public class Background implements CompendiumObject {

    public static Background parse(Node node) throws ParseException {
        BetterNodeList background = new BetterNodeList(node.getChildNodes());
        try {
            String name = background.getFirstValue("name");
            List<String> proficiencies = new ArrayList<>();
            for (String prof : background.getFirstValue("proficiency", "").split(",")){
                if (!prof.isBlank()){
                    proficiencies.add(prof.trim());
                }
            }
            List<Trait> traits = new ArrayList<>();
            for (Node trait : background.getNodes("trait")){
                traits.add(Trait.parse(trait));
            }
            return new Background(name, proficiencies, traits);
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Background definition: " + e.getMessage());
        }
    }

    private String name;
    private List<String> proficiencies;
    private List<Trait> traits;

    public Background(String name, List<String> proficiencies, List<Trait> traits) {
        this.name = name;
        this.proficiencies = proficiencies;
        this.traits = traits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(List<String> proficiencies) {
        this.proficiencies = proficiencies;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    @Override
    public Node toXML() {
        return null;
    }

}
