package BigPackage.Client;

import BigPackage.CurrencyType;
import java.util.Scanner;

public class ClientInterface {
	public int startMenu() {
		System.out.println("Thank you for using the DSB client");
		System.out.println("Below is the list of services available:");
		
		System.out.println("1.Open New Account");
		System.out.println("2.Close Existing Account");
		System.out.println("3.Deposit Money");
		System.out.println("4.Withdraw Money");
		System.out.println("5.Monitor Update at Server");
		System.out.println("6.Custom Operation I");
		System.out.println("7.Custom Operation II");
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
//	private int verifyPassword() {
//		System.out.println("Please enter your Account Number");
//		
//	}
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
		CurrencyType currency = CurrencyType.values()[currencyInput];
		accountInfo.setcType(currency);
		
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
			System.out.println(response.getMessage());
			System.out.println("Account Number is "+response.getAccountInfo().getAccountNum());
		}
		else {
			System.out.println(response.getMessage());
		}
		
	}
	
//	public void accountCreationError(String errorMessage) {
//		System.out.println("Error: the account creation was not successful");
//		System.out.println(errorMessage);
//	}
	
	//2. close existing account
	public AccountInfo closeAccount() {
		Scanner sc = new Scanner(System.in);
		AccountInfo accountInfo = new AccountInfo();
		
		System.out.println("Welcome to account closing");
		
		System.out.println("Input name:");
		String name = sc.nextLine();
		accountInfo.setName(name);
		
		
		
		
		return accountInfo;
	}
	
	
}
