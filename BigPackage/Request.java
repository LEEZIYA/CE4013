package BigPackage;


public class Request {
    int requestType;
    String senderIP;
    int portNumber;
    byte[] requestData;

    Request(int requestType, byte[] requestData){
        this.requestType = requestType;
        this.requestData = requestData;
    }

}
