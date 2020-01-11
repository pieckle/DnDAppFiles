package model;

import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.parseIntNode;

public class Clazz implements CompendiumObject {

    public static Clazz parse(Node node) throws ParseException {
        BetterNodeList clazz = new BetterNodeList(node.getChildNodes());
        try {
            String name = clazz.getFirstValue("name");
            int hitDice = parseIntNode(clazz, "hd");
            List<Ability> saveAbilities = new ArrayList<>();
            for (String term : clazz.getFirstValue("proficiency").split(", ")){
                saveAbilities.add(Ability.valueOf(term));
            }
            Ability spellAbility = Ability.valueOf(clazz.getFirstValue("spellAbility"));
            List<LevelFeature> levelFeatures = new ArrayList<>();
            for (Node autolvl : clazz.getNodes("autolevel")){
                try {
                    levelFeatures.add(LevelFeature.parse(autolvl));
                }
                catch (ParseException e){
                    throw new ParseException("Bad autolevel in class " + name + ": " + e.getMessage());
                }
            }
            return new Clazz(
                    name,
                    hitDice,
                    saveAbilities,
                    spellAbility,
                    levelFeatures
            );
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Class definition: " + e.getMessage());
        }
    }

    private String name;
    private int hitDice;
    private List<Ability> saveProficiencies;
    private Ability spellAbility;
    private List<LevelFeature> levelFeatures;

    public Clazz(String name, int hitDice, List<Ability> saveProficiencies, Ability spellAbility, List<LevelFeature> levelFeatures) {
        this.name = name;
        this.hitDice = hitDice;
        this.saveProficiencies = saveProficiencies;
        this.spellAbility = spellAbility;
        this.levelFeatures = levelFeatures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitDice() {
        return hitDice;
    }

    public void setHitDice(int hitDice) {
        this.hitDice = hitDice;
    }

    public List<Ability> getSaveProficiencies() {
        return saveProficiencies;
    }

    public void setSaveProficiencies(List<Ability> saveProficiencies) {
        this.saveProficiencies = saveProficiencies;
    }

    public Ability getSpellAbility() {
        return spellAbility;
    }

    public void setSpellAbility(Ability spellAbility) {
        this.spellAbility = spellAbility;
    }

    public List<LevelFeature> getLevelFeatures() {
        return levelFeatures;
    }

    public void setLevelFeatures(List<LevelFeature> levelFeatures) {
        this.levelFeatures = levelFeatures;
    }

    @Override
    public Node toXML() {
        return null;
    }

}
