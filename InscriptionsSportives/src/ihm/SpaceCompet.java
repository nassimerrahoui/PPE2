package ihm;



import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceCompet
	{
		private JPanel ongletComp = new JPanel();
		
		public SpaceCompet()
		{
			
		}
		
		public JPanel getOnglet(){
			return this.ongletComp;
		}
		
		public JTable getTableau() throws enEquipeException, addCloseException
		{
			String[] entete = {"Nom", "Cloture", "En Equipe"};
			Object[][] data = new Object[30][30];
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
			
			// mise en page du header	
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return tableau;
		}
		
		public JPanel getAddCompetition() 
		{
			JPanel addCompetition = new JPanel();
			JTextField nomAjoutField = new JTextField();
			JButton boutonAjout = new JButton("Ajouter");
			addCompetition.setBackground(Color.WHITE);
			nomAjoutField.setPreferredSize(new Dimension(130, 20));
			addCompetition.add(new JLabel("Intitulé de la compétition : "));
			addCompetition.add(nomAjoutField);
			addCompetition.add(boutonAjout);
			
			return addCompetition;
		}
	}
