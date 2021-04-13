package uno;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuGUI extends JFrame
{
	private JTextField textUno;
	
	// All components
	public MenuGUI()
	{
		getContentPane().setLayout(null);
		
		// Main panel
		JPanel panel = new JPanel();
		panel.setBackground(new Color(165, 42, 42));
		panel.setBounds(15, 16, 968, 565);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		// "The UNO game" title
		textUno = new JTextField();
		textUno.setBackground(new Color(216, 191, 216));
		textUno.setFont(new Font(".VnExotic", Font.BOLD, 30));
		textUno.setBounds(595, 0, 358, 114);
		textUno.setHorizontalAlignment(SwingConstants.CENTER);
		textUno.setText("The UNO Card Game");
		panel.add(textUno);
		textUno.setColumns(10);
		
		// "View Rules" button
		JButton rulesButton = new JButton("VIEW RULES");
		rulesButton.setBackground(UIManager.getColor("CheckBox.darkShadow"));
		rulesButton.setFont(new Font("Wide Latin", Font.PLAIN, 25));
		rulesButton.setBounds(595, 254, 358, 80);
		panel.add(rulesButton);
		
		// "Play" button
		JButton playButton = new JButton("PLAY");
		playButton.setBackground(UIManager.getColor("CheckBox.darkShadow"));
		playButton.setFont(new Font("Wide Latin", Font.BOLD, 25));
		playButton.setBounds(595, 159, 358, 80);
		panel.add(playButton);
		
		// "Exit" button
		JButton exitButton = new JButton("EXIT");
		exitButton.setBackground(UIManager.getColor("CheckBox.darkShadow"));
		exitButton.setFont(new Font("Wide Latin", Font.PLAIN, 25));
		exitButton.setBounds(595, 350, 358, 80);
		panel.add(exitButton);
		
		// Cover picture
		JLabel unoImage = new JLabel("(Insert Image here)");
		unoImage.setBackground(Color.PINK);
		unoImage.setBounds(15, 16, 424, 533);
		panel.add(unoImage);
	}
	
	// "Play" button action performer
	private void playButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		new AddPlayerNames().setVisible(true);
		this.dispose();
	}
	
	// "Exit" button action performer
	private void exitButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		System.exit(0);
	}
}