package model;

public enum Size {

    Tiny("T"),
    Small("S"),
    Medium("M"),
    Large("L"),
    Huge("H"),
    Gargantuan("G");

    private final String abv;

    Size(String abv){
        this.abv = abv;
    }

    public String getAbbreviation(){
        return abv;
    }

    public static Size fromAbbreviation(String abv) {
        for (Size size : Size.values()) {
            if (size.abv.equalsIgnoreCase(abv)) {
                return size;
            }
        }
        throw new IllegalArgumentException("No Size found for abbreviation \'" + abv + "\'");
    }

}
