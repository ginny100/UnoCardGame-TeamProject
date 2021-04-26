package uno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class EndControl implements ActionListener{
	
	private JPanel container;
	private GameClient client;
	
	
	public EndControl(JPanel container,GameClient client)
	{
		this.container = container;
		this.client = client;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
