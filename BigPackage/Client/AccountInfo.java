package BigPackage.Client;

import BigPackage.CurrencyType;

public class AccountInfo {
	private String name;
	private char[] password; 
	private CurrencyType cType; 
	private float initialBalance;
	
	public String getName() {
		return this.name;
	}
	public char[] getPassword() {
		return this.password;
	}
	public CurrencyType getCType() {
		return this.cType;
	}
	public float getInitialBalance() {
		return this.initialBalance;
	}
	
	public setName(String name) {
		return this.name = name;
	}
	public getPassword(String name) {
		return this.password = name;
	}
	public getCType() {
		return this.cType;
	}
	
}
