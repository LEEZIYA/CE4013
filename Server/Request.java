package Server;

public class Request {
    int requestType;
    byte[] requestData;

    Request(int requestType, byte[] requestData){
        this.requestType = requestType;
        this.requestData = requestData;
    }

}
