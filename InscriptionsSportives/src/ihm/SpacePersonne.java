package ihm;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.*;
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

	
