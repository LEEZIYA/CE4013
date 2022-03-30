package BigPackage.Server;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Date;
import java.net.InetAddress;
import javax.lang.model.util.ElementScanner14;

import java.math.*;

import BigPackage.BufferPointer;
import BigPackage.MarshUtil;

public class Server5{

    boolean ATMOSTFLAG = true; //USAGE: 1 for At Most Semantics. 0 for At Least Semantics.

    double thres = 0.8; //USAGE : Setting success percentage.
    int map[][];
    int xx;
    InetAddress INA;
    int CP;
    byte[] oldmsg;
    Subs[] Slist;
    int Slistcnt;

	private DatagramSocket socket;
 
	public Server5(int port) throws SocketException { // USAGE : Constructed once only per server run!

       try{ port = 40500;
        socket = new DatagramSocket(port);
        map = new int[1024][2];
        xx=-1;
        Slist = new Subs[20];
        Slistcnt =  -1;
        InetAddress tp = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + tp.getHostAddress());
        System.out.println("Host Name:- " + tp.getHostName());}
        catch(Exception e)
        {
            System.out.println("Client Constructor Fault.");
        }
    }

	public byte[] serverMsgWait() { //USAGE : Used to receive a byte buffer and takes care of duplicates.

        try {
            return serviceReceive();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
        return null;
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
                if(Math.random()<thres)
                {   System.out.println("Simulated Fault.");
                    break;
            }}

            System.out.println("Request received.");

            //Demystifying MSG for at Most Once Semantics.

           // byte[] UID = Arrays.copyOfRange(buffermax,0,4);

           System.out.println("Numbered Bytes: "+Arrays.toString(buffermax));

            byte[] USMS = Arrays.copyOfRange(buffermax,0,4);
            byte[] MID = Arrays.copyOfRange(buffermax,4,8);
            buffermax = Arrays.copyOfRange(buffermax,8,buffermax.length);

            System.out.println("Non-Numbered Bytes: "+Arrays.toString(buffermax));

            int UidID = MarshUtil.unmarshInt(USMS, new BufferPointer());
            int MsgID = MarshUtil.unmarshInt(MID, new BufferPointer());


            if(ATMOSTFLAG=true){

                        for(int i = 0; i<=xx;i++)
                        {
                            if((UidID==map[i][0])&&(MsgID==map[i][1]))
                                retflg = 1;
                        }
                        if (retflg==1){
                            retflg = 0;
                            serverMsgSend(oldmsg);
                            continue;
                        }
            
                        xx++;
                        map[xx][0] = UidID;
                        map[xx][1] = MsgID;
                        System.out.println("New Message Created.");
                    }

            INA = request.getAddress();
            CP = request.getPort();

            System.out.println(INA);
            System.out.println(CP);

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

    public void serverMsgSend(byte[] a) throws IOException{ //USAGE : Use this to reply to the client. Automatically handles sending message to last receiver.
 
        oldmsg = a;
        DatagramPacket response = new DatagramPacket(a, a.length, INA, CP);
        socket.send(response);
        System.out.println("HERE IS THE FINAL SENDING:");
        System.out.println(Arrays.toString(a));
        System.out.println("Answer sent. \n");
            
    }

    public void serverMsgSendParam(byte[] a, InetAddress b, int c){ //Used by sending function - broadcastList.
 
        try{
        DatagramPacket response = new DatagramPacket(a, a.length, b, c);
        socket.send(response);
        System.out.println("HERE IS THE SUB FINAL SENDING:");
        System.out.println(Arrays.toString(a));
        System.out.println("Answer sent. \n");}
        catch(Exception e)
        {
            System.out.println("Exception occurs at serverMsgSendParam");
        }

            
    }

    public void addList(long millitime){
        Slistcnt++;
        Slist[Slistcnt]= new Subs(INA,CP,millitime);
        System.out.println("Added new SUB.");
        System.out.println("StartTime: "+Slist[Slistcnt].startTime);
        System.out.println("IntervalTime: "+Slist[Slistcnt].intervalTime);
        System.out.println("EndTime: "+Slist[Slistcnt].endTime);
    }

    public void broadcastList(byte[] bmsg){

        Date date = new Date();

        if(Slistcnt>-1)
        {

        int[] arem = new int[Slistcnt+1];
        int aremcnt = -1;

        for(int i = 0; i<= Slistcnt;i++)
        {
            if(Slist[i].endTime>=date.getTime())
            {
                serverMsgSendParam(bmsg, Slist[i].INA, Slist[i].port);
            }
            else
            {
                aremcnt++;
                arem[aremcnt]=i;
            }
        }

        //Dealing with removal.

        int finalcnt = Slistcnt-aremcnt-1;
        Subs[] nlist = new Subs[finalcnt+1];
        int nlistcnt = -1;
        int flg = 0;

        for (int i = 0;i<=Slistcnt;i++)
        {
            for (int j = 0; j<=aremcnt;j++)
            {
                if(arem[j]==i)
                {
                    flg = 1;
                    break;
                }
            }
            
            if(flg == 1)
            {
                flg = 0;
                break;
            }
            
            nlistcnt++;
            nlist[nlistcnt] = Slist[i];
        }
        Slist = nlist;
        Slistcnt = nlistcnt;

        }
        else{
           System.out.println("Broadcast list is empty.");
        }
    }

}



