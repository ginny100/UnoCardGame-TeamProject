package uno;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class AddPlayerNames extends JFrame
{
	// Global data fields
	public ArrayList<String> playerIds;
	private JTextField nameTextField;
	private JLabel player1Label;
	private JLabel player2Label;
	private JLabel player3Label;
	private JLabel player4Label;
	private JLabel player5Label;
	private JLabel player6Label;
	private JLabel player7Label;
	private JLabel player8Label;
	
	// Constructor
	public AddPlayerNames()
	{
		initComponents();
		playerIds = new ArrayList<>();
	}
	
	// Get players'ids
	public String[] getPlayerIds()
	{
		String[] pIds = playerIds.toArray(new String[playerIds.size()]);
		return pIds;
	}
	
	// All components
	private void initComponents()
	{
		getContentPane().setLayout(null);
		
		// Main panel
		JPanel panel = new JPanel();
		panel.setBounds(15, 16, 465, 225);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		// "Save" button
		JButton saveButton = new JButton("SAVE");
		saveButton.setBounds(15, 180, 115, 29);
		panel.add(saveButton);
		
		// "Done" button
		JButton doneButton = new JButton("DONE\r\n");
		doneButton.setBounds(335, 180, 115, 29);
		panel.add(doneButton);
		
		// Page label
		JLabel addNamesLabel = new JLabel("Add the names of the players");
		addNamesLabel.setFont(new Font("Swis721 Cn BT", Font.BOLD, 20));
		addNamesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addNamesLabel.setBounds(15, 0, 435, 29);
		panel.add(addNamesLabel);
		
		// TextField's label
		JLabel nameLabel = new JLabel("Name of the Player");
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBounds(15, 45, 143, 20);
		panel.add(nameLabel);
		
		// TextField
		JTextField nameTextField = new JTextField();
		nameTextField.setBounds(188, 45, 262, 26);
		panel.add(nameTextField);
		nameTextField.setColumns(10);
		
		// All players to be displayed after being added
		player1Label = new JLabel("New label");
		player1Label.setBounds(15, 95, 69, 20);
		panel.add(player1Label);
		
		player2Label = new JLabel("New label");
		player2Label.setBounds(15, 131, 69, 20);
		panel.add(player2Label);
		
		player3Label = new JLabel("New label");
		player3Label.setBounds(145, 95, 69, 20);
		panel.add(player3Label);
		
		player4Label = new JLabel("New label");
		player4Label.setBounds(145, 131, 69, 20);
		panel.add(player4Label);
		
		player5Label = new JLabel("New label");
		player5Label.setBounds(251, 95, 69, 20);
		panel.add(player5Label);
		
		player6Label = new JLabel("New label");
		player6Label.setBounds(251, 131, 69, 20);
		panel.add(player6Label);
		
		player7Label = new JLabel("New label");
		player7Label.setBounds(381, 95, 69, 20);
		panel.add(player7Label);
		
		player8Label = new JLabel("New label");
		player8Label.setBounds(381, 131, 69, 20);
		panel.add(player8Label);
	}

	// "Save" button's action performer
	private void saveButtonActionPerformed (java.awt.event.ActionEvent evt)
	{
		if (nameTextField.getText().isEmpty())
		{
			JLabel message = new JLabel("Please enter a name");
			message.setFont(new Font("Arial", Font.BOLD, 48));
			JOptionPane.showMessageDialog(null, message);
		}
		else
		{
			String name = nameTextField.getText().trim();
			playerIds.add(name);
			
			if (playerIds.size() == 1)
			{
				player1Label.setText(playerIds.get(0));
			}
			else if (playerIds.size() == 2)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
			}
			else if (playerIds.size() == 3)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
				player3Label.setText(playerIds.get(2));
			}
			else if (playerIds.size() == 4)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
				player3Label.setText(playerIds.get(2));
				player4Label.setText(playerIds.get(3));
			}
			else if (playerIds.size() == 5)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
				player3Label.setText(playerIds.get(2));
				player4Label.setText(playerIds.get(3));
				player5Label.setText(playerIds.get(4));
			}
			else if (playerIds.size() == 6)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
				player3Label.setText(playerIds.get(2));
				player4Label.setText(playerIds.get(3));
				player5Label.setText(playerIds.get(4));
				player6Label.setText(playerIds.get(5));
			}
			else if (playerIds.size() == 7)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
				player3Label.setText(playerIds.get(2));
				player4Label.setText(playerIds.get(3));
				player5Label.setText(playerIds.get(4));
				player6Label.setText(playerIds.get(5));
				player7Label.setText(playerIds.get(6));
			}
			else if (playerIds.size() == 8)
			{
				player1Label.setText(playerIds.get(0));
				player2Label.setText(playerIds.get(1));
				player3Label.setText(playerIds.get(2));
				player4Label.setText(playerIds.get(3));
				player5Label.setText(playerIds.get(4));
				player6Label.setText(playerIds.get(5));
				player7Label.setText(playerIds.get(6));
				player8Label.setText(playerIds.get(7));
			}
			
			if (playerIds.size() > 0 && playerIds.size() < 9)
			{
				JLabel message = new JLabel("Successful Save");
				message.setFont(new Font("Arial", Font.BOLD, 48));
				JOptionPane.showMessageDialog(null, message);
				nameTextField.setText("");
			}
			
			if (playerIds.size() == 9)
			{
				playerIds.remove(name);
				JLabel message = new JLabel("There can only be between 2-8 players!");
				message.setFont(new Font("Arial", Font.BOLD, 48));
				JOptionPane.showMessageDialog(null, message);
				nameTextField.setText("");
			}
		}
	}

	// "Done" button's action performer
	private void doneButtonActionPerformed (java.awt.event.ActionEvent evt)
	{
		if (playerIds.size() == 1 || playerIds.size() == 0)
		{
			JLabel message = new JLabel("There must be at least 2 players!");
			message.setFont(new Font("Arial", Font.BOLD, 48));
			JOptionPane.showMessageDialog(null, message);
			nameTextField.setText("");
		}
		else
		{
			this.dispose();
			new GameStage(playerIds).setVisible(true);
		}
	}
}