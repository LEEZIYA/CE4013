package BigPackage.Server;

import java.util.Date;
import java.net.InetAddress;

public class Subs{

public InetAddress INA;
public int port;
public long startTime;
public long intervalTime;
public long endTime;

public Subs(InetAddress A, int B, long C){
    Date date = new Date();
    INA = A;
    port = B;
    startTime = date.getTime();
    intervalTime = C;
    endTime = startTime+intervalTime;
}
}
