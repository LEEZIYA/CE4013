package BigPackage.Server;

public class Main {
    public static void main (String[] args) throws Exception {
		ServerController control = new ServerController();
		
		control.start();	// or started by udp socket? 
	}
}
