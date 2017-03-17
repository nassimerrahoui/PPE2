package ihm;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import metier.Inscriptions;
public class Container 
{
	
	public Container()
	{   
		JFrame f = new JFrame("Gestion des Inscriptions");
		f.setSize(900, 700);
				
		JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
		
		
		SpacePersonne Pers = new SpacePersonne(new JLabel ("Personne"));	
		JPanel ongletPers = Pers.getOnglet();
		
		
		SpaceEquipe Equi = new SpaceEquipe(new JLabel ("Equipe"));	
		JPanel ongletEqui = Equi.getOnglet();
		
		
		SpaceCompet Comp = new SpaceCompet(new JLabel ("Competition"));	
		JPanel ongletComp = Comp.getOnglet();
		
		
		
		onglets.addTab("Competition", ongletComp);
		onglets.addTab("Personne", ongletPers);
		onglets.addTab("Equipe", ongletEqui);
	
		
		onglets.setOpaque(true);		
		f.add(onglets);		
		f.getContentPane().add(onglets);		
		f.setVisible(true);
		f.setResizable(false);
		getInscriptions();
		}
	  
	public static Inscriptions getInscriptions()
	{
		Inscriptions i= Inscriptions.getInscriptions();
		return i;
	}
	public static void main(String[] args)
	{
	new Container();
        
	}
}

