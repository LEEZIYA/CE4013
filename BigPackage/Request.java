package BigPackage;


public class Request {
    short requestType;
    String senderIP;
    short portNumber;
    byte[] requestData;

    Request(short requestType, byte[] requestData){
        this.requestType = requestType;
        this.requestData = requestData;
    }
    // feels like don't really need this. might delete later.
}
