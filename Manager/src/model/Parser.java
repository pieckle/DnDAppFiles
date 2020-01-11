package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ui.ConsoleOut;
import util.BetterNodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<CompendiumObject> parse(File file) throws ParserConfigurationException, IOException, SAXException, ParseException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("compendium") &&
                root.hasAttribute("version") &&
                root.getAttribute("version").equals("5")){
            List<CompendiumObject> out = new ArrayList<>();
            for (Node node : new BetterNodeList(root)){
                try {
                    out.add(doParse(node));
                }
                catch (ParseException p){
                    ConsoleOut.printError("Failed to parse entry <" + node.getNodeName() + ">: " + p.getMessage());
                }
            }
            return out;
        }
        else {
            throw new ParseException("XML file not a compendium");
        }
    }

    private static CompendiumObject doParse(Node node) throws ParseException {
        switch (node.getNodeName()){
            case "spell":
                return Spell.parse(node);
            case "item":
                return Item.parse(node);
            case "monster":
                return Monster.parse(node);
            case "feat":
                return Feat.parse(node);
            case "background":
                return Background.parse(node);
            case "race":
                return Race.parse(node);
            case "class":
                return Clazz.parse(node);
            default:
                throw new ParseException("Undefined tag type: " + node.getNodeName());
        }
    }

    public static int parseIntNode(BetterNodeList list, String tag){
        try {
            return Integer.parseInt(list.getFirstValue(tag).trim());
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("Failed to parse " + tag + " \"" + list.getFirstValue(tag) + "\"");
        }
    }

    public static void addTextNode(Document doc, Element parent, String tag, int value){
        addTextNode(doc, parent, tag, value + "");
    }

    public static void addTextNode(Document doc, Element parent, String tag, String text){
        Element out = doc.createElement(tag);
        out.appendChild(doc.createTextNode(text));
        parent.appendChild(out);
    }

    public static void checkUnused(BetterNodeList list){
        if (!list.getUnusedNodes().isEmpty()){
            String out = "";
            for (String node : list.getUnusedNodes()){
                out += node + ", ";
            }
            out = out.substring(0, out.length() - 2);
            ConsoleOut.printError("  Unused fields \"" + out + "\" in node " + list.getName());
        }
    }

}
