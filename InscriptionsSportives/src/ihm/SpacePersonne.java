package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Personne;


public class SpacePersonne
	{	
		private JPanel ongletPers = new JPanel();
		JPanel addPersonne = new JPanel();
		JTextField fieldAddNom = new JTextField();
		JTextField fieldAddPrenom = new JTextField();
		JTextField fieldAddMail = new JTextField();
		JButton buttonAdd = new JButton("Ajouter");
		
		public SpacePersonne()
		{
			// désactive le boutton ajouter
			buttonAdd.setEnabled(false);
			
			// active l'écoute sur les champs
			setListener();
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
			JTable tableau = new JTable(data, entete);
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
			
			// mise en page du header
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			//verrouillage des champs
			tableau.setEnabled(false);
			
			return tableau;
		}
		
		public JPanel getAddPersonne()
		{
			addPersonne.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldAddNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldAddPrenom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldAddMail.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldAddNom.setPreferredSize(new Dimension(130, 20));
			fieldAddPrenom.setPreferredSize(new Dimension(130, 20));
			fieldAddMail.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout de personne
			addPersonne.add(new JLabel("Nom :"));
			addPersonne.add(fieldAddNom);
			addPersonne.add(new JLabel("Prenom : "));
			addPersonne.add(fieldAddPrenom);
			addPersonne.add(new JLabel("Mail : "));
			addPersonne.add(fieldAddMail);
			addPersonne.add(Box.createHorizontalStrut(5));
			addPersonne.add(Box.createHorizontalStrut(5));
			addPersonne.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addPersonne.setBorder(BorderFactory.createTitledBorder("Créer une personne"));
			addPersonne.add(buttonAdd);
			
			return addPersonne;
		}
		
		private void refreshSpacePers() 
		{
			ongletPers.revalidate();
			ongletPers.repaint();
		}

		/** validation format des champs d'ajout d'une personne **/ 
		private boolean isValid() 
		{
			if(nomValid() == true && prenomValid() == true && mailValid() == true)
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		/** contrôle sur le nom de la personne **/
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z ]{1,}");
		}
		
		/** contrôle sur le prénom de la personne **/
		private boolean prenomValid() {
			return fieldAddPrenom.getText().matches("[a-zA-Z ]{1,}");
		}
		
		/** contrôle sur le mail de la personne **/
		private boolean mailValid() {
			return fieldAddMail.getText().matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+.[a-z]{2,6}");
		}

		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			fieldAddPrenom.setBorder(BorderFactory.createLineBorder(prenomValid() ? Color.GREEN : Color.RED));
			fieldAddMail.setBorder(BorderFactory.createLineBorder(mailValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled((isValid()));
		}

		/** écoute les touches **/
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

		/** actions lorsqu'on clique sur ajouter **/
		class buttonAddListener implements ActionListener 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nom = fieldAddNom.getText();
				String prenom = fieldAddPrenom.getText();
				String mail = fieldAddMail.getText();

				try 
				{
					Container.getInscriptions().createPersonne(nom, prenom, mail);
				} 
				catch (enEquipeException | addCloseException e) 
				{
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(
						null,
						fieldAddNom.getText()+ fieldAddPrenom.getText() + " " 
						+ "a bien été ajouté !", "M2L Info",
						JOptionPane.INFORMATION_MESSAGE
				);
				refreshSpacePers();
			}
		}
		
		/** Ajout des écouteurs pour chaque champ **/
		private void setListener() 
		{
			fieldAddNom.addKeyListener(new fieldAddListener());
			fieldAddPrenom.addKeyListener(new fieldAddListener());
			fieldAddMail.addKeyListener(new fieldAddListener());
			buttonAdd.addActionListener(new buttonAddListener());

		}	
	}

	
