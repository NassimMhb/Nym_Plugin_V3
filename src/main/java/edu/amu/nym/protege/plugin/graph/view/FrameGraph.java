package edu.amu.nym.protege.plugin.graph.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;

public class FrameGraph extends JPanel {
	private JButton refreshButton = new JButton("2nd Graph view");

    private JLabel textComponent = new JLabel();

    private OWLModelManager modelManager;


    private ActionListener refreshAction = e -> recalculate();
    
    private OWLModelManagerListener modelListener = event -> {
        if (event.getType() == EventType.ACTIVE_ONTOLOGY_CHANGED) {
            recalculate();
        }
    };
    
    public FrameGraph(OWLModelManager modelManager) {
    	this.modelManager = modelManager;
        recalculate();
        
        modelManager.addListener(modelListener);
        refreshButton.addActionListener(refreshAction);
        
        add(textComponent);
        add(refreshButton);
    }
    
    public void dispose() {
        modelManager.removeListener(modelListener);
        refreshButton.removeActionListener(refreshAction);
    }
    
    private void recalculate() {
        int count = modelManager.getActiveOntology().getClassesInSignature().size();
        if (count == 0) {
            count = 1;  // owl:Thing is always there.
        }
        textComponent.setText("Total classes = " + count);
    }

}
