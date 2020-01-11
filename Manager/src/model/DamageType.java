package model;

public enum DamageType {

    Slashing("S"),
    Piercing("P"),
    Bludgeoning("B"),
    Radiant("R"),
    Narcotic("N"),
    Force("F"),
    Acid("A"),
    Poison("P"),
    Thunder("T"),
    Fire("F"),
    Cold("C");

    private final String abv;

    DamageType(String abv){
        this.abv = abv;
    }

    public String getAbbreviation(){
        return abv;
    }

    public static DamageType fromAbbreviation(String abv) {
        for (DamageType type : DamageType.values()) {
            if (type.abv.equals(abv)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No damage type found for abbreviation \'" + abv + "\'");
    }

}
