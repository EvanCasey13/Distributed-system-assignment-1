// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.awt.BorderLayout;
import java.io.*; 
import java.text.*; 
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.net.*; 

// Server class 
public class Server extends JFrame
{ 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea();
	
	public static void main(String[] args) throws IOException 
	{ 
		new Server();
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(8000); 
		
		// running infinite loop for getting 
		// client request 
		
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				InetAddress inetAddress = s.getInetAddress();
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				 
				System.out.println("Assigning new thread for this client"); 
				System.out.println("Client's host name is " + inetAddress.getHostName());
				System.out.println("Client's IP Address is " + inetAddress.getHostAddress());
				// create a new thread object 
				Thread t = new StudentHandler(s, dis, dos); 
				  
				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
	public Server() {
	    // Place text area on the frame
	    setLayout(new BorderLayout());
	    add(new JScrollPane(jta), BorderLayout.CENTER);
	  
	    setTitle("Server");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!
	    
	    jta.append("Server started at " + new Date() + '\n');
	}
} 