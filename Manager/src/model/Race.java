package model;

import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.parseIntNode;

public class Race implements CompendiumObject {

    public static Race parse(Node node) throws ParseException {
        BetterNodeList race = new BetterNodeList(node.getChildNodes());
        try {
            String name = race.getFirstValue("name");
            Size size = Size.fromAbbreviation(race.getFirstValue("size"));
            int speed = parseIntNode(race, "speed");
            List<AbilityBonus> abs = new ArrayList<>();
            for (String ab : race.getFirstValue("ability").split(",")){
                abs.add(AbilityBonus.parse(ab.trim()));
            }
            List<Trait> traits = new ArrayList<>();
            for (Node trait : race.getNodes("trait")){
                traits.add(Trait.parse(trait));
            }
            return new Race(
                    name,
                    size,
                    speed,
                    abs,
                    traits
            );
        }
        catch (IllegalArgumentException | ParseException e){
            throw new ParseException("Incomplete Race definition: " + e.getMessage());
        }
    }

    private String name;
    private Size size;
    private int speed;
    private List<AbilityBonus> abilities;
    private List<Trait> traits;

    public Race(String name, Size size, int speed, List<AbilityBonus> abilities, List<Trait> traits) {
        this.name = name;
        this.size = size;
        this.speed = speed;
        this.abilities = abilities;
        this.traits = traits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public List<AbilityBonus> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilityBonus> abilities) {
        this.abilities = abilities;
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
