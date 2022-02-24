package BigPackage.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
//import java.util.Scanner;

import BigPackage.BufferPointer;
import BigPackage.MarshUtil;

/**
 * Created by dan.geabunea on 6/3/2016.
 */
public class UdpUnicastServer implements Runnable {
    /**
     * The port where the client is listening.
     */
    private final int clientPort;


    public UdpUnicastServer(int clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        /**
         * Create a new server socket and bind it to a free port. I have chosen
         * one in the 49152 - 65535 range, which are allocated for internal applications
         */

         //Scanner sc = new Scanner(System.in);
         int testData = 123;
         byte[] buf = new byte[4];
         BufferPointer bufPt = new BufferPointer();
         MarshUtil.marshInt(testData, buf, bufPt);

        try (DatagramSocket serverSocket = new DatagramSocket(50000)) {
            // The server will generate 3 messages and send them to the client
           // for (int i = 0; i < 3; i++) {
           //     String message = "Message number " + i;
              //  DatagramPacket datagramPacket = new DatagramPacket(
             //           message.getBytes(),
            //            message.length(),
              //          InetAddress.getLocalHost(),
             //           clientPort
             //   );

            //System.out.println("Welcome to DSB! Please choose 1) Create or 2) Check");
            //String message = sc.nextLine();
            // String message = ("LMAO it works!");
            DatagramPacket datagramPacket = new DatagramPacket(
                        buf,
                        buf.length,
                       InetAddress.getLocalHost(),
                       clientPort);

                serverSocket.send(datagramPacket);
            
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}