package BigPackage.Client;

import BigPackage.CurrencyType;

public class AccountInfo {
	private String name;
	private String accountNum;
	private char[] password; 
	private CurrencyType cType; 
	private float initialBalance;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	public CurrencyType getcType() {
		return cType;
	}
	public void setcType(CurrencyType cType) {
		this.cType = cType;
	}
	public float getInitialBalance() {
		return initialBalance;
	}
	public void setInitialBalance(float initialBalance) {
		this.initialBalance = initialBalance;
	}
	
	public AccountInfo() {
		initialBalance = 0;
	}
	

	
	
	
}
