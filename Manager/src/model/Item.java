package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;
import util.CurrencyHelper;

import java.util.ArrayList;
import java.util.List;

import static model.Parser.*;

public class Item implements CompendiumObject {

    public static Item parse(Node node) throws ParseException {
        BetterNodeList item = new BetterNodeList(node);
        try {
            String name = item.getFirstValue("name");
            ItemType type = ItemType.fromAbbreviation(item.getFirstValue("type"));
            boolean magic = item.hasNode("magic") && item.getFirstValue("magic").equals("1");
            boolean attunement = item.hasNode("detail") && item.getFirstValue("detail").contains("attunement");
            double value = CurrencyHelper.parseCurrency(item.getFirstValue("value", ""));
            double weight;
            try {
                weight = Double.parseDouble(item.getFirstValue("weight", "0"));
            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Could not parse weight value \"" + item.getFirstValue("weight") + "\"");
            }
            int ac = 0;
            if (item.hasNode("ac")){
                ac = parseIntNode(item, "ac");
            }
            int strengthReq = 0;
            if (item.hasNode("strength") && !item.getFirstValue("strength").isBlank()){
                strengthReq = parseIntNode(item, "strength");
            }
            boolean stealthDisadvantage = item.hasNode("stealth") && item.getFirstValue("stealth").equalsIgnoreCase("YES");
            String dmg1 = item.getFirstValue("dmg1", "");
            String dmg2 = item.getFirstValue("dmg2", "");
            DamageType dmgType = null;
            if (item.hasNode("dmgType")){
                dmgType = DamageType.fromAbbreviation(item.getFirstValue("dmgType"));
            }
            List<ItemProperty> properties = new ArrayList<>();
            for (String prop : item.getFirstValue("property", "").split(",")){
                if (!prop.isBlank()) {
                    properties.add(ItemProperty.fromAbbreviation(prop.trim()));
                }
            }
            int nearRange = 0, longRange = 0;
            try {
                if (item.hasNode("range") && !item.getFirstValue("range").isBlank()) {
                    String[] range = item.getFirstValue("range").split("/");
                    nearRange = Integer.parseInt(range[0]);
                    if (range.length > 1) {
                        longRange = Integer.parseInt(range[1]);
                    }
                }
            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Could not parse range value \"" + item.getFirstValue("range") + "\"");
            }
            String rarity = item.getFirstValue("rarity", "");
            StringBuilder text = new StringBuilder();
            for (Node txt : item.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            List<Modifier> modifiers = new ArrayList<>();
            for (Node mod : item.getNodes("modifier")){
                modifiers.add(Modifier.parse(mod));
            }
            checkUnused(item);
            return new Item(
                    name,
                    type,
                    magic,
                    attunement,
                    value,
                    weight,
                    ac,
                    strengthReq,
                    stealthDisadvantage,
                    dmg1,
                    dmg2,
                    dmgType,
                    properties,
                    nearRange,
                    longRange,
                    rarity,
                    text.toString(),
                    modifiers
            );
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete item definition: " + e.getMessage());
        }
    }

    private String name;
    private ItemType type;
    private boolean magic;
    private boolean attunementReq;
    private double value;
    private double weight;
    private int ac;
    private int strengthReq;
    private boolean stealthDisadvantage;
    private String dmg1, dmg2;
    private DamageType dmgType;
    private List<ItemProperty> itemProperties;
    private int nearRange, longRange;
    private String rarity;
    private String text;
    private List<Modifier> modifiers;

    public Item(String name,
                ItemType type,
                boolean magic,
                boolean attunementReq,
                double value,
                double weight,
                int ac,
                int strengthReq,
                boolean stealthDisadvantage,
                String dmg1,
                String dmg2,
                DamageType dmgType,
                List<ItemProperty> itemProperties,
                int nearRange,
                int longRange,
                String rarity,
                String text,
                List<Modifier> modifiers) {
        this.name = name;
        this.type = type;
        this.magic = magic;
        this.attunementReq = attunementReq;
        this.value = value;
        this.weight = weight;
        this.ac = ac;
        this.strengthReq = strengthReq;
        this.stealthDisadvantage = stealthDisadvantage;
        this.dmg1 = dmg1;
        this.dmg2 = dmg2;
        this.dmgType = dmgType;
        this.itemProperties = itemProperties;
        this.nearRange = nearRange;
        this.longRange = longRange;
        this.rarity = rarity;
        this.text = text;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public boolean isMagic() {
        return magic;
    }

    public void setMagic(boolean magic) {
        this.magic = magic;
    }

    public boolean isAttunementReq() {
        return attunementReq;
    }

    public void setAttunementReq(boolean attunementReq) {
        this.attunementReq = attunementReq;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getStrengthReq() {
        return strengthReq;
    }

    public void setStrengthReq(int strengthReq) {
        this.strengthReq = strengthReq;
    }

    public boolean isStealthDisadvantage() {
        return stealthDisadvantage;
    }

    public void setStealthDisadvantage(boolean stealthDisadvantage) {
        this.stealthDisadvantage = stealthDisadvantage;
    }

    public String getDmg1() {
        return dmg1;
    }

    public void setDmg1(String dmg1) {
        this.dmg1 = dmg1;
    }

    public String getDmg2() {
        return dmg2;
    }

    public void setDmg2(String dmg2) {
        this.dmg2 = dmg2;
    }

    public DamageType getDmgType() {
        return dmgType;
    }

    public void setDmgType(DamageType dmgType) {
        this.dmgType = dmgType;
    }

    public List<ItemProperty> getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(List<ItemProperty> itemProperties) {
        this.itemProperties = itemProperties;
    }

    public int getNearRange() {
        return nearRange;
    }

    public void setNearRange(int nearRange) {
        this.nearRange = nearRange;
    }

    public int getLongRange() {
        return longRange;
    }

    public void setLongRange(int longRange) {
        this.longRange = longRange;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
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

    @Override
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement("item");
        addTextNode(doc, out, "name", this.name);
        addTextNode(doc, out, "type", type.getAbbreviation());
        if (magic){
            addTextNode(doc, out, "magic", "1");
        }
        addTextNode(doc, out, "value", CurrencyHelper.printCurrency(value));
        addTextNode(doc, out, "weight", Double.toString(weight));
        if (ac > 0){
            addTextNode(doc, out, "ac", ac);
        }
        if (strengthReq > 0){
            addTextNode(doc, out, "strength", strengthReq);
        }
        if (stealthDisadvantage){
            addTextNode(doc, out, "stealth", "YES");
        }
        if (!dmg1.isEmpty()){
            addTextNode(doc, out, "dmg1", dmg1);
        }
        if (!dmg2.isEmpty()){
            addTextNode(doc, out, "dmg2", dmg2);
        }
        if (dmgType != null){
            addTextNode(doc, out, "dmgType", dmgType.getAbbreviation());
        }
        String props = "";
        for (ItemProperty itemProperty : itemProperties){
            props += itemProperty.getAbbreviation() + ",";
        }
        if (!props.isEmpty()) {
            props = props.substring(0, props.length() - 1);
        }
        addTextNode(doc, out, "property", props);
        String range = "";
        if (nearRange > 0){
            range += nearRange;
        }
        if (nearRange > 0 && longRange > 0){
            range += "/";
        }
        if (longRange > 0){
            range += longRange;
        }
        addTextNode(doc, out, "range", range);
        String detail = "";
        if (!range.isEmpty()){
            addTextNode(doc, out, "rarity", rarity);
            detail += rarity;
        }
        if (attunementReq){
            if (!detail.isEmpty()){
                detail += " ";
            }
            detail += "(requires attunement)";
        }
        if (!detail.isEmpty()) {
            addTextNode(doc, out, "detail", detail);
        }
        for (String text : this.text.split("\n")){
            addTextNode(doc, out, "text", text);
        }
        for (Modifier modifier : modifiers){
            out.appendChild(modifier.toXML(doc));
        }
        return out;
    }

}
