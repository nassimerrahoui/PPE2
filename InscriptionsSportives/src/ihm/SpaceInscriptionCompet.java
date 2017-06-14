package ihm;


import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import metier.Candidat;
import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceInscriptionCompet {
	
	private MyTableModel TableModel = new MyTableModel();
	private static JTable candidatsInscritTable;
	
	public SpaceInscriptionCompet()
	{
		candidatsInscritTable = new JTable(TableModel);
	}
	
	public JTable getCandidatsInscrit()
	{
		return candidatsInscritTable;
	}
	
	private class MyTableModel extends AbstractTableModel 
	{
		
		private static final long serialVersionUID = -5329897223213964946L;
		ArrayList<Candidat> candidatsInscrit = new ArrayList<>();
		
		public MyTableModel() {
			candidatsInscrit = load(SpaceCompet.getIdCompetition());
		}
		
		public ArrayList<Candidat> load(int ID) {
		
			ArrayList<Candidat> candidats = new ArrayList<>();
			
			try 
			{
				for (Competition c : Container.getInscriptions().getCompetitions())
				{
					if(c.getId() == ID)
					{
						for (Candidat candidatC : c.getCandidats()) 
						{
							candidats.add(candidatC);
						}
					}
				}
			} 
			
			catch (enEquipeException | addCloseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return candidats;
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
			case 0: return "Nom";
			case 1: return "N° Candidat";
			default:
				break;
			}
			return "-1";
		}

		public int getRowCount() {
			return candidatsInscrit.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			
			switch (columnIndex) {

			case 0:
				return candidatsInscrit.get(rowIndex).getNom();
				
			case 1:
				return candidatsInscrit.get(rowIndex).getId();
						
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
		
	}
}
