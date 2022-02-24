package BigPackage.Client;

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
		
		Scanner sc = new Scanner();
		int response = sc.nextInt();
		while(true) {
			if(0<response<8) {
				break
			}
			else {
				System.out.println("Please input an integer range 1 to 7");
				response = sc.nextInt();
			}
		}
		return response;

	}
	private int verifyPassword() {
		System.out.println("Please enter your Account Number");
		
	}
	public int createAccount() {
		System.out.println("Input Account number");
	}
}
