package Server;

public class Response {
    int responseType;
    byte[] responseData;

    Response(int responseType, byte[] responseData){
        this.responseType = responseType;
        this.responseData = responseData;
    }
}
