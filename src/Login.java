// mySQL JDBC GUI
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class Login
{
public static ResultSet rs;
public static String name="", surname="";
public static Connection con;
public static Statement st;
public static int tot_req=0, stud_id=0;

public static void main(String[] args){
 new Login();
}

public Login() {
	JFrame f=new JFrame();
	 JLabel labell = new JLabel("STUD_ID: ");
	 JTextField textl=new JTextField(10);
	 JButton b1= new JButton("Login");
	 JTextArea jta = new JTextArea();
	 try{
	 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign1", "root", "");
	 st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	 rs=st.executeQuery("select * from students");
	 }catch(Exception e){}
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
					 String id = textl.getText();
					 Socket socket = new Socket("localhost", 8000);
					 
					// obtaining input and out streams 
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
					
					dos.writeUTF(id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 	
		 }});
}
}
