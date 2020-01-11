package model;

public enum ItemType {

    Currency("$"),
    Adventuring_Gear("G"),
    Melee_Weapon("M"),
    Ranged_Weapon("R"),
    Ammunition("A"),
    Shield("S"),
    Light_Armor("LA"),
    Medium_Armor("MA"),
    Heavy_Armor("HA"),
    Wondrous_Item("W"),
    Potion("P"),
    Rod("RD"),
    Staff("ST"),
    Ring("RG"),
    Wand("WD"),
    Scroll("SC");

    private final String abv;

    ItemType(String abv){
        this.abv = abv;
    }

    public String getAbbreviation(){
        return abv;
    }

    public static ItemType fromAbbreviation(String abv){
        for (ItemType type : ItemType.values()){
            if (type.abv.equals(abv)){
                return type;
            }
        }
        throw new IllegalArgumentException("No Item Type found for abbreviation \'" + abv + "\'");
    }


}
