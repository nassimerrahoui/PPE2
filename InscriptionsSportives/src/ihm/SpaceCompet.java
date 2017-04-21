package ihm;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;

public class SpaceCompet
	{
		private JPanel ongletComp = new JPanel();
		JPanel addCompetition = new JPanel();
		JTextField fieldAddNom = new JTextField();
		JTextField fieldAddCloture = new JTextField();
		JRadioButton fieldAddEnEquipe = new JRadioButton();
		JButton buttonAdd = new JButton("Ajouter");
		
		/** Construteur **/
		public SpaceCompet()
		{
			// désactive le boutton ajouter
			buttonAdd.setEnabled(false);
			
			// active l'écoute sur les champs
			setListener();
		}
		
		/** page onglet **/
		public JPanel getOnglet(){
			return this.ongletComp;
		}
		
		public String[] getEntete()
		{
			String[] entete = {"Nom", "Cloture", "En Equipe", "Supprimer"};
			return entete;
		}
		
		public Object[][] getData() throws enEquipeException, addCloseException
		{
			Object[][] data = new Object[30][30];
			int i = 0;
			int j = 0;
			for (Competition c : Container.getInscriptions().getCompetitions()) 
			{
				data[i][j] = c.getNom();
				j++;
				data[i][j] = c.getDateCloture();
				j++;
				data[i][j] = c.estEnEquipe();
				j++;
				data[i][j] = "Supprimer";
				j++;
				data[i][j] = c.getId();
				j-=4;
				i++;
			}
			
			return data;
		}
		
		// TODO JTableModel
		// TODO Popup edition
		/** Liste des compétitions **/
		public JTable getTableau() throws enEquipeException, addCloseException
		{
			JTable tableau = new JTable(getData(), getEntete());
			
			// couleur de l'entete du tableau
			tableau.getTableHeader().setBackground(new Color(0, 149, 182));
			
			//bouton supprimer;
			tableau.getColumn("Supprimer").setCellRenderer(new ButtonSuppRenderer());
			tableau.getColumn("Supprimer").setCellEditor(new ButtonSuppEditor(new JCheckBox()));
			
			return tableau;
		}
		
		/** Panneau d'ajout de compétition **/
		public JPanel getAddCompetition() 
		{
			// fond du panneau ajouter
			addCompetition.setBackground(Color.WHITE);
			
			// Initialisation des bordures de champ en rouge
			fieldAddNom.setBorder(BorderFactory.createLineBorder(Color.RED));
			fieldAddCloture.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			// Taille des champs
			fieldAddNom.setPreferredSize(new Dimension(130, 20));
			fieldAddCloture.setPreferredSize(new Dimension(130, 20));
			
			// Ajout des composants dans le panneau d'ajout de compétition
			addCompetition.add(new JLabel("Intitulé de la compétition : "));
			addCompetition.add(fieldAddNom);
			addCompetition.add(new JLabel("Date de cloture : "));
			addCompetition.add(fieldAddCloture);
			addCompetition.add(new JLabel("En Equipe : "));
			addCompetition.add(fieldAddEnEquipe);
			addCompetition.add(Box.createHorizontalStrut(10));
			addCompetition.add(Box.createHorizontalStrut(10));
			addCompetition.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
			addCompetition.setBorder(BorderFactory.createTitledBorder("Créer une compétition"));
			addCompetition.add(buttonAdd);
			
			return addCompetition;
		}
		
		/** Actualisation des données **/
		private void refreshSpaceCompet() 
		{
			ongletComp.invalidate();
			ongletComp.revalidate();
			ongletComp.repaint();
		}

		/** validation format des champs d'ajout d'une compétition **/
		private boolean isValid() 
		{
			if(nomValid() == true && clotureValid() == true)
			{
				return true;
			}
			
			else
			{
				return false;
			}
		}
		
		/** contrôle sur l'intitulé de la compétition **/
		private boolean nomValid() {
			return fieldAddNom.getText().matches("[a-zA-Z ]{1,}");
		}
		
		/** contrôle sur la date de clôture de la compétition **/
		private boolean clotureValid() {
			boolean test = false;
			try 
			{
				LocalDate Cloture = LocalDate.parse(fieldAddCloture.getText());
				if(Cloture != null)
				{
					test = true;
				}
				else
				{
					test = false;
				}
			}
			catch (Exception e) 
			{
				
			}
			
			return test;
		}

		/** bordure verte si le champ est correct et activation du bouton ajouter si tous les champs sont valides **/
		private void verifyField()
		{
			fieldAddNom.setBorder(BorderFactory.createLineBorder(nomValid() ? Color.GREEN : Color.RED));
			fieldAddCloture.setBorder(BorderFactory.createLineBorder(clotureValid() ? Color.GREEN : Color.RED));
			buttonAdd.setEnabled(isValid());
		}

		/** écoute les touches **/
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

		/** écoute les actions **/
		class buttonAddListener implements ActionListener 
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nom = fieldAddNom.getText();
				LocalDate Cloture = LocalDate.parse(fieldAddCloture.getText());
				boolean EnEquipe = isInTeam();

				try 
				{
					Container.getInscriptions().createCompetition(nom, Cloture, EnEquipe);
					refreshSpaceCompet();
				} 
				catch (enEquipeException | addCloseException e) 
				{
					e.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(
						null,
						fieldAddNom.getText() + " " 
						+ "a bien été ajouté !", "M2L Info",
						JOptionPane.INFORMATION_MESSAGE
				);
			}
			
			private boolean isInTeam() {
				if(fieldAddEnEquipe.isSelected()) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		
		/** Ajout des écouteurs pour chaque champ **/
		private void setListener() 
		{
			fieldAddNom.addKeyListener(new fieldAddListener());
			fieldAddCloture.addKeyListener(new fieldAddListener());
			buttonAdd.addActionListener(new buttonAddListener());

		}
		
		@SuppressWarnings("serial")
		class ButtonSuppRenderer extends JButton implements TableCellRenderer {

		    public ButtonSuppRenderer() {
		        setOpaque(true);
		    }

		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column) {
		        if (isSelected) {
		            setForeground(table.getSelectionForeground());
		            setBackground(table.getSelectionBackground());
		        } else {
		            setForeground(table.getForeground());
		            setBackground(UIManager.getColor("Button.background"));
		        }
		        setText((value == null) ? "" : value.toString());
		        return this;
		    }
		}

		@SuppressWarnings("serial")
		class ButtonSuppEditor extends DefaultCellEditor {

		    protected JButton button;
		    private String label;
		    private boolean isPushed;
		    Object[][] tab = new Object[30][30];

		    public ButtonSuppEditor(JCheckBox checkBox) {
		        super(checkBox);
		        button = new JButton();
		        button.setOpaque(true);
		        button.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                fireEditingStopped();
		                try 
		                {
		                	for (Competition c : Container.getInscriptions().getCompetitions()) 
		        			{
		                		Object nom = c.getNom();
		                		tab = getData();
								for (int i = 0; i < tab.length; i++) 
								{
									if(nom == tab[i][0])
									{
										//c.delete();
									}
								}
							}
						} 
		                catch (enEquipeException | addCloseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            }
		        });
		    }

		    @Override
		    public Component getTableCellEditorComponent(JTable table, Object value,
		            boolean isSelected, int row, int column) {
		        if (isSelected) {
		            button.setForeground(table.getSelectionForeground());
		            button.setBackground(table.getSelectionBackground());
		        } else {
		            button.setForeground(table.getForeground());
		            button.setBackground(table.getBackground());
		        }
		        label = (value == null) ? "" : value.toString();
		        button.setText(label);
		        isPushed = true;
		        return button;
		    }

		    @Override
		    public Object getCellEditorValue() {
		        if (isPushed) {
		            JOptionPane.showMessageDialog(button, "La compétition a été supprimé");
		        }
		        isPushed = false;
		        return label;
		    }

		    @Override
		    public boolean stopCellEditing() {
		        isPushed = false;
		        return super.stopCellEditing();
		    }
		}
	}
