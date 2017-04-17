package ihm;

import java.awt.Color;
import javax.swing.*;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Personne;


public class SpacePersonne
	{	
		private JPanel ongletPers = new JPanel();
		
		public SpacePersonne()
		{
			
		}
		
		public JPanel getOnglet(){
			return this.ongletPers;
		}
		
		public JTable getTableau() throws enEquipeException, addCloseException
		{
			String[] entete = {"Nom", "Prenom", "Mail"};
			Object[][] data = new Object[50][50];
			int i = 0;
			int j = 0;
			for (Personne p : Container.getInscriptions().getPersonnes()) 
			{
				data[i][j] = p.getNom();
				j++;
				data[i][j] = p.getPrenom();
				j++;
				data[i][j] = p.getMail();
				j--;
				j--;
				i++;
			}
			JTable tableau = new JTable(data, entete);
			
			// mise en page du header
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return tableau;
		}
			
	}

	
