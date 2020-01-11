package model;

public class AbilityBonus {

    public static AbilityBonus parse(String value) throws ParseException {
        String[] bits = value.split(" ");
        if (bits.length != 2){
            throw new ParseException("Unable to parse Ability Bonus \"" + value + "\": bad format");
        }
        Ability ab;
        try {
            ab = Ability.fromAbbreviation(bits[0].trim());
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Unable to parse Ability Bonus \"" + value + "\": ability abbreviation not recognized");
        }
        int bonus;
        try {
            bonus = Integer.parseInt(bits[1]);
        }
        catch (NumberFormatException e){
            throw new ParseException("Unable to parse Ability Bonus \"" + value + "\": bonus value not a number");
        }
        return new AbilityBonus(ab, bonus);
    }

    private Ability ability;
    private int bonus;

    public AbilityBonus(Ability ability, int bonus) {
        this.ability = ability;
        this.bonus = bonus;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return ability.getAbbreviation() + " " + bonus;
    }

}
