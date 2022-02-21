package BigPackage;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        int port = 50001;
        BigPackage.Server.UdpUnicastServer server = new BigPackage.Server.UdpUnicastServer(port);
        BigPackage.Client.UdpUnicastClient client = new BigPackage.Client.UdpUnicastClient(port);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(client);
        executorService.submit(server);
    }
}