package ihm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

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
		JTable competitionTable = getTableau();
		
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
		
		
		public TableModel lesDonnees()
		{
			ArrayList<Competition> competitionsIHM = new ArrayList<>(); 
			try 
			{
				for (Competition c : Container.getInscriptions().getCompetitions())
				{
					competitionsIHM.add(c);
				}
			} 
			catch (enEquipeException | addCloseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return new TableModel() {
			
				public void addTableModelListener(TableModelListener arg0){
					// TODO Auto-generated method stub
	
				}
	
				public Class<?> getColumnClass(int columnIndex) {
					switch (columnIndex) {
	
					case 1:
						return String.class;
	
					case 3:
						return LocalDate.class;
	
					case 2:
						return boolean.class;
	
					default:
						return Object.class;
					}
				}
	
				public int getColumnCount() {
					return 3;
				}
	
				public String getColumnName(int columnIndex) {
					switch (columnIndex) {
					case 0: return "Nom";
					case 1: return "Date de clôture";
					case 2: return "En equipe ";
					default:
						break;
					}
					return "-1";
				}
	
				public int getRowCount() {
					return competitionsIHM.size();
				}
	
				public Object getValueAt(int rowIndex, int columnIndex) {
					
					switch (columnIndex) {
	
					case 0:
						// Intitulé de la compétition
						return competitionsIHM.get(rowIndex).getNom();
	
					case 1:
						// Prenom
						return competitionsIHM.get(rowIndex).getDateCloture();
	
					case 2:
						// Annee
						return competitionsIHM.get(rowIndex).estEnEquipe();
	
					default:
						throw new IllegalArgumentException();
					}
				}
	
				public boolean isCellEditable(int arg0, int arg1) {
					// TODO Auto-generated method stub
					return false;
				}
	
				public void removeTableModelListener(TableModelListener arg0) {
					// TODO Auto-generated method stub
	
				}
	
				public void setValueAt(Object arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
	
				}
			};
		}

		// TODO Popup edition
		/** Liste des compétitions **/
		public JTable getTableau()
		{
		    JTable tableau = new JTable(lesDonnees());
			
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

		/** validation format des champs d'ajout d'une compétition **/
		private boolean isValid() 
		{
			if(nomValid() == true && clotureValid() == true)
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		/** contrôle sur l'intitulé de la compétition **/
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z ]{1,}");
		}
		
		/** contrôle sur la date de clôture de la compétition **/
		private boolean clotureValid() {
			boolean test = false;
			try 
			{
				LocalDate Cloture = LocalDate.parse(fieldAddCloture.getText());
				test = (Cloture != null);
			}
			catch (Exception e) 
			{
				
			}
			
			return test;
		}

		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			fieldAddCloture.setBorder(BorderFactory.createLineBorder(clotureValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled(isValid());
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
					competitionTable.tableChanged(new TableModelEvent(competitionTable.getModel()));
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
