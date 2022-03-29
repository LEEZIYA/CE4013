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
					this.closeAccount();
					break;
				case 3: //withdraw money
					this.withdrawMoney();
					break;
				case 4: //deposit money
					this.depositMoney();
					break;
				case 5://monitor update at server
					this.monitorUpdate();
					break;
				case 6://get account info
					this.getAccountBalance();
					break;
				case 7://transfer funds
					this.transferFund();
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
	public void closeAccount(){
		AccountInfo closedAccount = this.clientUI.closeAccount();
		Response response = this.clientService.closeAccount(closedAccount);
		this.clientUI.accountClosingResult(response);
		return;
	}
	public void depositMoney(){
		AccountInfo depositAccount = this.clientUI.depositMoney();
		Response response = this.clientService.depositMoney(depositAccount);
		this.clientUI.moneyDepositResult(response);
		return;
	}
	public void withdrawMoney(){
		AccountInfo withdrawAccount = this.clientUI.withdrawMoney();
		Response response = this.clientService.withdrawMoney(withdrawAccount);
		this.clientUI.moneyDepositResult(response);
		return;
	}
	public void monitorUpdate(){
		int monitorInterval;
		monitorInterval = this.clientUI.monitorUpdate();
		this.clientService.subscribeForUpdate(monitorInterval);// Minutes.
		//need to make this non-blocking?
		while(true){
			//blocking wait for update
			Response response = this.clientService.getUpdate();
			
			if(!response.isSuccess()){ // check whether interval is finished
				System.out.println("Monitoring stopped");
				break; //informed by server that monitor interval over, stop monitoring
			}
			else{
				this.clientUI.monitoringResult(response); //print out response
			}
			//else continue looping and monitoring
		}
		return;

	}

	public void getAccountBalance(){
		AccountInfo requestedAccount = this.clientUI.getAccountBalance();
		Response response = this.clientService.getAccountBalance(requestedAccount);
		this.clientUI.accountBalanceResult(response);
		return;
	}

	public void transferFund(){
		AccountInfo accountInfo = this.clientUI.transferFund();
		Response response = this.clientService.transferFund(accountInfo);
		this.clientUI.fundTransferResult(response);
		return;
	}


}
