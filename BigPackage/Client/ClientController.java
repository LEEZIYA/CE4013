package BigPackage.Client;

public class ClientController {
	private ClientInterface clientUI;
	private ClientService clientService;
//	public ClientController() {
//		clientUI = new ClientInterface();
//	} 
	public ClientController(ClientInterface clientUI,ClientService clientService) {
		this.clientUI = clientUI;
		this.clientService = clientService;
		
	}
	public void start() {
		//ClientInterface clientUI = new ClientInterface();
		boolean keepRunning = true;
		int result;
		while(keepRunning) {
			result = this.clientUI.startMenu();
			switch(result) {
				case 1: //create account
					this.createAccount();
					break;
				case 2: //close account
					
					break;
				case 3: //deposit money
					
					break;
				case 4: //withdraw money
					
					break;
				case 5://monitor update at server
					
					break;
				case 6://get account info
					
					break;
				case 7://transfer funds
					
					break;
				case -1 :
					keepRunning = false;
					break;
				default:
					break;
												
			}
		}
		
		
	}
	public void createAccount() {
		AccountInfo newAccount = this.clientUI.createAccount();
		Response response = this.clientService.openAccount(newAccount);
		this.clientUI.accountCreationResult(response);
		return;
	}
	public void verifyPassword(AccountInfo accountInfo) {
		
	}
}
