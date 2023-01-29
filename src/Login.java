// mySQL JDBC GUI
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
public class Login
{
public static ResultSet rs;
public static int id = 0;
public static Connection con;
public static Statement st;
public static void main(String[] args){
 JFrame f=new JFrame();
 JLabel labell = new JLabel("STUD_ID: ");
 JTextField textl=new JTextField(20);
 JLabel label2 = new JLabel("");
 JButton b1= new JButton("Login");
 try{
 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign1", "root", "");
 st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
 rs=st.executeQuery("select * from students");
 }catch(Exception e){}
 JPanel p=new JPanel(new GridLayout(7,6));
 p.add(labell);
 p.add(textl);
 f.add(p);
 f.setVisible(true);
 f.pack();
 p.add(b1);
 p.add(label2);
 
 b1.addActionListener(new ActionListener() {
	 public void actionPerformed(ActionEvent arg0) {
	 try {
	 System.out.println(id);
	 PreparedStatement pstmt = con.prepareStatement("Select STUD_ID from students where STUD_ID=?");
	 String id = textl.getText();
	 pstmt.setString(1, id);
	 ResultSet rs = pstmt.executeQuery();
     if (rs.next()) {
    	 AreaOfCircle ac = new AreaOfCircle();
    	 ac.setVisible(true);
         JOptionPane.showMessageDialog(label2, "You have successfully logged in");
     } else {
         JOptionPane.showMessageDialog(label2, "Wrong STUD_ID");
     }
	 } catch (SQLException e) {
	 //TODO Auto-generated catch block
	 e.printStackTrace();
	 }}});
}
}