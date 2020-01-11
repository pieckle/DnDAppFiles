package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static model.Parser.addTextNode;
import static model.Parser.parseIntNode;

public class Spell implements CompendiumObject {

    public static Spell parse(Node node) throws ParseException {
        BetterNodeList spell = new BetterNodeList(node);
        try {
            String name = spell.getFirstValue("name");
            int level = parseIntNode(spell, "level");
            School school = School.fromAbbreviation(spell.getFirstValue("school"));
            boolean ritual = spell.hasNode("ritual") && spell.getFirstValue("ritual").equalsIgnoreCase("YSE");
            String time = spell.getFirstValue("time");
            boolean concentration = time.contains("Concentration") || time.contains("concentration");
            String range = spell.getFirstValue("range");
            String comps = spell.getFirstValue("components");
            boolean verbal = false, somatic = false, material = false;
            List<String> materials = new ArrayList<>();
            if (comps.contains("(")){
                String mats = comps.substring(comps.indexOf("(") + 1, comps.lastIndexOf(")"));
                for (String mat : mats.split(", ")){
                    materials.add(mat);
                }
            }
            for (String comp : comps.split("\\(")[0].split(",")){
                if (comp.trim().equals("V")){
                    verbal = true;
                }
                else if (comp.trim().equals("S")){
                    somatic = true;
                }
                else if (comp.trim().startsWith("M")){
                    material = true;
                }
            }
            String duration = spell.getFirstValue("duration");
            List<String> classes = new ArrayList<>();
            for (String clazz : spell.getFirstValue("classes").split(",")){
                classes.add(clazz.trim());
            }
            StringBuilder text = new StringBuilder();
            for (Node txt : spell.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            return new Spell(
                    name,
                    level,
                    school,
                    ritual,
                    time,
                    concentration,
                    range,
                    verbal,
                    somatic,
                    material,
                    materials,
                    duration,
                    classes,
                    text.toString()
            );
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete spell definition: " + e.getMessage());
        }
    }

    private String name;
    private int level;
    private School school;
    private boolean ritual;
    private String time;
    private boolean concentration;
    private String range;
    private boolean verbal, somatic, material;
    private List<String> materials;
    private String duration;
    private List<String> classes;
    private String text;

    public Spell(String name,
                 int level,
                 School school,
                 boolean ritual,
                 String time,
                 boolean concentration,
                 String range,
                 boolean verbal,
                 boolean somatic,
                 boolean material,
                 List<String> materials,
                 String duration,
                 List<String> classes,
                 String text) {
        this.name = name;
        this.level = level;
        this.school = school;
        this.ritual = ritual;
        this.time = time;
        this.concentration = concentration;
        this.range = range;
        this.verbal = verbal;
        this.somatic = somatic;
        this.material = material;
        this.materials = materials;
        this.duration = duration;
        this.classes = classes;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public School getSchool() {
        return school;
    }

    public boolean isRitual(){
        return ritual;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void setRitual(boolean ritual) {
        this.ritual = ritual;
    }

    public String getTime() {
        return time;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setConcentration(boolean concentration) {
        this.concentration = concentration;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public boolean isVerbal() {
        return verbal;
    }

    public void setVerbal(boolean verbal) {
        this.verbal = verbal;
    }

    public boolean isSomatic() {
        return somatic;
    }

    public void setSomatic(boolean somatic) {
        this.somatic = somatic;
    }

    public boolean isMaterial() {
        return material;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spell spell = (Spell) o;
        return level == spell.level &&
                verbal == spell.verbal &&
                somatic == spell.somatic &&
                material == spell.material &&
                name.equals(spell.name) &&
                school == spell.school &&
                time.equals(spell.time) &&
                range.equals(spell.range) &&
                materials.equals(spell.materials) &&
                duration.equals(spell.duration) &&
                classes.equals(spell.classes) &&
                text.equals(spell.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, school, time, range, verbal, somatic, material, materials, duration, classes, text);
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("spell");
        addTextNode(doc, out, "name", name);
        addTextNode(doc, out, "level", level);
        addTextNode(doc, out, "school", school.getAbbreviation());
        if (ritual){
            addTextNode(doc, out, "ritual", "YES");
        }
        addTextNode(doc, out, "time", time);
        addTextNode(doc, out, "range", range);
        String comps = "";
        if (material && !materials.isEmpty()){
            comps += "M (";
            for (String mat : materials){
                comps += mat + ", ";
            }
            comps = comps.substring(0, comps.length() - 2) + ")";
        }
        if (somatic){
            comps = "S, " + comps;
        }
        if (verbal){
            comps = "V, " + comps;
        }
        if (comps.endsWith(", ")){
            comps = comps.substring(0, comps.length() - 2);
        }
        addTextNode(doc, out, "components", comps);
        addTextNode(doc, out, "duration", duration);
        String classes = "";
        for (String cl : this.classes){
            classes += cl + ", ";
        }
        if (!classes.isEmpty()){
            classes = classes.substring(0, classes.length() - 2);
        }
        addTextNode(doc, out, "classes", classes);
        for (String text : this.text.split("\n")){
            addTextNode(doc, out, "text", text);
        }
        return out;
    }

}
