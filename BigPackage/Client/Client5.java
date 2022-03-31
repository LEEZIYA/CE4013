package BigPackage.Client;
import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Scanner;

public class Client5{

    public DatagramSocket socket;
    public byte[] bufferino;
    public int msgcnt;
    public int usms;
    public byte[] buffstore;
    public String servipstring;
    public double thres;
    public int rlim;
 
    public Client5(int portSRC, String xyz, int sesd) throws SocketException { // USAGE : Initialize and construct Client Socket. Provide port, IP and session ID during construction.
        try{
            Scanner sc = new Scanner(System.in);
            socket = new DatagramSocket(portSRC);
            msgcnt = 0;
            usms = sesd;
            rlim = 20;
            servipstring=xyz;
            String ip = "";
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;
    
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                }
            }
            System.out.println("The client IP (your computer's IP) is: "+ip);

            System.out.println("Please enter the success rate of receiving communications (To be edited for fault tolerance measures). Choose a value BETWEEN 0.00 (0% Success) and 1.00 (100% success): ");
            thres = sc.nextDouble();
        }
        catch(Exception e)
        {
            System.out.println("Client Constructor Fault.");
        }

    }

    public byte[] sendMSG(byte[] b){ //USAGE : Used to send message of byte reference. Returns received message. Handles all faults. Returns 0 if fails.
        buffstore=new byte[512];
        bufferino = b;
        int rsndcnt = 1;
        int rsndflg = 0;
        msgcnt++;

        while(rsndcnt<=rlim)
        {
            int port = 40500;
            try{
                InetAddress address = InetAddress.getByName(servipstring);
                socket.setSoTimeout(5000);

                    ByteArrayOutputStream out = new ByteArrayOutputStream(); //Formatting message controlling.
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

                    DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
                    socket.send(request);

                    if(rsndcnt == 0){
                    System.out.println("Request sent. Request ID for given user: "+msgcnt);}

                    byte[] buffer = new byte[512];
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length);

                    while(true)
                    {
                        socket.receive(response);
                        if(Math.random()<thres){
                            break;}
                        else
                            System.out.println("Simulated fault.");
                    }

                    buffstore = buffer;

        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error " +rsndcnt);
            rsndflg=1;
            rsndcnt++;  
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
    } 

    if(rsndflg==1)
    {
        rsndflg=0;
        continue;
    }

    break;
    }
    if (rsndcnt == rlim){
        System.out.println("Communication failed after "+rsndcnt+" attempts of receiving. Stop.");
        return new byte[0];}
    else
        return buffstore;
}

public void startMonitor(byte[] b){ //USAGE : Used to send message of byte reference. Returns received message. Handles all faults. Returns 0 if fail.

    buffstore=new byte[512];
    bufferino = b;
    int rsndcnt = 1;
    int rsndflg = 0;
    msgcnt++;

    while(rsndcnt<rlim)
    {
    
    int port = 40500; //USAGE : Server Port.

    try{
        InetAddress address = InetAddress.getByName(servipstring);
        socket.setSoTimeout(5000);

        System.out.println("Connection protocol initialized.");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
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

        DatagramPacket request = new DatagramPacket(buffermax,buffermax.length,address,port);
        socket.send(request);

        System.out.println("Request sent. Request ID for given user: "+msgcnt);

        byte[] buffer = new byte[512];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);

        while(true)
        {
            socket.receive(response);
            if(Math.random()<thres)
                break;
            else
                System.out.println("Simulated Fault.");
        }

        buffstore = buffer;

    } catch (SocketTimeoutException ex) {
        System.out.println("Timeout error " +rsndcnt);
        rsndflg=1;
        rsndcnt++;  
    } catch (IOException ex) {
        System.out.println("Client error: " + ex.getMessage());
        ex.printStackTrace();
    } 
    if(rsndflg==1)
    {
        rsndflg=0;
        continue;
    }

    break;
    }
    if (rsndcnt == rlim){
        System.out.println("Subscription failed after "+rlim+" attempts of receiving. Stop.");}
    else
        {System.out.println("Subscription Successful");}
    }

public byte[] listenMonitor(int milli)//Listen for subscription calls for the given millisecond amount.
    {
    try{
        socket.setSoTimeout(milli);
        byte[] buffer = new byte[512];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);

        socket.receive(response);

        return buffer;}
        catch (SocketTimeoutException ex) {
            System.out.println("Subscription Elapsed.");
            byte[] b = new byte[2];
            b[0]= (byte)0;
            b[1]= (byte)0;
            return b;
        } 
        catch(SocketException SEMILLI)
        {
            System.out.println("Subscription Elapsed.");
            byte[] b = new byte[2];
            b[0]= (byte)0;
            b[1]= (byte)0;
            return b;
        }
        catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
            byte[] b = new byte[2];
            b[0]= (byte)0;
            b[1]= (byte)0;
            return b;
        } 

    }

}
