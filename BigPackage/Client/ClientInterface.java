package BigPackage.Client;

import BigPackage.CurrencyType;
import java.util.Scanner;

public class ClientInterface {

	public int getPort(){
		System.out.println("Please enter the port you want to use:");
		Scanner sc = new Scanner(System.in);
		int port = sc.nextInt();
		sc.nextLine();
		return port;
	}
	public String getAddress(){
		System.out.println("Please enter the IP you want to connect to:");
		Scanner sc = new Scanner(System.in);
		String address = sc.nextLine();
		return address;
	}
	public int getClientID(){
		System.out.println("Please enter the client ID you want to connect to:");
		Scanner sc = new Scanner(System.in);
		int clientID = sc.nextInt();
		sc.nextLine();
		return clientID;
	}

	public int startMenu() {
		System.out.println("Thank you for using the DSB client");
		System.out.println("Below is the list of services available:");
		
		System.out.println("1.Open New Account");
		System.out.println("2.Close Existing Account");
		System.out.println("3.Withdraw Money");
		System.out.println("4.Deposit Money");
		System.out.println("5.Monitor Update at Server");
		System.out.println("6.Check Account Balance");
		System.out.println("7.Transfer Funds");
		System.out.println("8.Exit programme");
		
		System.out.println("Please input an integer range 1 to 7");
		Scanner sc = new Scanner(System.in);
		int response = sc.nextInt();
		while(true) {
			if(0<response || response<9) {
				break;
			}
			else {
				System.out.println("Please input an integer range 1 to 7");
				response = sc.nextInt();
			}
		}
		if(response==8) {
			return -1;
		}
		else {
			return response;
		}
		

	}
	private void verifyPassword(AccountInfo accountInfo) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Please Enter Your Username:");
		String name = sc.nextLine();
		System.out.println("Please Enter Your Account Number:");
		int accountNum = sc.nextInt();
		sc.nextLine();
		System.out.println("Please Enter Your Password: ");
		String passwordStr = sc.nextLine();
		char[] password = passwordStr.toCharArray();

