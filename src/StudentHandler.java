import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextArea;

class StudentHandler extends Thread {
// Text area for displaying contents
private JTextArea jta = new JTextArea();
//The socket the client is connected through
private Socket socket;
//The ip address of the client
private InetAddress address;
//The input and output streams to the client
private DataInputStream inputFromClient;
private DataOutputStream outputToClient;
// The Constructor for the client

// Constructor 
public StudentHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
{ 
	this.socket = s; 
	this.inputFromClient = dis; 
	this.outputToClient = dos; 
} 

/*
* The method that runs when the thread starts
*/
public void run() {
try {
// Send+Receive+Calculations goes here
	// Receive radius from the client
    double radius = inputFromClient.readDouble();

    // Compute area
    double area = radius * radius * Math.PI;

    // Send area back to the client
    outputToClient.writeDouble(area);

} catch (Exception e) {
System.err.println(e + " on " + socket);
}
}
}