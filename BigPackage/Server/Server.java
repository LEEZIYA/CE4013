package BigPackage.Server;

import BigPackage.BufferPointer;
import BigPackage.CurrencyType;
import BigPackage.MarshUtil;
import BigPackage.NetworkException;
import BigPackage.RequestException;

public class Server {

    static int MAX_REQUEST_BUF_SIZE = 1024;  // depending on how network socket works, might not use this
    static Servant servant = new Servant();

    public static void openAccount(byte[] requestData, BufferPointer readBufPt, byte[] responseBuf, BufferPointer writeBufPt) throws NetworkException {     // TBD: exception types, exception content
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float initialBalance = MarshUtil.unmarshFloat(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);        // TBD: length of password
        String name = MarshUtil.unmarshString(requestData, readBufPt);
        System.out.println("openAccount with currencyType: " + cType);
        System.out.println("openAccount with initialBalance: " + initialBalance);
        System.out.println("openAccount with password: " + new String(password));
        System.out.println("openAccount with name: " + name);

        int returnValue = servant.openAccount(name, password, cType, initialBalance);
        MarshUtil.marshInt(returnValue, responseBuf, writeBufPt);
    }

    public static void closeAccount(byte[] requestData, BufferPointer readBufPt, byte[] responseBuf, BufferPointer writeBufPt) throws NetworkException, RequestException {
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        boolean returnValue = servant.closeAccount(name, password, cType);
        if (returnValue){
            MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
        } else {
            MarshUtil.marshShort((short)0, responseBuf, writeBufPt);
        }
    }

    public static void changeBalance(byte[] requestData, BufferPointer readBufPt, byte[] responseBuf, BufferPointer writeBufPt) throws NetworkException, RequestException {
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float change = MarshUtil.unmarshFloat(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        float returnValue = servant.changeBalance(name, password, cType, change);
        MarshUtil.marshFloat(returnValue, responseBuf, writeBufPt);
    }

    public static void subscribeForUpdate(byte[] requestData, BufferPointer readBufPt, String ipAddr, short portNum) throws NetworkException {
        float monitorInterval = MarshUtil.unmarshFloat(requestData, readBufPt);
        servant.subscribeForUpdate(monitorInterval);    // where to get the client information?
    }

    public static void main(String[] args){
        
        byte[] requestBuf = new byte[MAX_REQUEST_BUF_SIZE];
        BufferPointer readBufPt = new BufferPointer();
        BufferPointer writeBufPt = new BufferPointer();
        
        // receive request data
        MarshUtil.marshShort((short)0, requestBuf, writeBufPt);
        MarshUtil.marshCType(CurrencyType.SGD, requestBuf, writeBufPt);
        MarshUtil.marshFloat(0.01f, requestBuf, writeBufPt);
        MarshUtil.marshCharArray("password".toCharArray(), requestBuf, writeBufPt);
        MarshUtil.marshString("John Doe", requestBuf, writeBufPt);

        String ipAddr = "192.168.1.1";
        short portNum = 80;
        
        short requestType = MarshUtil.unmarshShort(requestBuf, readBufPt);
        byte[] responseBuf;
        writeBufPt.at = 0;
        try{
            switch (requestType){
                case 0:
                    responseBuf = new byte[2 + 4];
                    MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
                    openAccount(requestBuf, readBufPt, responseBuf, writeBufPt);
                    break;
                case 1:
                    responseBuf = new byte[2 + 2];
                    MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
                    closeAccount(requestBuf, readBufPt, responseBuf, writeBufPt);
                    break;
                case 2:
                    responseBuf = new byte[2 + 4];
                    MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
                    changeBalance(requestBuf, readBufPt, responseBuf, writeBufPt);
                    break;
                case 3:
                    responseBuf = new byte[2];
                    MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
                    subscribeForUpdate(requestBuf, readBufPt, ipAddr, portNum);
                default:
                    throw new RequestException();
            }
            System.out.println("Response hex: " + MarshUtil.bytesToHex(responseBuf));
        } catch (Exception e) {
            System.out.println("Exception occurred when handling request from " + ipAddr + ": " + portNum + ". "+ e);
        }

    }

}
