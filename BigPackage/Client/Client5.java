package BigPackage.Client;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.math.*;
import java.util.Arrays;
import java.net.InetAddress;
public class Client5{

    public DatagramSocket socket;
    public byte[] bufferino;
    public int msgcnt;
    public int usms;
    public byte[] buffstore;
    String servipstring;
 
    public Client5(int portSRC, String xxx, int sesd) throws SocketException { // USAGE : Initialize and construct Client Socket. Provide port.
        try{socket = new DatagramSocket(portSRC);
        msgcnt = 0;
        usms = sesd;
        servipstring=xxx;
        InetAddress tp = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + tp.getHostAddress());
        System.out.println("Host Name:- " + tp.getHostName());}
        catch(Exception e)
        {
            System.out.println("Client Constructor Fault.");
        }

    }



/////////////////////////////////////////////////////////////////////////////////////////








    public byte[] sendMSG(byte[] b){ //USAGE : Used to send message of byte reference. Returns received message. Handles all faults. Returns 0 if fail.
       // if (args.length < 2) {
       //     System.out.println("Syntax: QuoteClient <hostname> <port>");
        //    return 0;
    buffstore=new byte[512];
    bufferino = b;
    int rsndcnt = 1;
    int rsndflg = 0;
    msgcnt++;

    while(rsndcnt<4)
    {
        //System.out.println("ENTERED RSNDCNT LOOP: "+rsndcnt); DEBUGERY

    String hostname = "localhost";
    int port = 40500;

    //Scanner sc = new Scanner(System.in);

    double thres = 0.8; //USAGE : Used to set fault percentage. 1 means no fault and 0 means total fault. Scale accordingly.

    try{
        //servip = InetAddress.getByName(xxx);
        InetAddress address = InetAddress.getByName(servipstring);
       // InetAddress address = InetAddress.getByName(hostname);
        //DatagramSocket socket = new DatagramSocket();
        //servip = InetAddress.getByName(xxx);
        socket.setSoTimeout(5000);

        //DEBUGERYSystem.out.println("Connection protocol initialized.");


            //System.out.println("Enter your request Mr. Customer: You can try \"New Account\" or \"Closing\" or \"Deposit\"");
           // String ssd = sc.nextLine();
           // byte buffermax[] = bufferino;

            //ADDING MSGCNT per sent request.
            
           // System.out.println("Non-Numbered Bytes: "+Arrays.toString(bufferino));

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //out.write((byte)UseID);
            out.write(0);
            out.write(0);
            out.write(0);
            out.write(usms);
            out.write(0);
            out.write(0);
            out.write(0);
            out.write(msgcnt);
            out.write(bufferino);
            byte[] buffermax = out.toByteArray();

          //  System.out.println("Numbered Bytes: "+Arrays.toString(buffermax));



            DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
            socket.send(request);

            System.out.println("Request sent. Request ID for given user: "+msgcnt);

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);

            while(true)
            {
                socket.receive(response);
                if(Math.random()<thres){
                    System.out.println("Simulated fault.");
                    break;}
            }

           //DEBUGERY System.out.println("Answer received. \n");

            buffstore = buffer;

            //DEBUGERYSystem.out.println("They Sent: "+Arrays.toString(buffstore));

            //String quote = new String(buffer, 0, response.getLength()); //Needs to be returned.

            //System.out.println(quote);
            //System.out.println();

            //Thread.sleep(100);

            //System.out.println("Enter \"NO\" to stop communication else continue with any other input for the next prompt:");
           // if(sc.nextLine().equals("NO"))
           //     x = 0;

        
    } catch (SocketTimeoutException ex) {
        System.out.println("Timeout error " +msgcnt);
        //System.out.println("Timeout error: " + ex.getMessage());
        //ex.printStackTrace();
        rsndflg=1;
        rsndcnt++;  
    } catch (IOException ex) {
        System.out.println("Client error: " + ex.getMessage());
        ex.printStackTrace();
    } //catch (InterruptedException ex) {
       // ex.printStackTrace();
  //  }

    if(rsndflg==1)
    {
        rsndflg=0;
        continue;
    }

    break;
    }
    //DEBUGERYSystem.out.println("RSNDCNT LOOP LEFT!");
    if (rsndcnt == 20){
        System.out.println("Communication failed after "+rsndcnt+" attempts of receiving. Stop.");
        return new byte[0];}
    else
        return buffstore;
}








