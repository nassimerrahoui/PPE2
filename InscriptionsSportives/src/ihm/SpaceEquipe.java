package ihm;

import java.awt.Color;

import javax.swing.*;

import metier.Equipe;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceEquipe
	{		
		private JPanel ongletEqui = new JPanel();
		
		public SpaceEquipe()
		{
	
		}
		
		public JPanel getOnglet(){
			return this.ongletEqui;
		}
		
		public JTable getTableau() throws enEquipeException, addCloseException
		{
			String[] entete = {"Nom"};
			Object[][] data = new Object[50][50];
			int i = 0;
			int j = 0;
			for (Equipe e : Container.getInscriptions().getEquipes()) 
			{
				data[i][j] = e.getNom();
				i++;
			}
			JTable tableau = new JTable(data, entete);
			
			// mise en page du header
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return tableau;
		}
		
	}
