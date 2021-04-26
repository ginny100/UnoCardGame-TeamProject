package Uno;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class GamePanel extends JPanel{
	//0 = 0
	//1-10 = 1-20
	//Drawtwo = 21,22
	//Reverse = 23,24
	//Skip = 25,26
	private JButton blueCardButtons[];
	private JButton redCardButtons[];
	private JButton yellowCardButtons[];
	private JButton greenCardButtons[];
	//Wild = 0-3
	//Wild4 = 4-7
	private JButton wildCardButtons[];

	private JLabel[] blueCardLabels;
	private JLabel[] redCardLabels;
	private JLabel[] yellowCardLabels;
	private JLabel[] greenCardLabels;
	private JLabel[] wildCardLabels;

	private JButton[] otherPlayerDeck;
	private JButton deckButton;
	private JButton userPlayButton;
	
	private JLabel[] unoLabels;
	private JLabel userCardCount;
	
	private JButton chooseColorButtons[];

	public GamePanel(GameControl Gc)
	{
		setLayout(null);

		//Probably need to make a text log of the things players did
		JLabel instructionLabel = new JLabel("Waiting for players to join", JLabel.CENTER);
		instructionLabel.setBounds(400, 0, 150, 30);
		Gc.setInstructionLabel(instructionLabel);
		add(instructionLabel);
		String path = "/images/PNGs";

		//Create Label for the top card on the deck
		blueCardLabels = new JLabel[14];
		redCardLabels = new JLabel[14];
		yellowCardLabels = new JLabel[14];
		greenCardLabels = new JLabel[14];
		wildCardLabels = new JLabel[2];
		
		for(int i = 0; i < 13; i++) {
			blueCardLabels[i] = new JLabel();
			blueCardLabels[i].setBounds(115, 5,95,160);
			add(blueCardLabels[i]);
			ImageIcon icon = new ImageIcon(GamePanel.class.getResource(path + "/Blue_"+i+".png"));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			blueCardLabels[i].setIcon(icon);
			blueCardLabels[i].setVisible(false);
		}
		for(int i = 0; i < 13; i++) {
			redCardLabels[i] = new JLabel();
			redCardLabels[i].setBounds(115, 5,95,160);
			add(redCardLabels[i]);
			ImageIcon icon = new ImageIcon(GamePanel.class.getResource(path + "/Red_"+i+".png"));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			redCardLabels[i].setIcon(icon);
			redCardLabels[i].setVisible(false);
		}
		for(int i = 0; i < 13; i++) {
			yellowCardLabels[i] = new JLabel();
			yellowCardLabels[i].setBounds(115, 5,95,160);
			add(yellowCardLabels[i]);
			ImageIcon icon = new ImageIcon(GamePanel.class.getResource(path + "/Yellow_"+i+".png"));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			yellowCardLabels[i].setIcon(icon);
			yellowCardLabels[i].setVisible(false);
		}
		for(int i = 0; i < 13; i++) {
			greenCardLabels[i] = new JLabel();
			greenCardLabels[i].setBounds(115, 5,95,160);
			add(greenCardLabels[i]);
			ImageIcon icon = new ImageIcon(GamePanel.class.getResource(path + "/Green_"+i+".png"));
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			greenCardLabels[i].setIcon(icon);
			greenCardLabels[i].setVisible(false);
		}
		wildCardLabels[0] = new JLabel();
		wildCardLabels[0].setBounds(115, 5,95,160);
		add(wildCardLabels[0]);
		ImageIcon icon = new ImageIcon(GamePanel.class.getResource(path + "/Wild.png"));
		Image img = icon.getImage() ;  
		Image newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		wildCardLabels[0].setIcon(icon);
		wildCardLabels[0].setVisible(false);

		wildCardLabels[1] = new JLabel();
		wildCardLabels[1].setBounds(115, 5,95,160);
		add(wildCardLabels[1]);
		icon = new ImageIcon(GamePanel.class.getResource(path + "/Wild_Draw.png"));
		img = icon.getImage() ;  
		newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		wildCardLabels[1].setIcon(icon);
		wildCardLabels[1].setVisible(false);



		//Create the buttons for the players cards
		blueCardButtons = new JButton[14];
		redCardButtons = new JButton[14];
		yellowCardButtons = new JButton[14];
		greenCardButtons = new JButton[14];
		wildCardButtons = new JButton[2];

		for(int i = 0; i < 13; i++) {
			blueCardButtons[i] = new JButton("B,"+i);
			blueCardButtons[i].setBounds(115, 525,95,160);
			blueCardButtons[i].addActionListener(Gc);
			add(blueCardButtons[i]);
			icon = new ImageIcon(GamePanel.class.getResource(path + "/Blue_"+i+".png"));
			img = icon.getImage() ;  
			newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			blueCardButtons[i].setIcon(icon);
			blueCardButtons[i].setVisible(false);
		}
		for(int i = 0; i < 13; i++) {
			redCardButtons[i] = new JButton("R," +i);
			redCardButtons[i].setBounds(115, 525,95,160);
			redCardButtons[i].addActionListener(Gc);
			add(redCardButtons[i]);
			icon = new ImageIcon(GamePanel.class.getResource(path + "/Red_"+i+".png"));
			img = icon.getImage() ;  
			newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			redCardButtons[i].setIcon(icon);
			redCardButtons[i].setVisible(false);
		}
		for(int i = 0; i < 13; i++) {
			yellowCardButtons[i] = new JButton("Y,"+i);
			yellowCardButtons[i].setBounds(115, 525,95,160);
			yellowCardButtons[i].addActionListener(Gc);
			add(yellowCardButtons[i]);
			icon = new ImageIcon(GamePanel.class.getResource(path + "/Yellow_"+i+".png"));
			img = icon.getImage() ;  
			newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			yellowCardButtons[i].setIcon(icon);
			yellowCardButtons[i].setVisible(false);
		}
		for(int i = 0; i < 13; i++) {
			greenCardButtons[i] = new JButton("G,"+i);
			greenCardButtons[i].setBounds(115, 525,95,160);
			greenCardButtons[i].addActionListener(Gc);
			add(greenCardButtons[i]);
			icon = new ImageIcon(GamePanel.class.getResource(path + "/Green_"+i+".png"));
			img = icon.getImage() ;  
			newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			greenCardButtons[i].setIcon(icon);
			greenCardButtons[i].setVisible(false);
		}
		wildCardButtons[0] = new JButton("W,0");
		wildCardButtons[0].setBounds(115, 525,95,160);
		wildCardButtons[0].addActionListener(Gc);
		add(wildCardButtons[0]);
		icon = new ImageIcon(GamePanel.class.getResource(path + "/Wild.png"));
		img = icon.getImage() ;  
		newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		wildCardButtons[0].setIcon(icon);
		wildCardButtons[0].setVisible(false);

		wildCardButtons[1] = new JButton("W,1");
		wildCardButtons[1].setBounds(115, 525,95,160);
		wildCardButtons[1].addActionListener(Gc);
		add(wildCardButtons[1]);
		icon = new ImageIcon(GamePanel.class.getResource(path + "/Wild_Draw.png"));
		img = icon.getImage() ;  
		newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		wildCardButtons[1].setIcon(icon);
		wildCardButtons[1].setVisible(false);


		//Create buttons for the decks of cards
		deckButton = new JButton("D");
		deckButton.setBounds(5, 5,95,160);
		deckButton.addActionListener(Gc);
		add(deckButton);
		icon = new ImageIcon(GamePanel.class.getResource(path + "/Deck.png"));
		img = icon.getImage() ;  
		newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		deckButton.setIcon(icon);
		deckButton.setVisible(false);


		//Create button for the users deck of cards
		userPlayButton = new JButton("PD");
		userPlayButton.setBounds(5, 525,95,160);
		userPlayButton.addActionListener(Gc);
		add(userPlayButton);
		icon = new ImageIcon(GamePanel.class.getResource(path + "/Deck.png"));
		img = icon.getImage();  
		newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
		userPlayButton.setIcon(icon);
		userPlayButton.setVisible(false);
		
		userCardCount = new JLabel();
		userCardCount.setBounds(5,500,20,20);
		add(userCardCount);
		userCardCount.setText("7");
		userCardCount.setVisible(false);
		
		
		otherPlayerDeck = new JButton[4];
		unoLabels = new JLabel[4];	// Even though the user will only see the other 3, have a label for ALL players for simplicity

		for(int i = 0; i < 4; i++) {
			otherPlayerDeck[i] = new JButton("uno");
			otherPlayerDeck[i].setBounds(700, (50+(200*i)),95,160);
			otherPlayerDeck[i].addActionListener(Gc);
			add(otherPlayerDeck[i]);
			icon = new ImageIcon(GamePanel.class.getResource(path + "/Deck.png"));
			img = icon.getImage();  
			newimg = img.getScaledInstance( 90, 160,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			otherPlayerDeck[i].setIcon(icon);
			otherPlayerDeck[i].setVisible(false);
			
			unoLabels[i] = new JLabel();
			unoLabels[i].setBounds(650, (100+(200*i)),20,20);
			add(unoLabels[i]);
			unoLabels[i].setText("7");
			unoLabels[i].setVisible(false);
		}
		
		
		
		String[] color = new String[4];
		color[0] = "Blue";
		color[1] = "Red";
		color[2] = "Yellow";
		color[3] = "Green";
		
		chooseColorButtons = new JButton[4];
		
		Color[] colors = {Color.blue, Color.red, Color.yellow, Color.green};
		
		for(int i = 0; i < 4; i++) {
			chooseColorButtons[i] = new JButton();
			chooseColorButtons[i].setActionCommand(color[i]);
			chooseColorButtons[i].setBounds((5+(50*i)), 300,20,20);
			chooseColorButtons[i].setBackground(colors[i]);
			chooseColorButtons[i].addActionListener(Gc);
			add(chooseColorButtons[i]);
			chooseColorButtons[i].setVisible(false);
		}
		
		Gc.setPlayedLabels(blueCardLabels, redCardLabels, yellowCardLabels, greenCardLabels, wildCardLabels);
		Gc.setChooseColorButtons(chooseColorButtons);
		Gc.setUserCardCount(userCardCount);
		Gc.setUnoLabels(unoLabels);
		Gc.setDecks(blueCardButtons, redCardButtons, yellowCardButtons, greenCardButtons, wildCardButtons,
				otherPlayerDeck, deckButton, userPlayButton);
	}
}
