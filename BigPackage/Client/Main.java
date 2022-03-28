package BigPackage.Client;

public class Main {
	public static void main(String[] args) {
		ClientInterface clientUI = new ClientInterface();
		ClientService clientService = new ClientService();
		ClientController control = new ClientController(clientUI,clientService);
		
		control.start();
	}

}
