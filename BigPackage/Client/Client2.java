package BigPackage.Client;
import java.io.*;
import java.net.*;

public class Client2{
    
    private DatagramSocket socket;
 
    public Client2(int port) throws SocketException {
        socket = new DatagramSocket(port);}

    public static void main(String[] args){
        if (args.length < 2) {
            System.out.println("Syntax: QuoteClient <hostname> <port>");
            return;
    }

    String hostname = "localhost";
    int port = Integer.parseInt(args[1]);

    try{
        InetAddress address = InetAddress.getByName(hostname);
        DatagramSocket socket = new DatagramSocket();

        System.out.println("Sent!");

        while(true){

            DatagramPacket request = new DatagramPacket(new byte[1],1,address,port);
            socket.send(request);

            System.out.println("Sent!");

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            System.out.println("Recd!");

            String quote = new String(buffer, 0, response.getLength());

            System.out.println(quote);
            System.out.println();

            Thread.sleep(100);

        }
    } catch (SocketTimeoutException ex) {
        System.out.println("Timeout error: " + ex.getMessage());
        ex.printStackTrace();
    } catch (IOException ex) {
        System.out.println("Client error: " + ex.getMessage());
        ex.printStackTrace();
    } catch (InterruptedException ex) {
        ex.printStackTrace();
    }
    }
}
