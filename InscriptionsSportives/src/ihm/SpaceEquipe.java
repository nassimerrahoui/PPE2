package ihm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.*;
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
