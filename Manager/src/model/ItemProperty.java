package model;

public enum ItemProperty {

    Finesse("F"),
    Light("L"),
    Heavy("H"),
    Versatile("V"),
    Two_Handed("2H"),
    Thrown("T"),
    Ammunition("A"),
    Loading("LD"),
    Reach("R"),
    Special("S");

    private final String abv;

    ItemProperty(String abv){
        this.abv = abv;
    }

    public String getAbbreviation(){
        return abv;
    }

    public static ItemProperty fromAbbreviation(String abv) {
        for (ItemProperty prop : ItemProperty.values()) {
            if (prop.abv.equals(abv)) {
                return prop;
            }
        }
        throw new IllegalArgumentException("No Item Property found for abbreviation \'" + abv + "\'");
    }

}
