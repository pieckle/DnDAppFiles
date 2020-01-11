package model;

public enum Ability {

    Strength("Str"),
    Dexterity("Dex"),
    Constitution("Con"),
    Intelligence("Int"),
    Wisdom("Wis"),
    Charisma("Cha");

    private final String abv;

    Ability(String abv){
        this.abv = abv;
    }

    public String getAbbreviation(){
        return abv;
    }

    public static Ability fromAbbreviation(String abv) {
        for (Ability ab : Ability.values()) {
            if (ab.abv.equalsIgnoreCase(abv)) {
                return ab;
            }
        }
        throw new IllegalArgumentException("No Ability found for abbreviation \'" + abv + "\'");
    }

}
