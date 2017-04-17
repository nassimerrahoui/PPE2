package ihm;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Inscriptions;
public class Container
{
	public Container() throws enEquipeException, addCloseException
	{   
		getContainer();
	}
	  
	public static Inscriptions getInscriptions() throws enEquipeException, addCloseException
	{
		Inscriptions i= Inscriptions.getInscriptions();
		return i;
	}
	
	public JFrame getContainer()
	{
		JFrame f = new JFrame("Gestion des Inscriptions");
		f.setSize(900, 700);
				
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
		
		SpacePersonne Pers = new SpacePersonne(new JLabel ("Personne"));	
		JPanel ongletPers = Pers.getOnglet();
		
		
		SpaceEquipe Equi = new SpaceEquipe(new JLabel ("Equipe"));	
		JPanel ongletEqui = Equi.getOnglet();
		
		
		SpaceCompet Comp = new SpaceCompet(new JLabel ("Competition"), new JTable());	
		JPanel ongletComp = Comp.getOnglet();
		
		
		onglets.addTab("Competition", ongletComp);
		onglets.addTab("Equipe", ongletEqui);
		onglets.addTab("Personne", ongletPers);
	
		
		onglets.setOpaque(true);		
		//f.add(onglets);		
		f.getContentPane().add(onglets);

		ongletComp.setLayout(new BorderLayout());
		//ongletComp.add(Comp.getTableau().getTableHeader(), BorderLayout.PAGE_START);
		//ongletComp.add(Comp.getTableau(), BorderLayout.CENTER);
		
		f.setVisible(true);
		f.setResizable(false);
		
		return f;
	}
	
	public static void main(String[] args) throws enEquipeException, addCloseException
	{
		new Container();   
	}
}

