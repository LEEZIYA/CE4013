package BigPackage.Server;


public class ServerController {

    private static final int MAX_REQUEST_BUF_SIZE = 1024;
    private WrapperService service;

    ServerController(){
        this.service = new WrapperService();
    }

    public void start() throws Exception{
        // initialize UDP socket

        int countdown = 10000;
        byte[] requestBuf = new byte[MAX_REQUEST_BUF_SIZE];


        while (true){
            countdown -= 100;
            if (countdown == 0){
                System.out.println("Server still listening.");
                countdown = 10000;
            }

            // receive bunches of bytes

            // for testing purpose only
            // BufferPointer writeBufPt = new BufferPointer();
            // MarshUtil.marshShort((short)0, requestBuf, writeBufPt);
            // MarshUtil.marshCType(CurrencyType.SGD, requestBuf, writeBufPt);
            // MarshUtil.marshFloat(0.01f, requestBuf, writeBufPt);
            // MarshUtil.marshCharArray("password".toCharArray(), requestBuf, writeBufPt);
            // MarshUtil.marshString("John Doe", requestBuf, writeBufPt);
            String ipAddr = "192.168.1.1";
            short portNum = 80;
            
            // reset the buffer pointers
            service.acceptNewRequest(requestBuf);
            try{
                int requestType = service.decodeRequestType();
                switch (requestType){
                    case 0:
                        service.openAccount();
                        break;
                    case 1:
                        service.closeAccount();
                        break;
                    case 2:
                        service.changeBalance();
                        break;
                    case 3:
                        // ask socket layer to add client to broadcast list
                        break;
                    case 4:
                        service.getAccountBalance();
                        break;
                    case 5:
                        service.transferFund();
                        break;
                    default:
                        System.out.println("Unknown request type of " + requestType);
                }
            } catch (RequestException e) {
                System.out.println("RequestException occurred when handling request from " + ipAddr + ": " + portNum + ". "+ e);
                service.addFailureResponse(e.getMessage());
            } catch (Exception e){
                System.out.println("Exception occurred when handling request from " + ipAddr + ": " + portNum + ". "+ e);
                service.addFailureResponse("Server error");
            }

            // send response back
            byte[] repsonse = service.getResponse();
            int responseLength = service.getResponseLength();

            Thread.sleep(100);
        }
    }



}
