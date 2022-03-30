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
        int accountNum;
        while (true) {
            accountNum = 10000 + Math.abs(new Random().nextInt(90000));

            // make sure accountNum is unique
            boolean sameAccountNumExisted = false;
            for (int i = 0; i < accounts.size(); i++){
                if (accountNum == accounts.get(i).getAccountNum()){
                    sameAccountNumExisted = true;
                }
            }
            if (!sameAccountNumExisted){
                break;
            }
        } 
        
        AccountInfo newAcc = new AccountInfo(name, password, cType, initialBalance, accountNum);
        accounts.add(newAcc);
        return accountNum;
    }

    private int getAccountIndex(int accountNum) throws RequestException {
        int retrievedAccountIndex = -1;
        for (int i = 0; i < accounts.size(); i++) {
            AccountInfo acc = accounts.get(i);
            if (acc.getAccountNum() == accountNum){
                retrievedAccountIndex = i;
                break;
            }
        }
    
        if (retrievedAccountIndex == -1){
            throw new RequestException("Account not found");
        }
        return retrievedAccountIndex;
    }

    private int getValidatedAccountIndex(int accountNum, String name, char[] password, CurrencyType cType) throws RequestException {
        int retrievedAccountIndex = getAccountIndex(accountNum);
        AccountInfo retrievedAccount = accounts.get(retrievedAccountIndex);
        if (!retrievedAccount.getName().equals(name)
            || !Arrays.equals(retrievedAccount.getPassword(), password)
            || retrievedAccount.getcType() != cType){
            throw new RequestException("Invalid credentials");
        }
        return retrievedAccountIndex;
    }

    public void closeAccount(int accountNum, String name, char[] password, CurrencyType cType) throws RequestException {
        int accIndex = getValidatedAccountIndex(accountNum, name, password, cType);
        accounts.remove(accIndex);
    }

    public float changeBalance(int accountNum, String name, char[] password, CurrencyType cType, float change) throws RequestException {
        int accIndex = getValidatedAccountIndex(accountNum, name, password, cType);
        AccountInfo acc = accounts.get(accIndex);

        float newBalance = acc.getCurrentBalance() + change;
        if (newBalance < 0){
            throw new RequestException("Insufficient balance");
        }

        acc.setCurrentBalance(newBalance);

        return newBalance;
    }

    public float getAccountBalance(int accountNum, String name, char[] password, CurrencyType cType) throws RequestException {
        int accIndex = getValidatedAccountIndex(accountNum, name, password, cType);
        return accounts.get(accIndex).getCurrentBalance();
    }

    public float transferFund(int accountNum, String name, char[] password, CurrencyType cType, int destAccountNum, float amount)throws RequestException {
        int destAccIndex;
        try{
            destAccIndex = getAccountIndex(destAccountNum);
        } catch (RequestException e) {
            throw new RequestException("Destination account not found");
        }
        AccountInfo destAcc = accounts.get(destAccIndex);

        float newBalance = changeBalance(accountNum, name, password, cType, -amount);
        float destAccCTypeAmount = amount * currencyRate.get(cType) / currencyRate.get(destAcc.getcType());
        float newDestBalance = destAcc.getCurrentBalance() + destAccCTypeAmount;
        destAcc.setCurrentBalance(newDestBalance);

        return newBalance;
    }

}
