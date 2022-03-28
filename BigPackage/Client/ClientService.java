package BigPackage.Client;

import BigPackage.MarshBuffer;

import BigPackage.MarshUtil;

public class ClientService {
	private boolean getSuccessStatus(MarshBuffer messageBuffer){
		return (messageBuffer.unmarshShort()==1?true:false);
	}

	public Response openAccount(AccountInfo accountInfo) {
		//marshalling
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+MarshUtil.getStringByteLen(accountInfo.getPassword)+MarshUtil.getStringByteLen(accountinfo.getName()));
		messageBuffer.marshShort((short)0); //requestType
		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshFloat(accountInfo.getInitialBalance());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpCLient.send(message);
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
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+MarshUtil.getStringByteLen(accountInfo.getPassword)+MarshUtil.getStringByteLen(accountinfo.getName()));
		messageBuffer.marshShort((short)1); //requestType
		messageBuffer.marshCType(accountInfo.getcType());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpCLient.send(message);
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
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+4+MarshUtil.getStringByteLen(accountInfo.getPassword)+MarshUtil.getStringByteLen(accountinfo.getName()));
		messageBuffer.marshShort((short)2); //requestType

		messageBuffer.marshCType(accountInfo.getcType());
		messageBUffer.marshFloat((-1)*accountInfo.getChange());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpCLient.send(message);
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
		MarshBuffer messageBuffer = new MarshBuffer(2+1+4+4+MarshUtil.getStringByteLen(accountInfo.getPassword)+MarshUtil.getStringByteLen(accountinfo.getName()));
		messageBuffer.marshShort((short)2); //requestType

		messageBuffer.marshCType(accountInfo.getcType());
		messageBUffer.marshFloat(accountInfo.getChange());
		messageBuffer.marshInt(accountInfo.getAccountNum());
		messageBuffer.marshCharArray(accountInfo.getPassword());
		messageBuffer.marshString(accountInfo.getName());

		byte[] message = messageBuffer.toByte();
		
		byte[] reply = udpCLient.send(message);
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