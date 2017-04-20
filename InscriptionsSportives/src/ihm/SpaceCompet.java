package ihm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.*;
import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceCompet
	{
		private JPanel ongletComp = new JPanel();
		JPanel addCompetition = new JPanel();
		JTextField fieldAddNom = new JTextField();
		JTextField fieldAddCloture = new JTextField();
		JRadioButton fieldAddEnEquipe = new JRadioButton();
		JButton buttonAdd = new JButton("Ajouter");
		
		/** Construteur **/
		public SpaceCompet()
		{
			// désactive le boutton ajouter
			buttonAdd.setEnabled(false);
			
			// active l'écoute sur les champs
			setListener();
		}
		
		/** page onglet **/
		public JPanel getOnglet(){
			return this.ongletComp;
		}
		
		/** Liste des compétitions **/
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
			
			// couleur de l'entete du tableau
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return tableau;
		}
		
		/** Panneau d'ajout de compétition **/
		public JPanel getAddCompetition() 
		{
			// fond du panneau ajouter
			addCompetition.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldAddNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldAddCloture.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldAddNom.setPreferredSize(new Dimension(130, 20));
			fieldAddCloture.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout de compétition
			addCompetition.add(new JLabel("Intitulé de la compétition : "));
			addCompetition.add(fieldAddNom);
			addCompetition.add(new JLabel("Date de cloture : "));
			addCompetition.add(fieldAddCloture);
			addCompetition.add(new JLabel("En Equipe : "));
			addCompetition.add(fieldAddEnEquipe);
			addCompetition.add(Box.createHorizontalStrut(10));
			addCompetition.add(Box.createHorizontalStrut(10));
			addCompetition.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addCompetition.setBorder(BorderFactory.createTitledBorder("Créer une compétition"));
			addCompetition.add(buttonAdd);
			
			return addCompetition;
		}
		
		/** Actualisation des données **/
		private void refreshSpaceCompet() 
		{
			ongletComp.revalidate();
			ongletComp.repaint();
		}

		/** validation format des champs d'ajout d'une compétition **/
		private boolean isValid(String s) 
		{
			switch (s) 
			{
				case "intitule":
					return nomValid();
				case "cloture":
					return clotureValid();
			}
			
			return false;
		}
		
		/** contrôle sur l'intitulé de la compétition **/
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z ]{1,}");
		}
		
		/** contrôle sur la date de clôture de la compétition **/
		private boolean clotureValid() {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				format.parse(fieldAddCloture.getText());
		        return true;
		    }
		    catch(ParseException e){
		          return false;
		    }
		}

		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			fieldAddCloture.setBorder(BorderFactory.createLineBorder(clotureValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled((isValid("intitule") && isValid("cloture")));
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

		/** écoute les actions **/
		class buttonAddListener implements ActionListener 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nom = fieldAddNom.getText();
				LocalDate Cloture = LocalDate.parse(fieldAddCloture.getText());
				boolean EnEquipe = isInTeam();

				try 
				{
					Container.getInscriptions().createCompetition(nom, Cloture, EnEquipe);
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
				refreshSpaceCompet();
			}
			
			private boolean isInTeam() {
				if(fieldAddEnEquipe.isSelected()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		/** Ajout des écouteurs pour chaque champ **/
		private void setListener() 
		{
			fieldAddNom.addKeyListener(new fieldAddListener());
			fieldAddCloture.addKeyListener(new fieldAddListener());
			buttonAdd.addActionListener(new buttonAddListener());

		}
	}
