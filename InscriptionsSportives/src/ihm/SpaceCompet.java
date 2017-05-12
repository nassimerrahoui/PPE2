 package ihm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Competition.setDateClotureException;

public class SpaceCompet
	{
		private JPanel ongletComp = new JPanel();
		JPanel addCompetition = new JPanel();
		JPanel updateCompetition = new JPanel();
		
		MyTableModel TableModel = new MyTableModel();
		private JTable competitionTable = new JTable(TableModel);
		
		JTextField fieldAddNom = new JTextField();
		JTextField fieldAddCloture = new JTextField();
		JRadioButton fieldAddEnEquipe = new JRadioButton();
		JButton buttonAdd = new JButton("Ajouter");
		
		JTextField fieldUpdateNom = new JTextField();
		JTextField fieldUpdateCloture = new JTextField();
		JRadioButton fieldUpdateEnEquipe = new JRadioButton();
		JButton buttonUpdate = new JButton("Confirmer");
		
		/** Construteur **/
		public SpaceCompet()
		{
			// d�sactive le boutton ajouter
			buttonAdd.setEnabled(false);
			
			// active l'�coute sur les champs
			setListener();
		}
		
		/** page onglet **/
		public JPanel getOnglet(){
			return this.ongletComp;
		}
		
		private class MyTableModel extends AbstractTableModel {
			
			private static final long serialVersionUID = -5329897223213964946L;
			ArrayList<Competition> competitionsIHM = new ArrayList<>(); 
			
			public MyTableModel() {
				competitionsIHM = load();
			}
			
			public ArrayList<Competition> load() {
			
				ArrayList<Competition> competitions = new ArrayList<>(); 
				try 
				{
					for (Competition c : Container.getInscriptions().getCompetitions())
					{
						competitions.add(c);
					}
				} 
				catch (enEquipeException | addCloseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return competitions;
			}

			public void addTableModelListener(TableModelListener arg0){
				// TODO Auto-generated method stub
			}

			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {

				case 0:
					return String.class;

				case 1:
					return LocalDate.class;

				case 2:
					return boolean.class;
					
				case 3:
					return int.class;
					
				default:
					return Object.class;
				}
			}

			public int getColumnCount() {
				return 4;
			}

			public String getColumnName(int columnIndex) {
				switch (columnIndex) {
				case 0: return "Nom";
				case 1: return "Date de cl�ture";
				case 2: return "En equipe ";
				case 3: return "ID";
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
					// Intitul� de la comp�tition
					return competitionsIHM.get(rowIndex).getNom();

				case 1:
					// Prenom
					return competitionsIHM.get(rowIndex).getDateCloture();

				case 2:
					// Annee
					return competitionsIHM.get(rowIndex).getEnEquipe();
					
				case 3:
					// ID
					return competitionsIHM.get(rowIndex).getId();
							
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
			
			public void refresh() {
				
				TableModel = new MyTableModel();
				TableModel.fireTableDataChanged();
				competitionTable.setModel(TableModel);
			}
		}
		
		// TODO Popup edition
		/** Liste des comp�titions **/
		public JTable getTableau()
		{
		    competitionTable.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return competitionTable;
		}
		
		/** Panneau d'ajout de comp�tition **/
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
			
			// Ajout des composants dans le panneau d'ajout de comp�tition
			addCompetition.add(new JLabel("Intitul� de la comp�tition : "));
			addCompetition.add(fieldAddNom);
			addCompetition.add(new JLabel("Date de cloture : "));
			addCompetition.add(fieldAddCloture);
			addCompetition.add(new JLabel("En Equipe : "));
			addCompetition.add(fieldAddEnEquipe);
			addCompetition.add(Box.createHorizontalStrut(10));
			addCompetition.add(Box.createHorizontalStrut(10));
			addCompetition.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addCompetition.setBorder(BorderFactory.createTitledBorder("Cr�er une comp�tition"));
			addCompetition.add(buttonAdd);
			
			return addCompetition;
		}
		
		public JPanel getUpdateCompetition() 
		{
			// fond du panneau ajouter
			updateCompetition.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldUpdateNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldUpdateCloture.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldUpdateNom.setPreferredSize(new Dimension(130, 20));
			fieldUpdateCloture.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout de comp�tition
			updateCompetition.add(new JLabel("Intitul� de la comp�tition : "));
			updateCompetition.add(fieldUpdateNom);
			updateCompetition.add(new JLabel("Date de cloture : "));
			updateCompetition.add(fieldUpdateCloture);
			updateCompetition.add(new JLabel("En Equipe : "));
			updateCompetition.add(fieldUpdateEnEquipe);
			updateCompetition.add(Box.createHorizontalStrut(10));
			updateCompetition.add(Box.createHorizontalStrut(10));
			updateCompetition.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			updateCompetition.setBorder(BorderFactory.createTitledBorder("Modifier cette comp�tition"));
			updateCompetition.add(buttonUpdate);
			
			return updateCompetition;
		}

		/** validation format des champs d'ajout d'une comp�tition **/
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
		
		/** contr�le sur l'intitul� de la comp�tition **/
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z ]{1,}");
		}
		
		/** contr�le sur la date de cl�ture de la comp�tition **/
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

		/** �coute les touches **/
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

		/** �coute les actions **/
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
					TableModel.refresh();
				} 
				catch (enEquipeException | addCloseException e) 
				{
					e.printStackTrace();
				}
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
		
		public class JTableListener implements MouseListener {
			 
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	String nom = (String) competitionTable.getValueAt(getTableau().getSelectedRow(), 0);
		    	LocalDate Cloture = (LocalDate) competitionTable.getValueAt(getTableau().getSelectedRow(), 1);
		    	boolean enEquipe = (boolean) competitionTable.getValueAt(getTableau().getSelectedRow(), 2);
		    	
		    	fieldUpdateNom.setText(nom);
		    	fieldUpdateCloture.setText(Cloture.toString());
		    	fieldUpdateEnEquipe.setSelected(enEquipe);
		    }
		 
		    @Override
		    public void mousePressed(java.awt.event.MouseEvent e) {
		         
		    }
		 
		    @Override
		    public void mouseReleased(java.awt.event.MouseEvent e) {

		    }
		 
		    @Override
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		        //entrer cellule
		         
		    }
		 
		    @Override
		    public void mouseExited(java.awt.event.MouseEvent e) {
		        //sortie cellule
		         
		    }
		}
		
		class buttonUpdateListener implements ActionListener 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nom = fieldUpdateNom.getText();
				LocalDate Cloture = LocalDate.parse(fieldUpdateCloture.getText());
				boolean EnEquipe = isInTeam();
				int ID = (int) competitionTable.getValueAt(getTableau().getSelectedRow(), 3);

				try 
				{
					for (Competition c : Container.getInscriptions().getCompetitions()) {
						
						if(c.getId() == ID && c.getId() <= Container.getInscriptions().getCompetitions().size()) {
							c.setNom(nom);
							c.setDateCloture(Cloture);
							c.setEnEquipe(EnEquipe);
							TableModel.refresh();
						}
					}
				} 
				catch (enEquipeException | addCloseException | setDateClotureException e) 
				{
					e.printStackTrace();
				}
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
		
		
		/** Ajout des �couteurs pour chaque champ **/
		private void setListener() 
		{
			fieldAddNom.addKeyListener(new fieldAddListener());
			fieldAddCloture.addKeyListener(new fieldAddListener());
			buttonAdd.addActionListener(new buttonAddListener());
			
			competitionTable.addMouseListener(new JTableListener());
			fieldUpdateNom.addKeyListener(new fieldAddListener());
			fieldUpdateCloture.addKeyListener(new fieldAddListener());
			buttonUpdate.addActionListener(new buttonUpdateListener());

		}
	}
