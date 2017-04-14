package ihm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableColumn;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Inscriptions;

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
			String[] nomColonnes = new String[10];
			Object[][] data = new Object[10][10];
			int i = 0;
			int j = 1;
			
			for (Competition c : Inscriptions.getInscriptions().getCompetitions()) 
			{
				data[i][j] = c.getNom();
				i++;
			}
			
			JTable tableau = new JTable(data, nomColonnes);
			
			return tableau;
		}
	}
