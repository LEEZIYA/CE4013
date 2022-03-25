package BigPackage.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import BigPackage.CurrencyType;
import BigPackage.Client.AccountInfo;

public class CoreService {

    ArrayList<AccountInfo> accounts;
    HashMap<CurrencyType, Float> currencyRate;

    CoreService(){
        accounts = new ArrayList<AccountInfo>();
        currencyRate = new HashMap<CurrencyType, Float>();
        currencyRate.put(CurrencyType.SGD, 1.0f);
        currencyRate.put(CurrencyType.USD, 1.36f);
        currencyRate.put(CurrencyType.EUR, 1.49f);
    }

    public int openAccount(String name, char[] password, CurrencyType cType, float initialBalance) {
        int accountNum = new Random().nextInt();
        AccountInfo newAcc = new AccountInfo(name, password, cType, initialBalance, accountNum);
        accounts.add(newAcc);
        return accountNum;
    }

    private AccountInfo getAccount(int accountNum) throws RequestException {
        AccountInfo retrievedAccount = null;
        for (int i = 0; i < accounts.size(); i++) {
            AccountInfo acc = accounts.get(i);
            if (acc.getAccountNum() == accountNum){
                retrievedAccount = acc;
            }
        }
    
        if (retrievedAccount == null){
            throw new RequestException("Account not found");
        }
        return retrievedAccount;
    }

    private AccountInfo getValidatedAccount(int accountNum, String name, char[] password, CurrencyType cType) throws RequestException {
        AccountInfo retrievedAccount = getAccount(accountNum);
        if (retrievedAccount.getName() != name 
            || !Arrays.equals(retrievedAccount.getPassword(), password)
            || retrievedAccount.getcType() != cType){
            throw new RequestException("Invalid credentials");
        }
        return retrievedAccount;
    }

    public void closeAccount(int accountNum, String name, char[] password, CurrencyType cType) throws RequestException {
        AccountInfo acc = getValidatedAccount(accountNum, name, password, cType);
        accounts.remove(acc);
    }

    public float changeBalance(int accountNum, String name, char[] password, CurrencyType cType, float change) throws RequestException {
        AccountInfo acc = getValidatedAccount(accountNum, name, password, cType);

        float newBalance = acc.getCurrentBalance() + change;
        if (newBalance < 0){
            throw new RequestException("Insufficient balance");
        }

        acc.setCurrentBalance(newBalance);
        return newBalance;
    }

    public float getAccountBalance(int accountNum, String name, char[] password, CurrencyType cType) throws RequestException {
        AccountInfo acc = getValidatedAccount(accountNum, name, password, cType);
        return acc.getCurrentBalance();
    }

    public float transferFund(int accountNum, String name, char[] password, CurrencyType cType, int destAccountNum, float amount)throws RequestException {
        AccountInfo destAcc;
        try{
            destAcc = getAccount(destAccountNum);
        } catch (RequestException e) {
            throw new RequestException("Destination account not found");
        }
        float newBalance = changeBalance(accountNum, name, password, cType, -amount);
        float destAccCTypeAmount = amount * currencyRate.get(cType) / currencyRate.get(destAcc.getcType());
        destAcc.setCurrentBalance(destAcc.getCurrentBalance() + destAccCTypeAmount);

        return newBalance;
    }

}
