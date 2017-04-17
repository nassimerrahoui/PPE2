package ihm;

import javax.swing.*;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceCompet
	{
		private JPanel ongletComp = new JPanel();
		
		public SpaceCompet(JLabel titreOnglet, JTable tableau)
		{
			this.ongletComp.add(titreOnglet);
			this.ongletComp.add(tableau);
		}
		
		public JPanel getOnglet(){
			return this.ongletComp;
		}
		
		public JTable getTableau() throws enEquipeException, addCloseException
		{
			String[] entete = {"Nom"};
			String[][] data = new String[10][10];
			int i = 0;
			int j = 0;
			
			for (Competition c : Container.getInscriptions().getCompetitions()) 
			{
				data[i][j] = c.getNom();
				i++;
			}
			
			JTable tableau = new JTable(data, entete);
			
			return tableau;
		}
	}
