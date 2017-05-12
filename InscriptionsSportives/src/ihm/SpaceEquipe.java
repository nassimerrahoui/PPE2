package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Equipe;

public class SpaceEquipe
{		
	private JPanel ongletEqui = new JPanel();
	JPanel addEquipe = new JPanel();
	JTextField fieldAddNom = new JTextField();
	JButton buttonAdd = new JButton("Ajouter");
	MyTableModel TableModel = new MyTableModel();
	private JTable equipeTable = new JTable(TableModel);
		
	public SpaceEquipe()
	{
		// désactive le boutton ajouter
		buttonAdd.setEnabled(false);
						
		// active l'écoute sur les champs
		setListener();
	}
		
	public JPanel getOnglet(){
		return this.ongletEqui;
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
			for (Equipe eq : Container.getInscriptions().getEquipes())
			{
				equipes.add(eq);
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
				default:
					return Object.class;
				}
		}

		public int getColumnCount() {
			return 1;
		}

		public String getColumnName(int columnIndex) {
			switch (columnIndex) {
				case 0: return "Nom";
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
					// Intitulé de la compétition
					return equipesIHM.get(rowIndex).getNom();
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
		
	//TODO Popup edition
	/** Liste des équipes **/
	public JTable getTableau()
	{
		equipeTable.getTableHeader().setBackground(new Color(0, 149, 182));
		return equipeTable;
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
		
	private boolean isValid() {
		if(nomValid() == true) {
			return true;
		}	
		else {
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
			try {
				Container.getInscriptions().createEquipe(nom);
				TableModel.refresh();
			} 
			catch (enEquipeException | addCloseException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private void setListener() {
		fieldAddNom.addKeyListener(new fieldAddListener());
		buttonAdd.addActionListener(new buttonAddListener());
	}	
}
