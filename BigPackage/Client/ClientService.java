package BigPackage.Client;

import BigPackage.MarshBuffer;

import BigPackage.MarshUtil;

public class ClientService {

	private Client5 udpClient;
	//private int monitorTimeMilli;
	private long monitorEnd;

	public ClientService(){
		try{
			udpClient = new Client5(40000); //port 17
		} catch (Exception e){
			System.out.println("error initializing client socket! please restart" + e);
		}
	}

	private boolean getSuccessStatus(MarshBuffer messageBuffer){
		return (messageBuffer.unmarshShort()==1?true:false);
	}

	public Response openAccount(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+MarshUtil.getStringByteLen(accountInfo.getPassword())+MarshUtil.getStringByteLen(accountInfo.getName()));
		messageBuffer.marshShort((short)0); //requestType
		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshFloat(accountInfo.getInitialBalance());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpClient.sendMSG(message); //Byte reply is where the reply from the server. This placehodler includes waiting.
		//blocking wait for reponse?
		
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(success){
			// response.setMessage("Account created successfully");
			int accountNum = replyBuffer.unmarshInt();
			AccountInfo account = new AccountInfo();
			account.setAccountNum(accountNum);
			response.setAccountInfo(account);
		}
		else{
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
	}
	public Response closeAccount(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+MarshUtil.getStringByteLen(accountInfo.getPassword())+MarshUtil.getStringByteLen(accountInfo.getName()));
		messageBuffer.marshShort((short)1); //requestType
		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpClient.sendMSG(message);
		//blocking wait for reponse?
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(!success){
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
		
	}

	public Response withdrawMoney(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+4+MarshUtil.getStringByteLen(accountInfo.getPassword())+MarshUtil.getStringByteLen(accountInfo.getName()));
		messageBuffer.marshShort((short)2); //requestType

		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshFloat((-1)*accountInfo.getChange());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpClient.sendMSG(message);
		//blocking wait for reponse?
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(success){
			// response.setMessage("Account created successfully");
			Float currentBalance = replyBuffer.unmarshFloat();
			AccountInfo account = new AccountInfo();
			account.setCurrentBalance(currentBalance);
			response.setAccountInfo(account);
		}
		else{
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
		
	}

	public Response depositMoney(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+4+MarshUtil.getStringByteLen(accountInfo.getPassword())+MarshUtil.getStringByteLen(accountInfo.getName()));
		messageBuffer.marshShort((short)2); //requestType

		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshFloat(accountInfo.getChange());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpClient.sendMSG(message);
		//blocking wait for reponse?
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(success){
			// response.setMessage("Account created successfully");
			Float currentBalance = replyBuffer.unmarshFloat();
			AccountInfo account = new AccountInfo();
			account.setCurrentBalance(currentBalance);
			response.setAccountInfo(account);
		}
		else{
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
		
	}


	//unblocking send
	public void subscribeForUpdate(int monitorInterval){
		MarshBuffer messageBuffer = new MarshBuffer(2+4);
		messageBuffer.marshShort((short)3); //requestType
		//this.monitorTimeMilli = monitorInterval*60*1000;
		messageBuffer.marshInt(monitorInterval*60*1000);

		byte[] message = messageBuffer.toByte();
		
		udpClient.startMonitor(message);
		this.monitorEnd = System.currentTimeMillis()+monitorInterval*60*1000;
		//need an unblocking version of send
		return;
	}

	//blocking wait for message from server
	public Response getUpdate() {
		// marshalling
		// MarshBuffer messageBuffer = new MarshBuffer(2+4);
		// messageBuffer.marshShort((short)3); //requestType

		// //messageBuffer.marshInt(monitorInterval);

		// byte[] message = messageBuffer.toByte();
		int remainingTime = (int)(this.monitorEnd - System.currentTimeMillis());
		
		byte[] reply = udpClient.listenMonitor(remainingTime);
		// //blocking wait for response
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(success){
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
		
	}



	public Response getAccountBalance(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+MarshUtil.getStringByteLen(accountInfo.getPassword())+MarshUtil.getStringByteLen(accountInfo.getName()));
		messageBuffer.marshShort((short)4); //requestType

		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpClient.sendMSG(message);
		//blocking wait for reponse?
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(success){
			// response.setMessage("Account created successfully");
			Float currentBalance = replyBuffer.unmarshFloat();
			AccountInfo account = new AccountInfo();
			account.setCurrentBalance(currentBalance);
			response.setAccountInfo(account);
		}
		else{
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
		
	}

	public Response transferFund(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+4+4+MarshUtil.getStringByteLen(accountInfo.getPassword())+MarshUtil.getStringByteLen(accountInfo.getName()));
		messageBuffer.marshShort((short)5); //requestType

		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshFloat(accountInfo.getChange());
		messageBuffer.marshInt(accountInfo.getDestAccount());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpClient.sendMSG(message);
		//blocking wait for reponse?
		Response response = new Response(); 

		MarshBuffer replyBuffer = new MarshBuffer(reply);
		
        boolean success = this.getSuccessStatus(replyBuffer);
		response.setSuccess(success);
		
		if(success){
			// response.setMessage("Account created successfully");
			Float currentBalance = replyBuffer.unmarshFloat();
			AccountInfo account = new AccountInfo();
			account.setCurrentBalance(currentBalance);
			response.setAccountInfo(account);
		}
		else{
			response.setMessage(replyBuffer.unmarshString());
		}			
		return response;
		
	}




}