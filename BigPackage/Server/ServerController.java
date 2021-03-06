package BigPackage.Server;

import java.util.Arrays;

public class ServerController {

    private static final int MAX_REQUEST_BUF_SIZE = 1024;
    private WrapperService service;
    private Server5 serverSocket;

    ServerController(){
        this.service = new WrapperService();
        try{
            this.serverSocket = new Server5(17);
        } catch (Exception e){
            System.out.println("got exception in initializing server5, please restart. ");
        }
    }

    public void run() throws Exception{
        while (true){
            // get new request bytes
            byte[] requestBuf = serverSocket.serverMsgWait();

            // reset the buffer pointers
            service.acceptNewRequest(requestBuf);
            String broadcastString = "";

            // handle request
            try{
                int requestType = service.decodeRequestType();
                switch (requestType){
                    case 0:
                        broadcastString = service.openAccount();
                        break;
                    case 1:
                        broadcastString = service.closeAccount();
                        break;
                    case 2:
                        broadcastString = service.changeBalance();
                        break;
                    case 3:
                        int monitorInterval = service.getMonitorInterval();
                        serverSocket.addList((long)monitorInterval);
                        service.addSuccessResponseCode();
                        break;
                    case 4:
                        broadcastString = service.getAccountBalance();
                        break;
                    case 5:
                        broadcastString = service.transferFund();
                        break;
                    default:
                        System.out.println("Unknown request type of " + requestType);
                        throw new RequestException("Unknown request");
                }
            } catch (RequestException e) {
                System.out.println("RequestException occurred when handling request! "+ e);
                service.addFailureResponse(e.getMessage());
            } catch (Exception e){
                System.out.println("Exception occurred when handling request! "+ e);
                service.addFailureResponse("Server error");
            }

            // send response
            byte[] response = service.getResponse();
            serverSocket.serverMsgSend(response);

            // broadcast update
            if (broadcastString != ""){
                System.out.println("broadcast string is: " + broadcastString);
                byte[] broadcastBytes = service.marshalBroadcastString(broadcastString);
                serverSocket.broadcastList(broadcastBytes);
            }
        }
    }



}
