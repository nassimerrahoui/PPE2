package ihm;

import javax.swing.JFrame;

public class Container extends JFrame
{
	private Panneau panneau = new Panneau();
	
	public Container()
	{   
		
	    this.setTitle("Gestionnaire de compétition");
	    this.setSize(800, 600);
	    this.setResizable(false);
	    this.setLocationRelativeTo(null);               
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
	    this.setContentPane(panneau);               
	    this.setVisible(true);
	 } 
	
	public static void main(String[] args)
	{
		Container content = new Container();
        content.setVisible(true);
	}
}

