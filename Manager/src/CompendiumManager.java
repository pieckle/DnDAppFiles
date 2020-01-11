import model.ParseException;
import model.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CompendiumManager {

    public static void main(String[] args){
        File root;
        try {
            root = validateDirectory(args[0]);
        }
        catch (IllegalArgumentException | IndexOutOfBoundsException e){
            root = getRootDir();
        }
        loadFiles(root);
    }

    private static File validateDirectory(String path){
        File f = new File(path);
        if (f.exists() && f.isDirectory()){
            return f;
        }
        throw new IllegalArgumentException();
    }

    private static File getRootDir(){
        Scanner cin = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter root directory: ");
                return validateDirectory(cin.nextLine());
            }
            catch (IllegalArgumentException e){
                continue;
            }
        }
    }

    private static void loadFiles(File root){
        for (File sub : root.listFiles()){
            if (sub.isDirectory()){
                loadFiles(sub);
            }
            else if (sub.getName().endsWith(".xml")){
                processXML(sub);
            }
        }
    }

    private static void processXML(File file){
        System.out.println("Parsing file: " + file.getAbsolutePath());
        try {
            Parser.parse(file);
        }
        catch (ParserConfigurationException | SAXException e) {
            System.err.println("Parse failed: bad XML: " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("Parse failed: file could not be read: " + e.getMessage());
        }
        catch (ParseException e) {
            System.err.println("Parse failed: Could not read data: " + e.getMessage());
        }
    }

}
