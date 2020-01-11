package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.BetterNodeList;

import static model.Parser.addTextNode;
import static model.Parser.checkUnused;

public class Action implements CompendiumObject {

    public static Action parse(Node node) throws ParseException {
        BetterNodeList action = new BetterNodeList(node);
        try {
            ActionType type = ActionType.fromTag(action.getName());
            String name = action.getFirstValue("name");
            StringBuilder text = new StringBuilder();
            for (Node txt : action.getNodes("text")){
                text.append(txt.getTextContent());
                text.append("\n");
            }
            Attack attack = null;
            if (action.hasNode("attack")){
                attack = Attack.parse(action.getNodes("attack").get(0));
            }
            checkUnused(action);
            return new Action(type, name, text.toString(), attack);
        }
        catch (IllegalArgumentException e){
            throw new ParseException("Incomplete Action definition: " + e.getMessage());
        }
    }

    private ActionType type;
    private String name;
    private String text;
    private Attack attack;

    public Action(ActionType type, String name, String text, Attack attack){
        this.type = type;
        this.name = name;
        this.text = text;
        this.attack = attack;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getText(){
        return text;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setText(String text){
        this.text = text;
    }

    public Attack getAttack() {
        return attack;
    }

    public void setAttack(Attack attack) {
        this.attack = attack;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public Node toXML(Document doc) {
        Element out = doc.createElement(type.name().toLowerCase());
        addTextNode(doc, out, "name", this.name);
        for (String text : this.text.split("\n")){
            addTextNode(doc, out, "text", text);
        }
        if (attack != null){
            addTextNode(doc, out, "attack", attack.toString());
        }
        return out;
    }

}
