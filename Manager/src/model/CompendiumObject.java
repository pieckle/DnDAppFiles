package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface CompendiumObject {

    Node toXML(Document doc);

}
