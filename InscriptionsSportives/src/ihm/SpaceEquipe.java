package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.SortedSet;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Equipe;


public class SpaceEquipe
	{
		private JPanel ongletEquipe = new JPanel();
		private JPanel addEquipe = new JPanel();
		private JPanel updateEquipe = new JPanel();
		
		private MyTableModel TableModel = new MyTableModel();
		private JTable equipeTable = new JTable(TableModel);
		
		private JLabel labelEquipe = new JLabel("Nom de l'équipe : ");
		
		private JTextField fieldAddNom = new JTextField();
		private JButton buttonAdd = new JButton("Ajouter");
		
		private JTextField fieldUpdateNom = new JTextField();
		private JButton buttonUpdate = new JButton("Confirmer");
		
		private JDialog modifyWindow = new JDialog();
		
		/** Construteur **/
		public SpaceEquipe()
		{
			// désactive le boutton ajouter
			buttonAdd.setEnabled(false);
			
			// active l'écoute sur les champs
			setListener();
		}
		
		/** page onglet **/
		public JPanel getOnglet(){
			return this.ongletEquipe;
		}
		
		private class MyTableModel extends AbstractTableModel {
			
			private static final long serialVersionUID = -5329897223213964946L;
			ArrayList<Equipe> equipesIHM = new ArrayList<>(); 
			
			public MyTableModel() {
				equipesIHM = load();
			}
			
			public ArrayList<Equipe> load() {
			
				ArrayList<Equipe> equipes = new ArrayList<>(); 
				try 
				{
					for (Equipe e : Container.getInscriptions().getEquipes())
					{
						equipes.add(e);
					}
				} 
				catch (enEquipeException | addCloseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				return equipes;
			}

			public void addTableModelListener(TableModelListener arg0){
				// TODO Auto-generated method stub
			}

			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {

				case 0:
					return String.class;

				case 1:
					return int.class;
					
				default:
					return Object.class;
				}
			}

			public int getColumnCount() {
				return 2;
			}

			public String getColumnName(int columnIndex) {
				switch (columnIndex) {
				case 0: return "Nom de l'équipe";
				case 1: return "ID";
				default:
					break;
				}
				return "-1";
			}

			public int getRowCount() {
				return equipesIHM.size();
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				
				switch (columnIndex) {

				case 0:
					return equipesIHM.get(rowIndex).getNom();
					
				case 1:
					// ID
					return equipesIHM.get(rowIndex).getId();
							
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
				equipeTable.setModel(TableModel);
			}
		}
		
		// TODO Popup edition
		/** Liste des équipes **/
		public JTable getTableau()
		{
			equipeTable.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return equipeTable;
		}
		
		/** Panneau d'ajout des équipes **/
		public JPanel getAddEquipe() 
		{
			// fond du panneau ajouter
			addEquipe.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldAddNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldAddNom.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout de compétition
			addEquipe.add(labelEquipe);
			addEquipe.add(fieldAddNom);
			addEquipe.add(Box.createHorizontalStrut(10));
			addEquipe.add(Box.createHorizontalStrut(10));
			addEquipe.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addEquipe.setBorder(BorderFactory.createTitledBorder("Créer une équipe"));
			addEquipe.add(buttonAdd);
			
			return addEquipe;
		}
		
		public JPanel getUpdateEquipe() 
		{
			// fond du panneau ajouter
			updateEquipe.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldUpdateNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldUpdateNom.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout des équipes
			updateEquipe.add(labelEquipe);
			updateEquipe.add(fieldUpdateNom);
			updateEquipe.add(buttonUpdate);
			
			return updateEquipe;
		}

		/** validation format des champs d'ajout d'une équipe **/
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
		
		/** contrôle sur le nom de l'équipe **/
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z0-9 ]{1,}");
		}
		
		/** validation format des champs de modification d'une équipe **/
		private boolean isValidModify() 
		{
			if(nomValidModify() == true)
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		/** contrôle sur le nom de l'équipe lors de la modification **/
		private boolean nomValidModify() {
			return fieldUpdateNom.getText().matches("[a-zA-Z0-9 ]{1,}");
		}

		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled(isValid());
		}
		
		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyFieldModify()
		{
			fieldUpdateNom.setBorder(BorderFactory.createLineBorder(nomValidModify() ? Color.GREEN : Color.RED));
			buttonUpdate.setEnabled(isValidModify());
		}

		/** écoute les touches **/
		class fieldListener implements KeyListener 
		{

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				verifyField();
				verifyFieldModify();
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

				try 
				{
					Container.getInscriptions().createEquipe(nom);
					fieldAddNom.setText("");
					verifyField();
					TableModel.refresh();
				} 
				catch (enEquipeException | addCloseException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		public class JTableListener implements MouseListener {
			 
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	String nom = (String) equipeTable.getValueAt(getTableau().getSelectedRow(), 0);
		    	
		    	fieldUpdateNom.setText(nom);
		    	
		    	modifyWindow.setSize(400, 400);
		    	modifyWindow.add(getUpdateEquipe());
		    	modifyWindow.setVisible(true);
		    	verifyFieldModify();
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
				int ID = (int) equipeTable.getValueAt(getTableau().getSelectedRow(), 1);

				try 
				{
					SortedSet<Equipe> Equipes = Container.getInscriptions().getEquipes();
					for (Equipe e : Equipes) {
						
						if(e.getId() == ID) {
								e.setNom(nom);
								TableModel.refresh();
						}
					}
				} 
				catch (enEquipeException | addCloseException e) 
				{
					e.printStackTrace();
				}
				
				modifyWindow.dispose();
			}
		}
		
		/** Ajout des écouteurs pour chaque champ **/
		private void setListener() 
		{
			fieldAddNom.addKeyListener(new fieldListener());
			buttonAdd.addActionListener(new buttonAddListener());
			
			equipeTable.addMouseListener(new JTableListener());
			fieldUpdateNom.addKeyListener(new fieldListener());
			buttonUpdate.addActionListener(new buttonUpdateListener());

		}
	}
