package model;

import org.w3c.dom.Node;

public class Attack {

    public static Attack parse(Node node) throws ParseException {
        String[] value = (" " + node.getTextContent() + " ").split("\\|");
        if (value.length != 3){
            throw new ParseException("Invalid Attack " + node.getTextContent());
        }
        int bonus;
        if (value[1].isBlank()){
            bonus = 0;
        }
        else {
            try {
                bonus = Integer.parseInt(value[1]);
            } catch (NumberFormatException e) {
                throw new ParseException("Invalid Attack " + node.getTextContent());
            }
        }
        return new Attack(value[0].trim(), bonus, value[2].trim());
    }

    private String name;
    private int bonus;
    private String roll;

    public Attack(String name, int bonus, String roll) {
        this.name = name;
        this.bonus = bonus;
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    @Override
    public String toString(){
        return name + "|" + bonus + "|" + roll;
    }

}
