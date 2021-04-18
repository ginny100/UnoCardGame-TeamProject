package Uno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


public class ServerEventHandler implements ActionListener{
	private JLabel stat;
	private JButton Listen;
	private JButton Close;
	private JButton ServerStop;
	private JButton Quit;
	private JTextField port;
	private JTextField timeout;
	private JTextArea log;
	private ChatServer server;

	public ServerEventHandler(ChatServer server, JLabel stat, JButton Listen, JButton Close, JButton ServerStop, JButton Quit,
			JTextField port, JTextField timeout, JTextArea log){
		this.server = server;
		this.stat = stat;
		this.Listen = Listen;
		this.Close = Close;
		this.ServerStop = ServerStop;
		this.Quit = Quit;
		this.port = port;
		this.timeout = timeout;
		this.log = log;
	}

	public void actionPerformed(ActionEvent e) {
		//server buttons
		if(e.getSource() == Listen) {
			if(!server.isListening()) {
				if (!port.getText().equals("") || !timeout.getText().equals("")) {
					try {
						server.setPort(Integer.parseInt(port.getText()));
						server.setTimeout(Integer.parseInt(timeout.getText()));
						server.listen();
					} catch(IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					log.append("Port Number/timeout not entered before pressing Listen\n");
				}
			}
			else {
				log.append("Server currently started\n");
			}
		}
		else if(e.getSource() == Close) {
			if(server.isListening()) {
				try {
					server.close();
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
			else {
				log.append("Server not currently started\n");
			}
		}
		else if(e.getSource() == ServerStop) {
			if(server.isListening()) {
				server.stopListening();
			}
			else {
				log.append("Server not currently started\n");
			}
		}
		else if(e.getSource() == Quit) {
			System.exit(0);
		}
	}
}

