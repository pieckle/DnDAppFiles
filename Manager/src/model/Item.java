package model;

import org.w3c.dom.Node;
import util.BetterNodeList;
import util.CurrencyHelper;

import java.util.ArrayList;
import java.util.List;

public class Item implements CompendiumObject {

    public static Item parse(Node node) throws ParseException {
        BetterNodeList item = new BetterNodeList(node.getChildNodes());
        try {
            String name = item.getFirstValue("name");
            ItemType type = ItemType.fromAbbreviation(item.getFirstValue("type"));
            boolean magic = item.hasNode("magic") && item.getFirstValue("magic").equals("1");
            double value = CurrencyHelper.parseCurrency(item.getFirstValue("value", ""));
            double weight;
            try {
                weight = Double.parseDouble(item.getFirstValue("weight", "0"));
            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Could not parse weight value \"" + item.getFirstValue("weight") + "\"");
            }
            String dmg1 = item.getFirstValue("dgm1", "");
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
                if (item.hasNode("range")) {
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
            return new Item(
                    name,
                    type,
                    magic,
                    value,
                    weight,
                    dmg1,
                    dmg2,
                    dmgType,
                    properties,
                    nearRange,
                    longRange,
                    rarity,
                    text.toString()
            );
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete item definition: " + e.getMessage());
        }
    }

    private String name;
    private ItemType type;
    private boolean magic;
    private double value;
    private double weight;
    private String dmg1, dmg2;
    private DamageType dmgType;
    private List<ItemProperty> itemProperties;
    private int nearRange, longRange;
    private String rarity;
    private String text;

    public Item(String name,
                ItemType type,
                boolean magic,
                double value,
                double weight,
                String dmg1,
                String dmg2,
                DamageType dmgType,
                List<ItemProperty> itemProperties,
                int nearRange,
                int longRange,
                String rarity,
                String text) {
        this.name = name;
        this.type = type;
        this.magic = magic;
        this.value = value;
        this.weight = weight;
        this.dmg1 = dmg1;
        this.dmg2 = dmg2;
        this.dmgType = dmgType;
        this.itemProperties = itemProperties;
        this.nearRange = nearRange;
        this.longRange = longRange;
        this.rarity = rarity;
        this.text = text;
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

    @Override
    public Node toXML() {
        return null;
    }

}
