package ihm;

import javax.swing.*;

public class SpaceWBuilder
	{
	private JTable table;
		
		/** Construteur **/
		public SpaceWBuilder()
		{
			JFrame f = new JFrame("Gestion des Inscriptions");
			f.setSize(900, 700);
			f.getContentPane().setLayout(null);
			
			table = new JTable();
			table.setBounds(10, 40, 450, 338);
			f.getContentPane().add(table);
			
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBounds(0, 0, 884, 21);
			f.getContentPane().add(menuBar);
			
			JMenu mnGnral = new JMenu("Cr\u00E9er");
			menuBar.add(mnGnral);
			
			JMenuItem mntmNouvellePersonne = new JMenuItem("Cr\u00E9er une personne");
			mnGnral.add(mntmNouvellePersonne);
			
			JMenuItem mntmNouvelleComptition = new JMenuItem("Cr\u00E9er une comp\u00E9tition");
			mnGnral.add(mntmNouvelleComptition);
			
			JMenuItem mntmCrerUnequipe = new JMenuItem("Cr\u00E9er une \u00E9quipe");
			mnGnral.add(mntmCrerUnequipe);
			
			JMenu mnGrer = new JMenu("G\u00E9rer");
			menuBar.add(mnGrer);
		}
		
		public static void main(String[] args) {
			new SpaceWBuilder();
		}
	}
