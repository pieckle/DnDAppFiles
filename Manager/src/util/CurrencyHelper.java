package util;

public abstract class CurrencyHelper {

    public static double parseCurrency(String cur){
        double out = 0;
        if (cur.equals("0")){
            return out;
        }
        for (String term : cur.replace("pp", "lp").split("p")){
            term = term.trim();
            if (!term.isEmpty()){
                int val;
                try {
                    val = Integer.parseInt(term.substring(0, term.length() - 1));
                }
                catch (NumberFormatException e){
                    throw new IllegalArgumentException("Couldn't parse currency \"" + cur + "\"");
                }
                if (term.endsWith("g")){
                    out += val;
                }
                else if (term.endsWith("s")){
                    out += val / 10.0;
                }
                else if (term.endsWith("c")){
                    out += val / 100.0;
                }
                else if (term.endsWith("e")){
                    out += val / 2.0;
                }
                else if (term.endsWith("l")){
                    out += val * 10.0;
                }
                else {
                    throw new IllegalArgumentException("Couldn't parse currency \"" + cur + "\"");
                }
            }
        }
        return out;
    }

    public static String printCurrency(double cur){
        int gold = (int) Math.floor(cur);
        int silver = (int) Math.floor((cur - gold) * 10);
        int copper = (int) Math.floor((((cur - gold) * 10) - silver) * 10);
        String out = "";
        if (gold > 0){
            out += gold + "gp";
        }
        if (silver > 0){
            out += silver + "sp";
        }
        if (copper > 0){
            out += copper + "cp";
        }
        if (out.isEmpty()){
            out = "0";
        }
        return out;
    }

}
