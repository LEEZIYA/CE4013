package BigPackage.Server;
import java.io.*;
import java.net.*;
import java.math.*;

import javax.lang.model.util.ElementScanner14;

public class Server5{

double thres = 1;
int map[];
int xx;
InetAddress INA;
int CP;

	private DatagramSocket socket;
 
	public Server5(int port) throws SocketException { // USAGE : Constructed once only per server run!

        socket = new DatagramSocket(port);
        map = new int[512];
        xx=-1;
    }

	public static byte[] serverMsgWait(String[] args) { //USAGE : Used to receive a byte buffer and takes care of duplicates.

        try {
            return server.serviceReceive();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

	private byte[] serviceReceive() throws IOException {

        int retflg = 0;

        while (true) {

            System.out.println("Awaiting user input.");

            byte[] buffermax = new byte[512];
            DatagramPacket request = new DatagramPacket(buffermax, buffermax.length);

            while(true)
            {
                socket.receive(request);
                System.out.println("HELLO");
                if(Math.random()<thres)
                    break;
            }

            System.out.println("Request received.");

            //Demystifying MSG for at Most Once Semantics.

           // byte[] UID = Arrays.copyOfRange(buffermax,0,4);
            byte[] MID = Arrays.copyOfRange(buffermax,0,4);
            buffermax = Arrays.copyOfRange(buffermax,4,buffermax.length);

            int MsgID = (int)MID;

            for(int i = 0; i<=xx;i++)
            {
                if(MsgID==map[i])
                    retflg = 1;
            }
            if (reflg==1)
                retflg = 0;
                continue;
            
            xx++;
            map[xx]=MsgID;

            INA = request.getAddress();
            CP = request.getPort();

            //String reqdata = new String(buffermax, 0, request.getLength());

           // System.out.println("Request: "+reqdata);

            return buffermax;

            /**byte[] buffer;

            if(reqdata.equals("New Account"))
            {
                String quote = "Your new account is ready!";buffer = quote.getBytes();
            }
            else if(reqdata.equals("Deposit"))
            {
                String quote = "Your deposit was successful!";buffer = quote.getBytes();
            }
            else if(reqdata.equals("Closing"))
            {
                String quote = "Thanks for banking with us Mr. Loyal Customer. Bye bye!";buffer = quote.getBytes();
            }
            else{
                String quote = "Re try maybe with another input.";buffer = quote.getBytes();
            }


 
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
 
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            //socket.send(response);
            System.out.println("Answer NOT REALLLY sent. \n");
            **/
        }
    }

    public void serverMsgSend(byte[] a){ //USAGE : Use this to reply to the client. Automatically handles sending message to last receiver.
 
        DatagramPacket response = new DatagramPacket(buffer, buffer.length, INA, CP);
        socket.send(response);
        System.out.println("Answer NOT REALLLY sent. \n");
            

    }

}



