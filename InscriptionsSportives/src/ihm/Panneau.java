package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FontMetrics;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.Inscriptions;

public class Panneau extends JPanel
{
	private JPanel menu = new JPanel();
	private PanneauPers panneauPersonne;
	private JLabel titre = new JLabel("Gestion de compétition");
	private JButton boutonPersonne = new JButton("Gestion de personne");
	private JButton boutonEquipe = new JButton("Gestion d'équipe");
	private JButton boutonCompetition = new JButton("Gestion de compétition");
	private static Inscriptions inscriptions;
	
	public Panneau()
	{
		
		this.setLayout(new BorderLayout());
		
		Font police = new Font("Century Gothic", Font.BOLD, 16);
		titre.setFont(police);
		titre.setHorizontalAlignment(JLabel.CENTER);
		this.add(titre,BorderLayout.NORTH);
		
		this.add(menu, BorderLayout.SOUTH);
		menu.add(boutonPersonne);
		menu.add(boutonEquipe);
		menu.add(boutonCompetition);
		boutonPersonne.addActionListener(new boutonPersonneListener());
		boutonEquipe.addActionListener(new boutonEquipeListener());
		boutonCompetition.addActionListener(new boutonCompetitionListener());	
	}
	
	private void setPanneauPersonne()
	{
		panneauPersonne = new PanneauPers();
		this.add(panneauPersonne,BorderLayout.CENTER);
	}
	
	private void setPanneauEquipe()
	{
		this.remove(panneauPersonne);
		this.repaint();
	}
	
	private void setPanneauCompetition()
	{
		this.remove(panneauPersonne);
		this.repaint();
	}
	
	public int centerText(Graphics g, Font font, String text)
	{
		return g.getFontMetrics(font).stringWidth(text)/2;
	}
	
	class boutonPersonneListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) 
		{
			titre.setText("Gestion des personnes");
			setPanneauPersonne();
		}
		
	}
	
	class boutonEquipeListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			titre.setText("Gestion des équipes");
			setPanneauEquipe();
		}
		
	}
	
	class boutonCompetitionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			titre.setText("Gestion des compétitions");
			setPanneauCompetition();
		}
		
	}
	
	public static Inscriptions getInscriptions()
	{
		inscriptions = Inscriptions.getInscriptions();
		return inscriptions;
	}
}
