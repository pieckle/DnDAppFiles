package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.addTextNode;
import static model.Parser.parseIntNode;

public class Clazz implements CompendiumObject {

    public static Clazz parse(Node node) throws ParseException {
        BetterNodeList clazz = new BetterNodeList(node);
        try {
            String name = clazz.getFirstValue("name");
            int hitDice = parseIntNode(clazz, "hd");
            List<Ability> saveAbilities = new ArrayList<>();
            for (String term : clazz.getFirstValue("proficiency").split(", ")){
                saveAbilities.add(Ability.valueOf(term));
            }
            Ability spellAbility = null;
            if (clazz.hasNode("spellAbility")) {
                spellAbility = Ability.valueOf(clazz.getFirstValue("spellAbility"));
            }
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
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("class");
        addTextNode(doc, out, "name", name);
        addTextNode(doc, out, "hd", hitDice);
        String saves = "";
        for (Ability prof : saveProficiencies){
            saves += prof.name() + ", ";
        }
        if (!saves.isEmpty()){
            saves = saves.substring(0, saves.length() - 2);
        }
        addTextNode(doc, out, "proficiency", saves);
        if (spellAbility != null) {
            addTextNode(doc, out, "spellAbility", spellAbility.getAbbreviation());
        }
        for (LevelFeature feat : levelFeatures){
            doc.appendChild(feat.toXML(doc));
        }
        return out;
    }

}
