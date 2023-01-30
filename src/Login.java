// mySQL JDBC GUI
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
public class Login
{
public static ResultSet rs;
public static String name="", surname="";
public static Connection con;
public static Statement st;
public static int tot_req=0, stud_id=0;

public static void main(String[] args){
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
    	 PreparedStatement ps = con.prepareStatement("UPDATE students SET TOT_REQ=? WHERE STUD_ID=?");
    	 ps.setInt(1, stud_id);
    	 ps.setInt(2, tot_req);
         ps.executeUpdate();
    	
    	 
    	 AreaOfCircle ac = new AreaOfCircle();
    	 ac.setTitle("Area of a circle" + stud_id);
    	 ac.setVisible(true);
         jta.setText("Welcome" + " " + name + " " + surname +  + tot_req +" " + ", You are now connected to the Server." + " " + "Please enter the Radius of the Circle");
     } else {
         // Display to the text area
         jta.setText("Sorry" + " " + id + "." + " " + "You are not a registered student. Try again or Exit");
     }
	 } catch (SQLException e) {
	 //TODO Auto-generated catch block
	 e.printStackTrace();
	 }}});
}
}