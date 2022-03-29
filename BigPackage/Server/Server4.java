package BigPackage.Server;
import java.io.*;
import java.net.*;
import java.math.*;

import javax.lang.model.util.ElementScanner14;

public class Server2{

double thres = 1;
int map[];
int xx;

	private DatagramSocket socket;
 
	public Server4(int port) throws SocketException {
        socket = new DatagramSocket(port);
        map = new int[128][128];
        xx=0;
    }

	public static byte[] serverMSG(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: QuoteServer <file> <port>");
            return 0;
        }
 
        int port = Integer.parseInt(args[0]);
 
        try {
            Server4 server = new Server4(port);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

	private byte[] service() throws IOException {
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

            int MsgID = MID;

            for(int i = 0; i<xx;i++)
            {
                
            }

            String reqdata = new String(buffermax, 0, request.getLength());

            System.out.println("Request: "+reqdata);

            byte[] buffer;

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
        }
    }

}


