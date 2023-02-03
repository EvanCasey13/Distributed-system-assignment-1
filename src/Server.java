// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.awt.BorderLayout;
import java.io.*; 
import java.text.*; 
import java.util.*;
import java.util.Date;
import java.sql.*;
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
		
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				PreparedStatement pstmt = con.prepareStatement("Select STUD_ID, FNAME, SNAME, TOT_REQ from students where STUD_ID=?");
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				jta.append("A new client is connected : " + s + "\n"); 
				InetAddress inetAddress = s.getInetAddress();
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				String id = dis.readUTF();
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
			         jta.append("Welcome" + " " + name + " " + surname + " " + ", You are now connected to the Server." + " " + "Please enter the Radius of the Circle");
			         jta.append("Assigning new thread for this client " + id + "\n"); 
					 jta.append("Client's host name is " + inetAddress.getHostName() + "\n");
					 jta.append("Client's host address is " + inetAddress.getHostAddress() + "\n");
					 // create a new thread object 
						Thread t = new StudentHandler(s, dis, dos); 
						  
					// Invoking the start() method 
					t.start(); 
			     } else {
			         // Display to the text area
			         jta.setText("Sorry" + " " + id + "." + " " + "You are not a registered student. Try again or Exit");
			     }
				 } catch (SQLException e) {
				 //TODO Auto-generated catch block
				 e.printStackTrace();
				 }}};	
	 
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