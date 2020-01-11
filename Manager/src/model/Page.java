package model;

import org.w3c.dom.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Page {

    private final File file;
    private List<CompendiumObject> entries;

    public Page(File file) {
        this.file = file;
        this.entries = new LinkedList<>();
    }

    public void addEntry(CompendiumObject obj){
        this.entries.add(obj);
    }

    public List<CompendiumObject> getEntries(){
        return Collections.unmodifiableList(entries);
    }

    public List<Node> toXML(){
        List<Node> out = new ArrayList<>(entries.size());
        for (CompendiumObject obj : entries){
            out.add(obj.toXML());
        }
        return out;
    }

}
