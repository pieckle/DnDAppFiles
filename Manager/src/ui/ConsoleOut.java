package ui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class ConsoleOut extends JTextPane {

    private static ConsoleOut instance = null;

    public static ConsoleOut getInstance(){
        if (instance == null) {
            instance = new ConsoleOut();
        }
        return instance;
    }

    private ConsoleOut(){
        setEditable(false);
        setMargin(new Insets(5, 5, 5, 5));
    }

    private void print(String msg, Color c){
        msg += "\n";
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        try{
            getStyledDocument().insertString(getDocument().getLength(), msg, aset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void printOut(String line){
        instance.print(line, Color.BLACK);
    }

    public static void printError(String line){
        instance.print(line, Color.RED);
    }

}
