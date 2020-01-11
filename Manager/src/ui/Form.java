package ui;

import model.CompendiumObject;
import model.ParseException;
import model.Parser;
import org.xml.sax.SAXException;
import util.Worker;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class Form extends JFrame {

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JTree treeModel;
    private JLayeredPane editorPnl;

    public Form(){
        initialize();
    }

    private void initialize(){
        setTitle("Compendium Manager");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon(getClass().getResource("/res/compendium_icon.png")).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);

        JMenu fileMenu = new JMenu("File");
        bar.add(fileMenu);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(this::selectRootDir);
        fileMenu.add(loadItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);

        treeModel = new JTree((TreeModel) null);
        treeModel.setBorder(new LineBorder(Color.lightGray));
        treeModel.addTreeSelectionListener(this::selectionChanged);

        JScrollPane treeScroll = new JScrollPane(treeModel);
        treeScroll.setPreferredSize(new Dimension(200, 10));
        add(treeScroll, BorderLayout.WEST);

        editorPnl = new JLayeredPane();
        add(editorPnl, BorderLayout.CENTER);

        JScrollPane consoleScroll = new JScrollPane(ConsoleOut.getInstance());
        consoleScroll.setPreferredSize(new Dimension(10, 150));
        add(consoleScroll, BorderLayout.SOUTH);
    }

    private void selectionChanged(TreeSelectionEvent treeSelectionEvent) {
        if (treeSelectionEvent.getNewLeadSelectionPath() != null &&
                treeSelectionEvent.getNewLeadSelectionPath().getLastPathComponent() instanceof DefaultMutableTreeNode){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSelectionEvent.getNewLeadSelectionPath().getLastPathComponent();
            if (node.getUserObject() instanceof CompendiumObject){
                openObject((CompendiumObject) node.getUserObject());
            }
            else {
                treeModel.clearSelection();
            }
        }
    }

    private void selectRootDir(ActionEvent a){
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileHidingEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File rootDir = chooser.getSelectedFile();
            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            Worker.run(() -> {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootDir.getName());
                loadFiles(root, rootDir);
                treeModel.setModel(new DefaultTreeModel(root));
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            });
        }
    }

    private boolean loadFiles(DefaultMutableTreeNode node, File dir){
        boolean found = false;
        for (File sub : dir.listFiles()){
            if (sub.isDirectory()){
                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(sub.getName());
                if (loadFiles(subNode, sub)){
                    node.add(subNode);
                    found = true;
                }
            }
            else if (sub.getName().endsWith(".xml")){
                if (processXML(node, sub)){
                    found = true;
                }
            }
        }
        return found;
    }

    private boolean processXML(DefaultMutableTreeNode node, File file){
        ConsoleOut.printOut("Parsing file: " + file.getAbsolutePath());
        try {
            DefaultMutableTreeNode pageNode = new DefaultMutableTreeNode(file.getName().substring(0, file.getName().lastIndexOf('.')));
            java.util.List<CompendiumObject> objs = Parser.parse(file);
            if (!objs.isEmpty()) {
                node.add(pageNode);
                for (CompendiumObject obj : objs) {
                    pageNode.add(new DefaultMutableTreeNode(obj));
                }
                return true;
            }
        }
        catch (ParserConfigurationException | SAXException e) {
            ConsoleOut.printError("Parse failed: bad XML: " + e.getMessage());
        }
        catch (IOException e) {
            ConsoleOut.printError("Parse failed: file could not be read: " + e.getMessage());
        }
        catch (ParseException e) {
            ConsoleOut.printError("Parse failed: Could not read data: " + e.getMessage());
        }
        return false;
    }

    private void openObject(CompendiumObject page){

    }

}
