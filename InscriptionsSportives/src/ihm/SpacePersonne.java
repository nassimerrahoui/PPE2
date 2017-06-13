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
import metier.Personne;


public class SpacePersonne
	{
		private JPanel ongletPers = new JPanel();
		private JPanel addPersonne = new JPanel();
		private JPanel updatePersonne = new JPanel();
		
		private MyTableModel TableModel = new MyTableModel();
		private JTable personneTable = new JTable(TableModel);
		
		private JLabel labelNom = new JLabel("Nom : ");
		private JLabel labelPrenom = new JLabel("Prénom : ");
		private JLabel labelMail = new JLabel("Mail : ");
		
		private JLabel labelNomModify = new JLabel("Nom : ");
		private JLabel labelPrenomModify = new JLabel("Prénom : ");
		private JLabel labelMailModify = new JLabel("Mail : ");
		
		private JTextField fieldAddNom = new JTextField();
		private JTextField fieldAddPrenom = new JTextField();
		private JTextField fieldAddMail = new JTextField();
		private JButton buttonAdd = new JButton("Ajouter");
		
		private JTextField fieldUpdateNom = new JTextField();
		private JTextField fieldUpdatePrenom = new JTextField();
		private JTextField fieldUpdateMail = new JTextField();
		private JButton buttonUpdate = new JButton("Confirmer");
		
		private JButton buttonDelete = new JButton("Supprimer cette personne");
		
		private JDialog modifyWindow = new JDialog();
		
		/** Construteur **/
		public SpacePersonne()
		{
			// désactive le boutton ajouter
			buttonAdd.setEnabled(false);
			
			// active l'écoute sur les champs
			setListener();
		}
		
		/** page onglet **/
		public JPanel getOnglet(){
			return this.ongletPers;
		}
		
		private class MyTableModel extends AbstractTableModel {
			
			private static final long serialVersionUID = -5329897223213964946L;
			ArrayList<Personne> personnesIHM = new ArrayList<>(); 
			
			public MyTableModel() {
				personnesIHM = load();
			}
			
			public ArrayList<Personne> load() {
			
				ArrayList<Personne> personnes = new ArrayList<>(); 
				try 
				{
					for (Personne p : Container.getInscriptions().getPersonnes())
						{
							personnes.add(p);
						}
				} 
				catch (enEquipeException | addCloseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				return personnes;
			}

			public void addTableModelListener(TableModelListener arg0){
				// TODO Auto-generated method stub
			}

			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {

				case 0:
					return String.class;
				
				case 1:
					return String.class;
				
				case 2:
					return String.class;

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
				case 1: return "Prénom";
				case 2: return "Mail";
				case 3: return "ID";
				default:
					break;
				}
				return "-1";
			}

			public int getRowCount() {
				return personnesIHM.size();
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				
				switch (columnIndex) {

				case 0:
					return personnesIHM.get(rowIndex).getNom();
				
				case 1:
					return personnesIHM.get(rowIndex).getPrenom();
					
				case 2:
					return personnesIHM.get(rowIndex).getMail();
					
				case 3:
					return personnesIHM.get(rowIndex).getId();
							
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
				personneTable.setModel(TableModel);
			}
		}
		
		// TODO Popup edition
		/** Liste des équipes **/
		public JTable getTableau()
		{
			personneTable.getTableHeader().setBackground(new Color(0, 149, 182));
			
			return personneTable;
		}
		
		/** Panneau d'ajout des équipes **/
		public JPanel getAddPersonne() 
		{
			// fond du panneau ajouter
			addPersonne.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldAddNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldAddPrenom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldAddMail.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldAddNom.setPreferredSize(new Dimension(130, 20));
			fieldAddPrenom.setPreferredSize(new Dimension(130, 20));
			fieldAddMail.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout de compétition
			addPersonne.add(labelNomModify);
			addPersonne.add(fieldAddNom);
			addPersonne.add(labelPrenomModify);
			addPersonne.add(fieldAddPrenom);
			addPersonne.add(labelMailModify);
			addPersonne.add(fieldAddMail);
			addPersonne.add(Box.createHorizontalStrut(10));
			addPersonne.add(Box.createHorizontalStrut(10));
			addPersonne.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addPersonne.setBorder(BorderFactory.createTitledBorder("Créer une personne"));
			addPersonne.add(buttonAdd);
			
			return addPersonne;
		}
		
		public JPanel getUpdatePersonne() 
		{
			// fond du panneau ajouter
			updatePersonne.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldUpdateNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldUpdatePrenom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldUpdateMail.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldUpdateNom.setPreferredSize(new Dimension(130, 20));
			fieldUpdatePrenom.setPreferredSize(new Dimension(130, 20));
			fieldUpdateMail.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout des personnes
			updatePersonne.add(labelNom);
			updatePersonne.add(fieldUpdateNom);
			updatePersonne.add(labelPrenom);
			updatePersonne.add(fieldUpdatePrenom);
			updatePersonne.add(labelMail);
			updatePersonne.add(fieldUpdateMail);
			updatePersonne.add(buttonUpdate);
			updatePersonne.add(buttonDelete);
			
			return updatePersonne;
		}

		/** validation format des champs d'ajout d'une personne **/
		private boolean isValid() 
		{
			if(nomValid() == true && prenomValid() && mailValid())
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
			return fieldAddNom.getText().matches("[a-zA-Z0-9 ]{1,}");
		}
		
		private boolean prenomValid() {
			return fieldAddPrenom.getText().matches("[a-zA-Z0-9 ]{1,}");
		}
		
		private boolean mailValid() {
			return fieldAddMail.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		}
		
		/** validation format des champs de modification d'une personne **/
		private boolean isValidModify() 
		{
			if(nomValidModify() == true && prenomValidModify() && mailValidModify())
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		/** contrôle sur le nom de la personne **/
		private boolean nomValidModify() {
			return fieldUpdateNom.getText().matches("[a-zA-Z0-9 ]{1,}");
		}
		
		private boolean prenomValidModify() {
			return fieldUpdatePrenom.getText().matches("[a-zA-Z0-9 ]{1,}");
		}
		
		private boolean mailValidModify() {
			return fieldUpdateMail.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		}

		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			fieldAddPrenom.setBorder(BorderFactory.createLineBorder(prenomValid() ? Color.GREEN : Color.RED));
			fieldAddMail.setBorder(BorderFactory.createLineBorder(mailValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled(isValid());
		}
		
		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyFieldModify()
		{
			fieldUpdateNom.setBorder(BorderFactory.createLineBorder(nomValidModify() ? Color.GREEN : Color.RED));
			fieldUpdatePrenom.setBorder(BorderFactory.createLineBorder(prenomValidModify() ? Color.GREEN : Color.RED));
			fieldUpdateMail.setBorder(BorderFactory.createLineBorder(mailValidModify() ? Color.GREEN : Color.RED));
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
				String prenom = fieldAddPrenom.getText();
				String mail = fieldAddMail.getText();

				try 
				{
					Container.getInscriptions().createPersonne(nom, prenom, mail);
					fieldAddNom.setText("");
					fieldAddPrenom.setText("");
					fieldAddMail.setText("");
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
		    	String nom = (String) personneTable.getValueAt(getTableau().getSelectedRow(), 0);
		    	String prenom = (String) personneTable.getValueAt(getTableau().getSelectedRow(), 1);
		    	String mail = (String) personneTable.getValueAt(getTableau().getSelectedRow(), 2);
		    	
		    	fieldUpdateNom.setText(nom);
		    	fieldUpdatePrenom.setText(prenom);
		    	fieldUpdateMail.setText(mail);
		    	
		    	modifyWindow.setSize(400, 400);
		    	modifyWindow.add(getUpdatePersonne());
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
				String prenom = fieldUpdatePrenom.getText();
				String mail = fieldUpdateMail.getText();
				
				int ID = (int) personneTable.getValueAt(getTableau().getSelectedRow(), 3);

				try 
				{
					SortedSet<Personne> Personnes = Container.getInscriptions().getPersonnes();
					for (Personne p : Personnes) {
						
						if(p.getId() == ID) {
							
								p.setNom(nom);
								p.setPrenom(prenom);
								p.setMail(mail);
								
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
		
		class buttonDeleteListener implements ActionListener 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ID = (int) personneTable.getValueAt(getTableau().getSelectedRow(), 3);

				try 
				{
					SortedSet<Personne> Personnes = Container.getInscriptions().getPersonnes();
					for (Personne p : Personnes) {
						
						if(p.getId() == ID) {
							
								p.delete();
								
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
			fieldAddPrenom.addKeyListener(new fieldListener());
			fieldAddMail.addKeyListener(new fieldListener());
			buttonAdd.addActionListener(new buttonAddListener());
			
			personneTable.addMouseListener(new JTableListener());
			fieldUpdateNom.addKeyListener(new fieldListener());
			fieldUpdatePrenom.addKeyListener(new fieldListener());
			fieldUpdateMail.addKeyListener(new fieldListener());
			buttonUpdate.addActionListener(new buttonUpdateListener());
			
			buttonDelete.addActionListener(new buttonDeleteListener());

		}
	}
