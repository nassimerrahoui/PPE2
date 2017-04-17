package ihm;

import java.awt.Color;
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
			String[] entete = {"Nom", "Cloture", "En Equipe"};
			Object[][] data = new Object[10][10];
			int i = 0;
			int j = 0;
			for (Competition c : Container.getInscriptions().getCompetitions()) 
			{
				data[i][j] = c.getNom();
				j++;
				data[i][j] = c.getDateCloture();
				j++;
				data[i][j] = c.estEnEquipe();
				j--;
				j--;
				i++;
			}
			JTable tableau = new JTable(data, entete);
			
			// mise en page du tableau
			
			tableau.setBounds(100, 100, 500, 500);
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return tableau;
		}
	}
