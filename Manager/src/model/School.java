package model;

public enum School {

    Conjuration("C"),
    Abjuration("A"),
    Transmutation("T"),
    Enchantment("EN"),
    Necromancy("N"),
    Divination("D"),
    Evocation("EV"),
    Illusion("I");

    private final String abv;

    School(String abv){
        this.abv = abv;
    }

    public String getAbbreviation(){
        return abv;
    }

    public static School fromAbbreviation(String abv){
        for (School sch : School.values()){
            if (sch.abv.equals(abv)){
                return sch;
            }
        }
        throw new IllegalArgumentException("No school found for abbreviation \'" + abv + "\'");
    }

}
