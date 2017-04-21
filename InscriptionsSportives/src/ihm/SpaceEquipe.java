package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import metier.Equipe;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceEquipe
	{		
		private JPanel ongletEqui = new JPanel();
		JPanel addEquipe = new JPanel();
		JTextField fieldAddNom = new JTextField();
		JButton buttonAdd = new JButton("Ajouter");
		
		public SpaceEquipe()
		{
			buttonAdd.setEnabled(false);
			setListener();
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
			
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			//verrouillage des champs
			tableau.setEnabled(false);
			
			return tableau;
		}
		
		public JPanel getAddEquipe()
		{
			addEquipe.setBackground(Color.WHITE);
			
			fieldAddNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			fieldAddNom.setPreferredSize(new Dimension(130, 20));
			
			addEquipe.add(new JLabel("Nom :"));
			addEquipe.add(fieldAddNom);
			addEquipe.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addEquipe.setBorder(BorderFactory.createTitledBorder("Créer une équipe"));
			addEquipe.add(buttonAdd);
			
			return addEquipe;
		}
		
		private void refreshSpaceEqui() 
		{
			ongletEqui.revalidate();
			ongletEqui.repaint();
		}

		private boolean isValid() 
		{
			if(nomValid() == true)
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z ]{1,}");
		}

		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled((isValid()));
		}

		class fieldAddListener implements KeyListener 
		{

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				verifyField();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

		}

		class buttonAddListener implements ActionListener 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nom = fieldAddNom.getText();

				try 
				{
					Container.getInscriptions().createEquipe(nom);
				} 
				catch (enEquipeException | addCloseException e) 
				{
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(
						null,
						fieldAddNom.getText() + " " 
						+ "a bien été ajouté !", "M2L Info",
						JOptionPane.INFORMATION_MESSAGE
				);
				refreshSpaceEqui();
			}
		}
		
		private void setListener() 
		{
			fieldAddNom.addKeyListener(new fieldAddListener());
			buttonAdd.addActionListener(new buttonAddListener());

		}
		
	}
