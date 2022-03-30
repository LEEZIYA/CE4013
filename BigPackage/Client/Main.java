package BigPackage.Client;

public class Main {
	public static void main(String[] args) {
		ClientInterface clientUI = new ClientInterface();
		String ipAddress = clientUI.getAddress();
		int port = clientUI.getPort();
		int clientID = clientUI.getClientID();
		ClientService clientService = new ClientService(port,ipAddress,clientID);
		ClientController control = new ClientController(clientUI,clientService);
		
		control.start();
	}

}
