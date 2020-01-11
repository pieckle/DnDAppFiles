package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.*;

public class Monster implements CompendiumObject {

    public static Monster parse(Node node) throws ParseException {
        BetterNodeList monster = new BetterNodeList(node);
        try {
            String name = monster.getFirstValue("name");
            Size size = Size.fromAbbreviation(monster.getFirstValue("size"));
            String type = monster.getFirstValue("type");
            String alignment = monster.getFirstValue("alignment");
            String armorClass = monster.getFirstValue("ac");
            String hitPoints = monster.getFirstValue("hp");
            String speed = monster.getFirstValue("speed");
            int strength = parseIntNode(monster, "str");
            int dexterity = parseIntNode(monster, "dex");
            int constitution = parseIntNode(monster, "con");
            int intelligence = parseIntNode(monster, "int");
            int wisdom = parseIntNode(monster, "wis");
            int charisma = parseIntNode(monster, "cha");
            String saves = monster.getFirstValue("save", "");
            String skills = monster.getFirstValue("skill", "");
            String vulnerabilities = monster.getFirstValue("vulnerable", "");
            String resistances = monster.getFirstValue("resist", "");
            String dmgImmunities = monster.getFirstValue("immune", "");
            String condImmunities = monster.getFirstValue("conditionImmune", "");
            String senses = monster.getFirstValue("senses", "");
            int passivePerception = parseIntNode(monster, "passive");
            String languages = monster.getFirstValue("languages", "");
            String challengeRating = monster.getFirstValue("cr");
            List<Trait> traits = new ArrayList<>();
            for (Node trait : monster.getNodes("trait")){
                traits.add(Trait.parse(trait));
            }
            List<Action> actions = new ArrayList<>();
            for (Node action : monster.getNodes("action")){
                actions.add(Action.parse(action));
            }
            for (Node reaction : monster.getNodes("reaction")){
                actions.add(Action.parse(reaction));
            }
            for (Node legendary : monster.getNodes("legendary")){
                actions.add(Action.parse(legendary));
            }
            List<Integer> slots = new ArrayList<>();
            if (monster.hasNode("slots") && !monster.getFirstValue("slots").isBlank()){
                try {
                    for (String slot : monster.getFirstValue("slots").split(",")) {
                        slots.add(Integer.parseInt(slot));
                    }
                }
                catch (NumberFormatException e){
                    throw new IllegalArgumentException("bad slots format");
                }
            }
            List<String> spells = new ArrayList<>();
            if (monster.hasNode("spells")){
                for (String spell : monster.getFirstValue("spells").split(", ")){
                    spells.add(spell);
                }
            }
            String description = monster.getFirstValue("description", "");
            checkUnused(monster);
            return new Monster(
                    name,
                    size,
                    type,
                    alignment,
                    armorClass,
                    hitPoints,
                    speed,
                    strength,
                    dexterity,
                    constitution,
                    intelligence,
                    wisdom,
                    charisma,
                    saves,
                    skills,
                    resistances,
                    vulnerabilities,
                    dmgImmunities,
                    condImmunities,
                    senses,
                    passivePerception,
                    languages,
                    challengeRating,
                    traits,
                    actions,
                    slots,
                    spells,
                    description
            );
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Monster definition: " + e.getMessage());
        }
    }

    private String name;
    private Size size;
    private String type;
    private String alignment;
    private String armorClass;
    private String hitPoints;
    private String speed;
    private int strength, dexterity, constitution, intelligence, wisdom, charisma;
    private String saves;
    private String skills;
    private String vulnerabilities;
    private String resistances;
    private String dmgImmunities;
    private String condImmunities;
    private String senses;
    private int passivePerception;
    private String languages;
    private String challengeRating;
    private List<Trait> traits;
    private List<Integer> slots;
    private List<String> spells;
    private String description;

    public Monster(String name,
                   Size size,
                   String type,
                   String alignment,
                   String armorClass,
                   String hitPoints,
                   String speed,
                   int strength,
                   int dexterity,
                   int constitution,
                   int intelligence,
                   int wisdom,
                   int charisma,
                   String skills,
                   String saves,
                   String vulnerabilities,
                   String resistances,
                   String dmgImmunities,
                   String condImmunities,
                   String senses,
                   int passivePerception,
                   String languages,
                   String challengeRating,
                   List<Trait> traits,
                   List<Action> actions,
                   List<Integer> slots,
                   List<String> spells,
                   String description) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.alignment = alignment;
        this.armorClass = armorClass;
        this.hitPoints = hitPoints;
        this.speed = speed;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.saves = saves;
        this.skills = skills;
        this.vulnerabilities = vulnerabilities;
        this.resistances = resistances;
        this.dmgImmunities = dmgImmunities;
        this.condImmunities = condImmunities;
        this.senses = senses;
        this.passivePerception = passivePerception;
        this.languages = languages;
        this.challengeRating = challengeRating;
        this.traits = traits;
        this.actions = actions;
        this.slots = slots;
        this.spells = spells;
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(String armorClass) {
        this.armorClass = armorClass;
    }

