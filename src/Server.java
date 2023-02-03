// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*; 
import java.text.*; 
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

// Server class 
public class Server extends JFrame
{ 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTextArea jta = new JTextArea("");
	public static ResultSet rs;
	public static String name="", surname="";
	public static Connection con;
	public static Statement st;
	public static int tot_req=0, stud_id=0;
	
	public static void main(String[] args) throws IOException 
	{ 
		try{
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign1", "root", "");
			 st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 rs=st.executeQuery("select * from students");
			 }catch(Exception e){}
		new Server();
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(8000); 
		
		// running infinite loop for getting 
		// client request 
		JFrame f=new JFrame();
		JLabel labell = new JLabel("STUD_ID: ");
		JTextField textl=new JTextField(10);
		JButton b1= new JButton("Login");
		JTextArea jta = new JTextArea();
		JPanel p=new JPanel(new GridLayout(5,5));
		 p.add(labell);
		 p.add(textl);
		 p.add(b1);
		 p.add(jta);
		 f.add(p);
		 f.setVisible(true);
		 f.pack();
		 f.setSize(500, 300);
		 
		 b1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent arg0) {
			 try {
			 PreparedStatement pstmt = con.prepareStatement("Select STUD_ID, FNAME, SNAME, TOT_REQ from students where STUD_ID=?");
			 String id = textl.getText();
			
			 pstmt.setString(1, id);
			 ResultSet rs = pstmt.executeQuery();
		     if (rs.next()) {
		    	 stud_id = rs.getInt("STUD_ID");
		    	 name=rs.getString("FNAME");
		    	 surname=rs.getString("SNAME");
		    	 tot_req=rs.getInt("TOT_REQ");
		    	 tot_req++;
		    	 String query = "UPDATE students SET TOT_REQ=? WHERE STUD_ID=?";
		    	 PreparedStatement ps = con.prepareStatement(query);
		    	 ps.setInt(1, tot_req);
		    	 ps.setInt(2, stud_id);
		         ps.executeUpdate();
		    	 
		    	 AreaOfCircle ac = new AreaOfCircle();
		    	 ac.setTitle("Area of a circle");
		    	 ac.setVisible(true);
		         jta.setText("Welcome" + " " + name + " " + surname + " " + ", You are now connected to the Server." + " " + "Please enter the Radius of the Circle");
		     } else {
		         // Display to the text area
		         jta.setText("Sorry" + " " + id + "." + " " + "You are not a registered student. Try again or Exit");
		     }
			 } catch (SQLException e) {
			 //TODO Auto-generated catch block
			 e.printStackTrace();
			 }}});
		while (true) 
			
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				jta.append("A new client is connected : " + s + "\n"); 
				InetAddress inetAddress = s.getInetAddress();
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			
				jta.append("Assigning new thread for this client \n"); 
				jta.append("Client's host name is " + inetAddress.getHostName() + "\n");
				jta.append("Client's host address is " + inetAddress.getHostAddress() + "\n");
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