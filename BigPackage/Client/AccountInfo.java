package BigPackage.Client;

import BigPackage.CurrencyType;

public class AccountInfo {
	private String name;
	private int accountNum;
	private char[] password; 
	private CurrencyType cType; 
	private float initialBalance;
	private float currentBalance;
	private float change;
	private int destAccount;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(Integer accountNum) {
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
	public float getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(float currentBalance) {
		this.currentBalance = currentBalance;
	}
	public float getChange() {
		return change;
	}
	public void setChange(float change) {
		this.change = change;
	}
	public int getDestAccount() {
		return accountNum;
	}
	public void setDestAccount(Integer accountNum) {
		this.destAccount = accountNum;
	}
	
	public AccountInfo() {
		initialBalance = 0;
	}
	
	public AccountInfo(String name, char[] password, CurrencyType cType, float initialBalance, int accountNum){
		this.name = name;
		this.password = password;
		this.cType = cType;
		this.initialBalance = initialBalance;
		this.currentBalance = initialBalance;
		// this.accountNum = accountNum;	// number of string?
	}
	
}
