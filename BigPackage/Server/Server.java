package BigPackage.Server;

import BigPackage.CurrencyType;
import BigPackage.MarshUtil;
import BigPackage.NetworkException;
import BigPackage.RequestException;

public class Server {

    Servant servant;
    boolean IS_LITTLE_ENDIAN = true;    // TBD: endian-ness

    public byte[] openAccount(byte[] requestData) throws NetworkException {     // TBD: exception types, exception content
        CurrencyType cType = MarshUtil.unmarshCType(requestData, 0);
        float initialBalance = MarshUtil.unmarshFloat(requestData, IS_LITTLE_ENDIAN, 1);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, 5);        // TBD: length of password
        String name = MarshUtil.unmarshString(requestData, IS_LITTLE_ENDIAN, 13);

        int returnValue = servant.openAccount(name, password, cType, initialBalance);
        return MarshUtil.marshInt(returnValue, IS_LITTLE_ENDIAN);
    }

    public byte[] closeAccount(byte[] requestData) throws NetworkException, RequestException {
        CurrencyType cType = MarshUtil.unmarshCType(requestData, 0);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, 5);
        String name = MarshUtil.unmarshString(requestData, IS_LITTLE_ENDIAN, 13);

        boolean returnValue = servant.closeAccount(name, password, cType);
        // temporarilty put short first. TBD the return type: boolean / short / enum
        if (returnValue){
            return MarshUtil.marshShort((short)1, IS_LITTLE_ENDIAN);
        } else {
            return MarshUtil.marshShort((short)0, IS_LITTLE_ENDIAN);
        }
    }

    public byte[] changeBalance(byte[] requestData) throws NetworkException, RequestException {
        CurrencyType cType = MarshUtil.unmarshCType(requestData, 0);
        float change = MarshUtil.unmarshFloat(requestData, IS_LITTLE_ENDIAN, 1);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, 5);
        String name = MarshUtil.unmarshString(requestData, IS_LITTLE_ENDIAN, 13);

        float returnValue = servant.changeBalance(name, password, cType, change);
        return MarshUtil.marshFloat(returnValue, IS_LITTLE_ENDIAN);
    }

    public void subscribeForUpdate(byte[] requestData) throws NetworkException {
        float monitorInterval = MarshUtil.unmarshFloat(requestData, IS_LITTLE_ENDIAN, 0);
        servant.subscribeForUpdate(monitorInterval);    // where to get the client information?
    }

    public static void main(String[] args){
        // unpack Request
        // run different function depending on the requestType
        // wrap return value in Response

        

    }

}
