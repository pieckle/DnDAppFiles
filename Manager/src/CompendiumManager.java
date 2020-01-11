
import ui.Form;

import static javax.swing.SwingUtilities.invokeLater;

public class CompendiumManager {

    public static void main(String[] args){
        invokeLater(() -> new Form().setVisible(true));
    }

}