    public String getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(String hitPoints) {
        this.hitPoints = hitPoints;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public String getSaves() {
        return saves;
    }

    public void setSaves(String saves) {
        this.saves = saves;
    }

    public String getSkills(){
        return skills;
    }

    public void setSkills(String skills){
        this.skills = skills;
    }

    public String getVulnerabilities(){
        return vulnerabilities;
    }

    public void setVulnerabilities(String vulnerabilities){
        this.vulnerabilities = vulnerabilities;
    }

    public String getResistances() {
        return resistances;
    }

    public void setResistances(String resistances) {
        this.resistances = resistances;
    }

    public String getDmgImmunities() {
        return dmgImmunities;
    }

    public void setDmgImmunities(String dmgImmunities) {
        this.dmgImmunities = dmgImmunities;
    }

    public String getCondImmunities() {
        return condImmunities;
    }

    public void setCondImmunities(String condImmunities) {
        this.condImmunities = condImmunities;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public int getPassivePerception() {
        return passivePerception;
    }

    public void setPassivePerception(int passivePerception) {
        this.passivePerception = passivePerception;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getChallengeRating() {
        return challengeRating;
    }

    public void setChallengeRating(String challengeRating) {
        this.challengeRating = challengeRating;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    private List<Action> actions;

    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }

    public List<String> getSpells() {
        return spells;
    }

    public void setSpells(List<String> spells) {
        this.spells = spells;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("monster");
        addTextNode(doc, out, "name", name);
        addTextNode(doc, out, "size", size.getAbbreviation());
        addTextNode(doc, out, "type", type);
        addTextNode(doc, out, "alignment", alignment);
        addTextNode(doc, out, "ac", armorClass);
        addTextNode(doc, out, "hp", hitPoints);
        addTextNode(doc, out, "speed", speed);
        addTextNode(doc, out, "str", strength);
        addTextNode(doc, out, "dex", dexterity);
        addTextNode(doc, out, "con", constitution);
        addTextNode(doc, out, "int", intelligence);
        addTextNode(doc, out, "wis", wisdom);
        addTextNode(doc, out, "cha", charisma);
        if (!saves.isEmpty()){
            addTextNode(doc, out, "save", saves);
        }
        if (!skills.isEmpty()){
            addTextNode(doc, out, "skill", skills);
        }
        if (!vulnerabilities.isEmpty()){
            addTextNode(doc, out, "vulnerable", vulnerabilities);
        }
        if (!resistances.isEmpty()){
            addTextNode(doc, out, "resist", resistances);
        }
        if (!dmgImmunities.isEmpty()){
            addTextNode(doc, out, "immune", dmgImmunities);
        }
        if (!condImmunities.isEmpty()){
            addTextNode(doc, out, "conditionImmune", condImmunities);
        }
        if (!senses.isEmpty()){
            addTextNode(doc, out, "senses", senses);
        }
        addTextNode(doc, out, "passive", passivePerception);
        if (!languages.isEmpty()){
            addTextNode(doc, out, "languages", languages);
        }
        addTextNode(doc, out, "cr", challengeRating);
        for (Trait trait : traits){
            out.appendChild(trait.toXML(doc));
        }
        for (Action action : actions){
            out.appendChild(action.toXML(doc));
        }
        if (!slots.isEmpty()){
            String slots = "";
            for (int i : this.slots){
                slots += i + ",";
            }
            slots = slots.substring(0, slots.length()-1);
            addTextNode(doc, out, "slots", slots);
        }
        if (!spells.isEmpty()){
            String spells = "";
            for (String spell : this.spells){
                spells += spell + ", ";
            }
            spells = speed.substring(0, spells.length() - 2);
            addTextNode(doc, out, "spells", spells);
        }
        if (!description.isEmpty()){
            addTextNode(doc, out, "description", description);
        }
        return out;
    }

}
