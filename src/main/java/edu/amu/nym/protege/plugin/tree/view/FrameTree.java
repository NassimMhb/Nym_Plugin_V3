package edu.amu.nym.protege.plugin.tree.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;

import org.protege.editor.core.ui.error.ErrorLogPanel;
import org.protege.editor.core.ui.util.LinkLabel;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;

import edu.amu.nym.protege.plugin.set.view.FrameSet;

public class FrameTree extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5411711042737977576L;
	
    public static final URI Classes_IRI_DOCUMENTATION = URI.create("https://www.w3.org/TR/2009/REC-owl2-syntax-20091027/#Classes");

    public static OWLModelManager modelManager;
    
    public static boolean isIndividualsTableClicked = false;
    
    public static String individualSelected = "";
    
        
	private final String classesNameFieldLabel = "Arborescence:  ";
	
	private JTree classesJTree = new JTree();

	private FillTree fillTree = new FillTree();
	
	private FrameSet frameSet = new FrameSet();
	
	
    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
        	fillComboBox();
        }
    };
    
    @SuppressWarnings("static-access")
	public FrameTree(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
    	fillComboBox();


        modelManager.addListener(modelListener);
                
        setLayout(new BorderLayout());
        add(createJTree(), BorderLayout.NORTH);
        
        
        
    }
    
    
    
    public void dispose() {
        modelManager.removeListener(modelListener);
    }
    
    private void showOntologyIRIDocumentation() {
    	try {
            Desktop.getDesktop().browse(Classes_IRI_DOCUMENTATION);
        }
        catch (IOException ex) {
            ErrorLogPanel.showErrorDialog(ex);
        }
    }
    
    private JToolBar createJTree() {
    	JToolBar panel = new JToolBar();
    	panel.add(new LinkLabel(classesNameFieldLabel , e -> {
        	showOntologyIRIDocumentation();
        }));
    	JScrollPane js = new JScrollPane(classesJTree);
    	panel.add(js);
    	return panel;
    }
    
    private void fillComboBox() {


    	classesJTree.setModel(fillTree.fillArbreTree());

    	repaint();
        revalidate();
    }
    
}