		accountInfo.setName(name);
		accountInfo.setAccountNum(accountNum);
		accountInfo.setPassword(password);
		return;
		
	}

	private void getCurrencyType(AccountInfo accountInfo){
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose your currency type:");
		int count=0;
		for (CurrencyType currency : CurrencyType.values()){
			count++;
			System.out.println(count+". "+currency);
			
		}
		System.out.println("Input the corresponding number:");
		int currencyInput = sc.nextInt();
		while(true) {
			if (currencyInput<1 || currencyInput>CurrencyType.values().length) {
				System.out.println("please input number between 1 and "+CurrencyType.values().length);
				currencyInput = sc.nextInt();
			}
			else {
				break;
			}
		}
		CurrencyType currency = CurrencyType.values()[currencyInput-1];
		accountInfo.setcType(currency);
	}

	//1. create account
	public AccountInfo createAccount() {
		
		Scanner sc = new Scanner(System.in);
		AccountInfo accountInfo = new AccountInfo();
		
		System.out.println("Welcome to account creation");
			
		System.out.println("Input name:");
		String name = sc.nextLine();
		accountInfo.setName(name);
		
		System.out.println("Input password (8 characters):");
		String passwordInput = sc.nextLine();
		char[] password = passwordInput.toCharArray();
		while(true) {
			if (password.length !=8 ) {
				System.out.println("Password not of specified length, please input password of 8-character length:");
				passwordInput = sc.nextLine();
				password = passwordInput.toCharArray();
			}
			else {
				break;
			}
		}
		accountInfo.setPassword(password);
		
		this.getCurrencyType(accountInfo);
		
		System.out.println("Input initial balance:");
		float initialBalance = sc.nextFloat();
		accountInfo.setInitialBalance(initialBalance);
				
		return accountInfo;
	}
	
	public void accountCreationResult(Response response) {
//		System.out.println("Account has been created successfully!");
//		System.out.println("Your account number is "+accountNum);
		boolean success = response.isSuccess();
		if(success) {
			System.out.println("Account created Succesfully!");
			System.out.println("Account Number is "+response.getAccountInfo().getAccountNum());
		}
		else {
			System.out.println("Account creation failed!");
			System.out.println("Error: "+response.getMessage());
		}
		
	}
	
	//2. close existing account
	public AccountInfo closeAccount() {
		Scanner sc = new Scanner(System.in);
		AccountInfo accountInfo = new AccountInfo();
		System.out.println("Welcome to account closing");	
		this.verifyPassword(accountInfo);
		this.getCurrencyType(accountInfo);

		return accountInfo;
	}

	public void accountClosingResult(Response response) {
		boolean success = response.isSuccess();
		if(success) {
			System.out.println("Account closed successfully!");
			// System.out.println(response.getMessage());
		}
		else {
			System.out.println("Account closing failed!");
			System.out.println("Error: "+response.getMessage());
		}
	}

	//3. withdraw money
	public AccountInfo withdrawMoney() {
		Scanner sc = new Scanner(System.in);
		AccountInfo accountInfo = new AccountInfo();
		System.out.println("Welcome to money withdrawal");	
		this.verifyPassword(accountInfo);

		this.getCurrencyType(accountInfo);

		System.out.println("Please enter the withdrawal amount");
		float change = sc.nextFloat();
		accountInfo.setChange(change);
		return accountInfo;
	}

	public void moneyWithdrawalResult(Response response) {
		boolean success = response.isSuccess();
		if(success) {
			System.out.println("Money withdrawed successfully!");
			System.out.println("Current balance is "+response.getAccountInfo().getCurrentBalance());
		}
		else {
			System.out.println("Money withdrawl failed!");
			System.out.println("Error: "+response.getMessage());
		}
	}

	//4. deposit money
	public AccountInfo depositMoney() {
		Scanner sc = new Scanner(System.in);
		AccountInfo accountInfo = new AccountInfo();
		System.out.println("Welcome to money deposit");	
		this.verifyPassword(accountInfo);
		this.getCurrencyType(accountInfo);
		System.out.println("Please enter the deposit amount");
		float change = sc.nextFloat();
		accountInfo.setChange(change);
		return accountInfo;
	}

	public void moneyDepositResult(Response response) {
		boolean success = response.isSuccess();
		if(success) {
			System.out.println("Money deposited successfully!");
			System.out.println("Current balance is "+response.getAccountInfo().getCurrentBalance());
		}
		else {
			System.out.println("Money deposit failed!");
			System.out.println("Error: "+response.getMessage());
		}
	}

	//5. monitor update
	public int monitorUpdate(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to update monitoring");
		int interval;
		while(true){
			System.out.println("Please enter your monitoring interval in minutes:");
			interval = sc.nextInt();
			if(interval>0){
				break;
			}else{
				System.out.println("Error: Please enter a positive integer.");
			}
		}
		return interval;
	}

	public void monitoringResult(Response response){
		System.out.println(response.getMessage());
		//do we just compose message at server side, or do we need proceesing of accountInfo field?
	}

	//6. get account balance
	public AccountInfo getAccountBalance(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to account balance checking");
		AccountInfo accountInfo = new AccountInfo();
		this.verifyPassword(accountInfo);
		this.getCurrencyType(accountInfo);

		return accountInfo;
	}

	public void accountBalanceResult(Response response){
		boolean success = response.isSuccess();
		if(success) {
			System.out.println("Balance checking successfully!");
			System.out.println("Current balance is "+response.getAccountInfo().getCurrentBalance());
		}
		else {
			System.out.println("Balance checking failed!");
			System.out.println("Error: "+response.getMessage());
		}
	}

	//7. transfer fund
	public AccountInfo transferFund(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to fund transfer");
		AccountInfo accountInfo = new AccountInfo();
		this.verifyPassword(accountInfo);
		this.getCurrencyType(accountInfo);
		System.out.println("Please enter your destination account number");
		int destAccount = sc.nextInt();
		accountInfo.setDestAccount(destAccount);
		System.out.println("Please enter your transfer amount");
		float amount = sc.nextFloat();
		accountInfo.setChange(amount);

		return accountInfo;
	}

	public void fundTransferResult(Response response){
		boolean success = response.isSuccess();
		if(success) {
			System.out.println("Fund transfer successfully!");
			System.out.println("Current balance is "+response.getAccountInfo().getCurrentBalance());
		}
		else {
			System.out.println("Fund transfer failed!");
			System.out.println("Error: "+response.getMessage());
		}
	}

	
	
}
