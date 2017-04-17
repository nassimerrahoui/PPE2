package ihm;

import javax.swing.*;


public class SpacePersonne
	{	
		private JPanel ongletPers = new JPanel();
		
		public SpacePersonne(JLabel titreOnglet)
		{
			
			this.ongletPers.add(titreOnglet);
			
		}
		
		
		
		public JPanel getOnglet(){
			return this.ongletPers;
		}
			
	}

	
