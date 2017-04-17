package ihm;

import javax.swing.*;

public class SpaceEquipe
	{		
		private JPanel ongletEqui = new JPanel();
		
		public SpaceEquipe(JLabel titreOnglet)
		{
			this.ongletEqui.add(titreOnglet);
	
		}
		
		public JPanel getOnglet(){
			return this.ongletEqui;
		}
		
	}
