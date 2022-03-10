package BigPackage.Server;
import java.io.*;
import java.net.*;

public class Server2{

	private DatagramSocket socket;
 
	public Server2(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

	public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: QuoteServer <file> <port>");
            return;
        }
 
        int port = Integer.parseInt(args[0]);
 
        try {
            Server2 server = new Server2(port);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

	private void service() throws IOException {
        while (true) {

            System.out.println("Awaiting user input.");
            DatagramPacket request = new DatagramPacket(new byte[1], 1);
            socket.receive(request);
            System.out.println("Request received.");
 
            String quote = getRandomQuote();
            byte[] buffer = quote.getBytes();
 
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
 
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(response);
            System.out.println("Answer sent. \n");
        }
    }

	private String getRandomQuote(){
		String SSS = "Here you go! This is a reply from the bank.";
		return SSS;
	}
}



