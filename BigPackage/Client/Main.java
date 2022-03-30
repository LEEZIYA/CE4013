package BigPackage.Client;

public class Main {
	public static void main(String[] args) {
		ClientInterface clientUI = new ClientInterface();
		String ipAddress = clientUI.getAddress();
		int port = clientUI.getPort();
		ClientService clientService = new ClientService(port,ipAddress);
		ClientController control = new ClientController(clientUI,clientService);
		
		control.start();
	}

}
