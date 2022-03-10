package BigPackage.Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

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

    Scanner sc = new Scanner(System.in);

    try{
        InetAddress address = InetAddress.getByName(hostname);
        DatagramSocket socket = new DatagramSocket();

        System.out.println("Connection protocol initialized.");

        int x = 1;

        while(x!=0){

            System.out.println("Enter your request Mr. Customer: ");
            String ssd = sc.nextLine();
            byte buffermax[] = ssd.getBytes();

            DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
            socket.send(request);

            System.out.println("Request sent.");

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            System.out.println("Answer received: \n");

            String quote = new String(buffer, 0, response.getLength());

            System.out.println(quote);
            System.out.println();

            Thread.sleep(100);

            System.out.println("Enter \"NO\" to stop communication else continue with any other input for the next prompt:");
            if(sc.nextLine().equals("NO"))
                x = 0;

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
