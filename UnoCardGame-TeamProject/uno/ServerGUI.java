package uno;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ServerGUI extends JFrame {
	private JLabel status; //Initialized to “Not Connected”
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JButton listen;
	private JButton close;
	private JButton stop;
	private JButton quit;
	private GameServer server;
	private Database database;

	public ServerGUI() {

		server = new GameServer();
		database = new Database();

		server.setDatabase(database);
		
		//this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//size of the frame
		setSize(600, 500);

		//The first jpanel attached to the frame
		JPanel jp = new JPanel(); 
		getContentPane().add(jp);

		//making all the panels attached to the first jpanel
		JPanel north = new JPanel(new GridLayout(0, 1, 0, 0));
		JPanel south = new JPanel();
		JPanel center = new JPanel(new BorderLayout());
		jp.setLayout(new BorderLayout(0, 0));
		jp.add(north, BorderLayout.NORTH);
		jp.add(south, BorderLayout.SOUTH);
		jp.add(center, BorderLayout.CENTER);

		//The status label at the top of the JFrame
		JPanel jpstatus = new JPanel();
		status = new JLabel("Not Connected");
		JLabel s = new JLabel("Status: ");
		status.setForeground(Color.RED);
		s.setLabelFor(status);
		jpstatus.add(s);
		jpstatus.add(status);
		north.add(jpstatus);
		server.setStatus(status);

		//The buttons at the bottom of the jFrame
		listen = new JButton("Listen");
		close = new JButton("Close");
		stop = new JButton("Stop");
		quit = new JButton("Quit");
		south.add(listen);
		south.add(close);
		south.add(stop);
		south.add(quit);

		//This is the labels and textfields
		for(int i = 0; i < 2; i++) {
			JPanel jpanel = new JPanel();
			JLabel jlabel = new JLabel(labels[i]);
			jpanel.add(jlabel);
			textFields[i] = new JTextField();
			jpanel.add(textFields[i]);
			textFields[i].setColumns(10);
			north.add(jpanel);
		}
		textFields[0].setText("8300");
		textFields[1].setText("500");

		//Client textarea
		//JPanel Textareas = new JPanel();
		JLabel loglabel = new JLabel("Server Log Below");
		loglabel.setHorizontalAlignment(SwingConstants.CENTER);
		log = new JTextArea(10,50);
		log.setLineWrap(true);
		JScrollPane logSP = new JScrollPane(log);
		JPanel logSPpanel = new JPanel();
		JPanel loglabelpanel = new JPanel();
		logSPpanel.add(logSP);
		loglabelpanel.add(loglabel);
		center.add(loglabelpanel, BorderLayout.NORTH);
		center.add(logSPpanel, BorderLayout.CENTER);
		server.setLog(log);

		//Making the action listener class for the actions
		ActionListener actionListener = new ServerEventHandler(server, status, listen, close, stop, 
				quit, textFields[0], textFields[1], log);

		//Adding actions to the action listener class
		listen.addActionListener(actionListener);
		close.addActionListener(actionListener);
		stop.addActionListener(actionListener);
		quit.addActionListener(actionListener);

		setVisible(true);
	}

	public static void main(String[] args)
	{
		new ServerGUI(); //args[0] represents the title of the GUI
	}
}