/////////////////////////////////////////////////////////////////////








public void startMonitor(byte[] b){ //USAGE : Used to send message of byte reference. Returns received message. Handles all faults. Returns 0 if fail.
    // if (args.length < 2) {
    //     System.out.println("Syntax: QuoteClient <hostname> <port>");
     //    return 0;
 buffstore=new byte[512];
 bufferino = b;
 int rsndcnt = 1;
 int rsndflg = 0;
 msgcnt++;

 while(rsndcnt<4)
 {
    //DEBUGERY System.out.println("ENTERED RSNDCNT LOOP: "+rsndcnt);

 String hostname = "localhost";
 int port = 40500;

 //Scanner sc = new Scanner(System.in);

 double thres = 0.7; //USAGE : Used to set fault percentage. 1 means no fault and 0 means total fault. Scale accordingly.

 try{
     InetAddress address = InetAddress.getByName(hostname);
     //DatagramSocket socket = new DatagramSocket();
     socket.setSoTimeout(5000);

     System.out.println("Connection protocol initialized.");


         //System.out.println("Enter your request Mr. Customer: You can try \"New Account\" or \"Closing\" or \"Deposit\"");
        // String ssd = sc.nextLine();
        // byte buffermax[] = bufferino;

         //ADDING MSGCNT per sent request.
         
        // System.out.println("Non-Numbered Bytes: "+Arrays.toString(bufferino));

         ByteArrayOutputStream out = new ByteArrayOutputStream();
         //out.write((byte)UseID);
         out.write(0);
         out.write(0);
         out.write(0);
         out.write(usms);
         out.write(0);
         out.write(0);
         out.write(0);
         out.write(msgcnt);
         out.write(bufferino);
         byte[] buffermax = out.toByteArray();

        // System.out.println("Numbered Bytes: "+Arrays.toString(buffermax));



         DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
         socket.send(request);

         System.out.println("Request sent. Request ID for given user: "+msgcnt);

         byte[] buffer = new byte[512];
         DatagramPacket response = new DatagramPacket(buffer, buffer.length);

         while(true)
         {
             socket.receive(response);
            //DEBUGERY System.out.println("HELLO MR. ERROR LOOP!");
             if(Math.random()<thres)
                 break;
         }

        //DEBUGERY System.out.println("Answer received. \n");

         buffstore = buffer;

         //DEBUGERY System.out.println("They Sent: "+Arrays.toString(buffstore));

         //String quote = new String(buffer, 0, response.getLength()); //Needs to be returned.

         //System.out.println(quote);
         //System.out.println();

         //Thread.sleep(100);

         //System.out.println("Enter \"NO\" to stop communication else continue with any other input for the next prompt:");
        // if(sc.nextLine().equals("NO"))
        //     x = 0;

     
 } catch (SocketTimeoutException ex) {
     System.out.println("Timeout error " +msgcnt);
     //System.out.println("Timeout error: " + ex.getMessage());
     //ex.printStackTrace();
     rsndflg=1;
     rsndcnt++;  
 } catch (IOException ex) {
     System.out.println("Client error: " + ex.getMessage());
     ex.printStackTrace();
 } //catch (InterruptedException ex) {
    // ex.printStackTrace();
//  }

 if(rsndflg==1)
 {
     rsndflg=0;
     continue;
 }

 break;
 }
  //DEBUGERY.println("RSNDCNT LOOP LEFT!");
 if (rsndcnt == 20){
     System.out.println("Subscription failed after 20 attempts of receiving. Stop.");}
 else
     System.out.println("Subscription Successful");
}



////////////////////////////////////////////////////////////////////////////////////////








public byte[] listenMonitor(int milli)
{
try{
    socket.setSoTimeout(milli);
    byte[] buffer = new byte[512];
    DatagramPacket response = new DatagramPacket(buffer, buffer.length);

    socket.receive(response);

    //DEBUGERY System.out.println("Subscription received. \n");
    //DEBUGERY System.out.println("They Sent: "+Arrays.toString(buffer));

    return buffer;}
    catch (SocketTimeoutException ex) {
        System.out.println("Subscription Elapsed.");
        return new byte[0];
    } 
    catch(SocketException SEMILLI)
    {
        System.out.println("Subscription Elapsed.");
        return new byte[0];
    }
    catch (IOException ex) {
        System.out.println("Client error: " + ex.getMessage());
        ex.printStackTrace();
        return new byte[0];
    } 

}



}
