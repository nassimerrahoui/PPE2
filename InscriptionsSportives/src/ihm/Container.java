package ihm;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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
	  
	public JFrame getContainer() throws enEquipeException, addCloseException
	{
		getInscriptions();
		
		JFrame f = new JFrame("Gestion des Inscriptions");
		f.setSize(900, 700);
				
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
		
		SpacePersonne Pers = new SpacePersonne();	
		JPanel ongletPers = Pers.getOnglet();
		
		
		SpaceEquipe Equi = new SpaceEquipe();	
		JPanel ongletEqui = Equi.getOnglet();
		
		
		SpaceCompet Comp = new SpaceCompet();	
		JPanel ongletComp = Comp.getOnglet();
		
		//les onglets
		onglets.addTab("Competition", ongletComp);
		onglets.addTab("Equipe", ongletEqui);
		onglets.addTab("Personne", ongletPers);
		onglets.setOpaque(true);				
		f.getContentPane().add(onglets);

		//tableau des competitions
		ongletComp.add(new JScrollPane(Comp.getTableau()));
		ongletComp.add(Comp.getAddCompetition());
		
		//tableau des equipes
		ongletEqui.add(new JScrollPane(Equi.getTableau()));
		
		//tableau des personnes
		ongletPers.add(new JScrollPane(Pers.getTableau()));
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setResizable(false);
		return f;
		}
	  
	public static Inscriptions getInscriptions() throws enEquipeException, addCloseException
	{
		Inscriptions i = Inscriptions.getInscriptions();
		i.reinitialiser();
		return i;
	}
	
	public static void main(String[] args) throws enEquipeException, addCloseException
	{
		new Container();
	}
}

