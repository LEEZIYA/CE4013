package Server;

public class Servant {

    public int openAccount(String name, char[] password, CurrencyType cType, float initialBalance) throws NetworkException {
        return 0;
    }

    public boolean closeAccount(String name, char[] password, CurrencyType cType) throws NetworkException, RequestException {
        return false;
    }

    public float changeBalance(String name, char[] password, CurrencyType cType, float change) throws NetworkException, RequestException {
        return 0;
    }

    public void subscribeForUpdate(float monitorInterval) throws NetworkException {
        
    }

}
