package BigPackage.Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.math.*;

public class Client4{

    private DatagramSocket socket;
    public static byte[] bufferino;
 
    public Client4(int port, byte b) throws SocketException {
        bufferino = b;
        socket = new DatagramSocket(port);}

    public static byte sendMSG(String[] args){
        if (args.length < 2) {
            System.out.println("Syntax: QuoteClient <hostname> <port>");
            return 0;
    }
    int rsndcnt = 1;
    int rsndflg = 0;

    while(rsndcnt<4)
    {
        System.out.println("ENTERED RSNDCNT LOOP: "+rsndcnt);

    String hostname = "localhost";
    int port = Integer.parseInt(args[1]);

    //Scanner sc = new Scanner(System.in);

    double thres = 1;

    try{
        InetAddress address = InetAddress.getByName(hostname);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);

        System.out.println("Connection protocol initialized.");

        int x = 1;

        while(x!=0){

            //System.out.println("Enter your request Mr. Customer: You can try \"New Account\" or \"Closing\" or \"Deposit\"");
           // String ssd = sc.nextLine();
            byte buffermax[] = bufferino;

            DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
            socket.send(request);

            System.out.println("Request sent.");

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            while(true)
            {
                socket.receive(response);
                System.out.println("HELLO MR. ERROR LOOP!");
                if(Math.random()<thres)
                    break;
            }

            System.out.println("Answer received. \n");

            //String quote = new String(buffer, 0, response.getLength()); //Needs to be returned.

            //System.out.println(quote);
            //System.out.println();

            //Thread.sleep(100);

            //System.out.println("Enter \"NO\" to stop communication else continue with any other input for the next prompt:");
           // if(sc.nextLine().equals("NO"))
           //     x = 0;

        }
    } catch (SocketTimeoutException ex) {
        System.out.println("Timeout error: " + ex.getMessage());
        ex.printStackTrace();
        rsndflg=1;
        rsndcnt++;  
    } catch (IOException ex) {
        System.out.println("Client error: " + ex.getMessage());
        ex.printStackTrace();
    } catch (InterruptedException ex) {
        ex.printStackTrace();
    }

    if(rsndflg==1)
    {
        rsndflg=0;
        continue;
    }

    break;
    }
    System.out.println("RSNDCNT LOOP LEFT!");
    System.out.println("Communication failed after 4 attempts of receiving. Stop.");
    if (rsndcnt == 4)
        return 0;
    else
        return buffer;
}

}
