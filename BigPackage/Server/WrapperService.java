package BigPackage.Server;

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

    public void addFailureResponse(String errorMessage){
        int dataByteCount = MarshUtil.getStringByteLen(errorMessage);
        responseBuf = new byte[2 + dataByteCount];

        MarshUtil.marshShort((short)0, responseBuf, writeBufPt);
        MarshUtil.marshString(errorMessage, responseBuf, writeBufPt);
    }

    private void addSuccessResponseCode(){
        MarshUtil.marshShort((short)1, responseBuf, writeBufPt);
    }

    public void openAccount() {     // TBD: exception types, exception content
        responseBuf = new byte[2 + 4];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float initialBalance = MarshUtil.unmarshFloat(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);        // TBD: length of password
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        int returnValue = coreService.openAccount(name, password, cType, initialBalance);
        addSuccessResponseCode();
        MarshUtil.marshInt(returnValue, responseBuf, writeBufPt);

        // send broadcastUpdate
    }

    public void closeAccount() throws RequestException {
        responseBuf = new byte[2 + 2];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        int accountNum = MarshUtil.unmarshInt(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        coreService.closeAccount(accountNum, name, password, cType);
        addSuccessResponseCode();

        // send broadcastUpdate
    }
 
    public void changeBalance() throws RequestException{
        responseBuf = new byte[2 + 4];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float change = MarshUtil.unmarshFloat(requestData, readBufPt);
        int accountNum = MarshUtil.unmarshInt(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        float returnValue = coreService.changeBalance(accountNum, name, password, cType, change);
        addSuccessResponseCode();
        MarshUtil.marshFloat(returnValue, responseBuf, writeBufPt);

        // send broadcastUpdate
    }

    public float getMonitorInterval(){
        responseBuf = new byte[2 + 4];
        float monitorInterval = MarshUtil.unmarshFloat(requestData, readBufPt);
        return monitorInterval;
    }

    public void getAccountBalance() throws RequestException{
        responseBuf = new byte[2 + 4];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        int accountNum = MarshUtil.unmarshInt(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        float returnValue = coreService.getAccountBalance(accountNum, name, password, cType);
        addSuccessResponseCode();
        MarshUtil.marshFloat(returnValue, responseBuf, writeBufPt);

        // send broadcastUpdate
    }

    public void transferFund() throws RequestException{
        responseBuf = new byte[2 + 4];
        CurrencyType cType = MarshUtil.unmarshCType(requestData, readBufPt);
        float amount = MarshUtil.unmarshFloat(requestData, readBufPt);
        int destAccountNum = MarshUtil.unmarshInt(requestData, readBufPt);
        int accountNum = MarshUtil.unmarshInt(requestData, readBufPt);
        char[] password = MarshUtil.unmarshCharArray(requestData, 8, readBufPt);
        String name = MarshUtil.unmarshString(requestData, readBufPt);

        float returnValue = coreService.transferFund(accountNum, name, password, cType, destAccountNum, amount);
        addSuccessResponseCode();
        MarshUtil.marshFloat(returnValue, responseBuf, writeBufPt);

        // send broadcastUpdate
    }

    public byte[] getResponse(){
        return responseBuf;
    }

    public String getResponseInHex(){
        return MarshUtil.bytesToHex(responseBuf);
    }

    public int getResponseLength(){
        return writeBufPt.at;
    }

}
