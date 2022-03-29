package BigPackage.Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.math.*;

public class Client5{

    private DatagramSocket socket;
    public static byte[] bufferino;
    public static int msgcnt;
 
    public Client5(int port) throws SocketException { // USAGE : Initialize and construct Client Socket. Provide port.
        socket = new DatagramSocket(port);
        msgcnt = 0;
    }

    public static byte[] sendMSG(byte[] b){ //USAGE : Used to send message of byte reference. Returns received message. Handles all faults. Returns 0 if fail.
       // if (args.length < 2) {
       //     System.out.println("Syntax: QuoteClient <hostname> <port>");
        //    return 0;
    
    bufferino = b;
    int rsndcnt = 1;
    int rsndflg = 0;
    msgcnt++;

    while(rsndcnt<4)
    {
        System.out.println("ENTERED RSNDCNT LOOP: "+rsndcnt);

    String hostname = "localhost";
    int port = Integer.parseInt(args[1]);

    //Scanner sc = new Scanner(System.in);

    double thres = 1; //USAGE : Used to set fault percentage. 1 means no fault and 0 means total fault. Scale accordingly.

    try{
        InetAddress address = InetAddress.getByName(hostname);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(5000);

        System.out.println("Connection protocol initialized.");

        int x = 1;

        while(x!=0){

            //System.out.println("Enter your request Mr. Customer: You can try \"New Account\" or \"Closing\" or \"Deposit\"");
           // String ssd = sc.nextLine();
           // byte buffermax[] = bufferino;

            //ADDING MSGCNT per sent request.
            
            System.out.println("Non-Numbered Bytes: "+Arrays.toString(bufferino));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //out.write((byte)UseID);
            out.write((byte)msgcnt);
            out.write(bufferino);
            byte[] buffermax = out.toByteArray();

            System.out.println("Non-Numbered Bytes: "+Arrays.toString(buffermax));

            DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
            socket.send(request);

            System.out.println("Request sent. Request ID for given user: "+msgcnt);

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
    if (rsndcnt == 4){
        System.out.println("Communication failed after 4 attempts of receiving. Stop.");
        return 0;}
    else
        return buffer;
}

}
