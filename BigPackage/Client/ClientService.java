package BigPackage.Client;

public class ClientService {
	public Response openAccount(AccountInfo accountInfo) {
		Response response = new Response(); //placeholder
		//marshalling
		//Response response = udpCLient.send(marshalledMessage);
		
		//placeholder
		response.setMessage("Account created successfully");
		AccountInfo account = new AccountInfo();
		account.setAccountNum(1234);
		response.setSuccess(true);
		response.setAccountInfo(account);
		//placeholder
		
		
		return response;
	}

}