package BigPackage.Server;

import java.util.ArrayList;
import java.util.Random;

import BigPackage.CurrencyType;
import BigPackage.Client.AccountInfo;

public class CoreService {

    ArrayList<AccountInfo> accounts;
    ArrayList<SubscribedClient> subscribedClients;

    CoreService(){
        accounts = new ArrayList<AccountInfo>();
    }

    public int openAccount(String name, char[] password, CurrencyType cType, float initialBalance) {
        int accountId = new Random().nextInt();
        AccountInfo newAcc = new AccountInfo(name, password, cType, initialBalance, accountId);
        accounts.add(newAcc);
        return accountId;
    }

    public boolean closeAccount(String name, char[] password, CurrencyType cType) {
        int indexToRemove = -1;
        for (int i = 0; i < accounts.size(); i++) {
            AccountInfo acc = accounts.get(i);
            if (acc.getName() == name && acc.getPassword() == password && acc.getcType() == cType){
                indexToRemove = i;
                break;
            }
        }
        if (indexToRemove == -1){
            return false;
        }
        accounts.remove(indexToRemove);
        return true;
    }

    public float changeBalance(String name, char[] password, CurrencyType cType, float change) {
        for (int i = 0; i < accounts.size(); i++) {
            AccountInfo acc = accounts.get(i);
            if (acc.getName() == name && acc.getPassword() == password && acc.getcType() == cType){
                float newBalance = acc.getCurrentBalance() + change;
                acc.setCurrentBalance(newBalance);
                return newBalance;
            }
        }
        return -1;
    }

    public ArrayList<SubscribedClient> getClientsToNotify(){
        for (int i = 0; i < subscribedClients.size(); i++){
            // remove expired subscription
        }
        return subscribedClients;
    }

    public void subscribeForUpdate(String ipAddr, int portNum, float monitorInterval) {
        SubscribedClient newSub = new SubscribedClient();
        subscribedClients.add(newSub);
    }

}
