package edu.amu.nym.protege.plugin.tree.view;


import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

public class FillTree {
		
	private DefaultTreeModel arbre;
	//private DefaultComboBoxModel<Object> classesComboBoxModel = new DefaultComboBoxModel<Object>();
	public static Object[] storeIndiv = null;

	public FillTree() {
		
	}
	
	public TreeModel fillArbreTree() {
		
		DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Racine");
		
		OWLOntology ontology = FrameTree.modelManager.getActiveOntology();
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
		int instanceCount = 0;
		OWLDataFactory factory = FrameTree.modelManager.getOWLDataFactory();
		
		
		int ClassesCount = FrameTree.modelManager.getActiveOntology().getClassesInSignature().size();
		Object[] test = FrameTree.modelManager.getActiveOntology().getClassesInSignature().toArray();
		
		for (int i = 0; i < ClassesCount; i++){
			//classesComboBoxModel.addElement(test[i]);
			DefaultMutableTreeNode rep = new DefaultMutableTreeNode(test[i]);
			
			//Mes tests

		/*	for(int j=0; j<=6; j++){
	            rep.add(new DefaultMutableTreeNode("Propriété n°" + j));
			}
		*/
			
			for (OWLClass c : ontology.getClassesInSignature()){
				System.out.println("voila :"+c.getIRI().getFragment()+" " + test[i]);
				
				//Problème sur la boucle if, il n'affiche rien car il pense que ce n'est pas identique
		//		if (c.getIRI().getFragment() == test[i]){
					String prefix = c.getIRI().getNamespace();
					NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
					instanceCount = instances.getFlattened().size();
					Object[] test2 = instances.getFlattened().toArray();
					storeIndiv = new Object[instanceCount];
			    	for (int x = 0; x < instanceCount; x++){
			    		OWLNamedIndividual classIndName = factory.getOWLNamedIndividual(IRI.create(prefix + test2[x]));
			    		storeIndiv[i] = classIndName;
			    		

			    		
			    			rep.add(new DefaultMutableTreeNode(classIndName));
			    			System.out.println("voila :"+c.getIRI().getFragment()+" " + test[i]);
			    	}
			//	}
			}
			
			
			
			//Fin de tests
			racine.add(rep);
    	}
		
		arbre = new DefaultTreeModel(racine);
		return arbre;
		//return classesComboBoxModel;
	}
}
