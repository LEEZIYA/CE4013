package BigPackage.Server;

import java.util.ArrayList;

import BigPackage.BufferPointer;
import BigPackage.CurrencyType;
import BigPackage.MarshUtil;

public class WrapperService {

    byte[] requestData;
    byte[] responseBuf;
    BufferPointer readBufPt;
    BufferPointer writeBufPt;
    CoreService coreService;

    WrapperService(){
        readBufPt = new BufferPointer();
        writeBufPt = new BufferPointer();
        coreService = new CoreService();
    }

    public void acceptNewRequest(byte[] requestData){
        this.requestData = requestData;
        readBufPt.at = 0;
        writeBufPt.at = 0;
    }
    
    public short decodeRequestType(){
        return MarshUtil.unmarshShort(requestData, readBufPt);
    }

    public void addSuccessResponseCode(){
        MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
    }

    public void openAccount() {     // TBD: exception types, exception content
        responseBuf = new byte[2 + 4];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float initialBalance = MarshUtil.unmarshFloat(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);        // TBD: length of password
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        int returnValue = coreService.openAccount(name, password, cType, initialBalance);
        MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
        MarshUtil.marshInt(returnValue, responseBuf, writeBufPt);

        ArrayList<SubscribedClient> subscribedClients = coreService.getClientsToNotify();
        // notify
    }

    public void closeAccount() {
        responseBuf = new byte[2 + 2];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        boolean returnValue = coreService.closeAccount(name, password, cType);
        if (returnValue){
            MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
        } else {
            MarshUtil.marshShort((short)0, responseBuf, writeBufPt);
        }

        ArrayList<SubscribedClient> subscribedClients = coreService.getClientsToNotify();
        // notify
    }

    public void changeBalance() {
        responseBuf = new byte[2 + 4];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float change = MarshUtil.unmarshFloat(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        float returnValue = coreService.changeBalance(name, password, cType, change);
        MarshUtil.marshFloat(returnValue, responseBuf, writeBufPt);

        ArrayList<SubscribedClient> subscribedClients = coreService.getClientsToNotify();
        // notify
    }

    public void subscribeForUpdate(String ipAddr, short portNum) {
        responseBuf = new byte[2];
        float monitorInterval = MarshUtil.unmarshFloat(requestData, readBufPt);
        coreService.subscribeForUpdate(ipAddr, portNum, monitorInterval);
    }

    public byte[] getResponse(){
        return responseBuf;
    }

    public String getResponseInHex(){
        return MarshUtil.bytesToHex(responseBuf);
    }

}
