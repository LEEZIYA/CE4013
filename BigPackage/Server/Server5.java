package BigPackage.Server;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Date;
import java.net.InetAddress;
import java.util.Scanner;
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
    Subs Slist[];
    int Slistcnt;

	private DatagramSocket socket;
 
	public Server5(int port) throws SocketException { // USAGE : Constructed once only per server run. Need to provide user port.
       try{ port = 40500;
        System.out.println("The default port of "+port+" is being used. Please change coding and send required port through constructor if needed.");
        Scanner sc = new Scanner(System.in);
        socket = new DatagramSocket(port);
        map = new int[1024][2];
        xx=-1;
        Slist = new Subs[50];
        Slistcnt =  -1;
        System.out.println("Enter 1 for At Most Once Semantics else any other integer for At Least once: ");
        int x = sc.nextInt();
        if(x==1)
            {
                ATMOSTFLAG = true;System.out.println("At Most Once Mode Activated.");
            }
        else
            {
                ATMOSTFLAG = false; System.out.println("At least once Mode Activated.");
            }

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

	private byte[] serviceReceive() throws IOException {//Extension called method for the waiting function by server.

        int retflg = 0;

        while (true) {

            System.out.println("Awaiting user input...");

            byte[] buffermax = new byte[512];
            DatagramPacket request = new DatagramPacket(buffermax, buffermax.length);

            while(true)
            {
                socket.receive(request);
                if(Math.random()<thres)
                {   System.out.println("Simulated Fault.");
                    break;
            }}

            byte[] USMS = Arrays.copyOfRange(buffermax,0,4);
            byte[] MID = Arrays.copyOfRange(buffermax,4,8);
            buffermax = Arrays.copyOfRange(buffermax,8,buffermax.length);

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
                        System.out.println("New Message Sent.");
                    }

            INA = request.getAddress();
            CP = request.getPort();

            return buffermax;

        }
    }

    public void serverMsgSend(byte[] a) throws IOException{ //USAGE : Use this to reply to the client. Automatically handles sending message to last receiver.
 
        oldmsg = a;
        DatagramPacket response = new DatagramPacket(a, a.length, INA, CP);
        socket.send(response);
            
    }

    public void serverMsgSendParam(byte[] a, InetAddress b, int c){ //Used by sending function - broadcastList.
 
        try{
        DatagramPacket response = new DatagramPacket(a, a.length, b, c);
        socket.send(response);
        }
        catch(Exception e)
        {
            System.out.println("Exception occurs at serverMsgSendParam");
        }
    }

    public void addList(long millitime){
        Slistcnt++;
        Slist[Slistcnt]= new Subs(INA,CP,millitime);
        System.out.println("Added new Sub:");
        System.out.println("IP: "+INA);
        System.out.println("Client Port: "+CP);
        System.out.println("StartTime: "+Slist[Slistcnt].startTime);
        System.out.println("IntervalTime: "+Slist[Slistcnt].intervalTime);
        System.out.println("EndTime: "+Slist[Slistcnt].endTime);
    }

    public void broadcastList(byte[] bmsg){

        boolean cnttrk = false;
        int cnttrkno = 0;

        Date date = new Date();

        System.out.println("The number of broadcast subscribers before monitor interval checking is: "+(Slistcnt+1));

        if(Slistcnt>-1)
        {
            Subs[] Nlist = new Subs[50];
            int Nlistcnt = -1;

            for(int i = 0; i<= Slistcnt;i++)
            {
                if(Slist[i].endTime>=date.getTime())
                {
                    cnttrk = true;
                    cnttrkno++;
                    serverMsgSendParam(bmsg, Slist[i].INA, Slist[i].port);
                    ++Nlistcnt;
                    Nlist[Nlistcnt]=Slist[i];
                }
            }
            Slist=Nlist;
            Slistcnt=Nlistcnt;
        if(!cnttrk)
        {
            System.out.println("After refreshing monitor intervals, no active subscribers were found. No updates were broadcasted.");
        }
        else
        {
            System.out.println("Number of subscribers who were messaged: "+cnttrkno);
        }
        }
        else{
           System.out.println("Broadcast list is empty.");
        }
    }
}



