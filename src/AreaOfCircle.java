import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AreaOfCircle extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

// Text field for receiving radius
  private JTextField jtf = new JTextField();

  // Text area to display contents
  private JTextArea jta = new JTextArea();
  
  private JButton jb = new JButton("Send");
  private JButton jb1 = new JButton("Exit");

  public static void main(String[] args) {
    new AreaOfCircle();
  }

  public AreaOfCircle() {
    // Panel p to hold the label and text field
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.add(jb, BorderLayout.WEST);
    p.add(jb1, BorderLayout.EAST);
    p.add(jtf, BorderLayout.CENTER);
    jtf.setHorizontalAlignment(JTextField.RIGHT);

    setLayout(new BorderLayout());
    add(p, BorderLayout.NORTH);
    add(new JScrollPane(jta), BorderLayout.CENTER);

    setTitle("Client");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!
    
    ActionListener ExitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
    
    jb1.addActionListener(ExitListener); // Register exit listener
    
    jb.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent arg0) {
				try {
					
					Socket socket = new Socket("localhost", 8000);
					 
					// obtaining input and out streams 
					DataInputStream dis = new DataInputStream(socket.getInputStream());
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); 
						
					// Get the radius from the text field
			        double radius = Double.parseDouble(jtf.getText().trim());

			        // Send the radius to the server
			        dos.writeDouble(radius);
			        dos.flush();

			        // Get area from the server
			        double area = dis.readDouble();

			        // Display to the text area
			        jta.append("Radius is " + radius + "\n");
			        jta.append("Area received from the server is "
			          + area + '\n');
				} catch (IOException ex) {
			        System.err.println(ex);
				} 	
		 }});
  }
}