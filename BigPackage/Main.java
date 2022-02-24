package BigPackage;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.SocketException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
       /* int port = 50001;
        BigPackage.Server.UdpUnicastServer server = new BigPackage.Server.UdpUnicastServer(port);
        BigPackage.Client.UdpUnicastClient client = new BigPackage.Client.UdpUnicastClient(port);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(client);
        executorService.submit(server);*/
        String s = "localhost";
        int port = 17;
        try {
            BigPackage.Server.Server2 server = new BigPackage.Server.Server2(port);
        } catch (SocketException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            BigPackage.Client.Client2 client = new BigPackage.Client.Client2(port);
        } catch (SocketException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}