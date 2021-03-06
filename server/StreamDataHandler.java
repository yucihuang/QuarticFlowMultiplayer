import java.io.*;
import java.nio.charset.Charset;

public class StreamDataHandler extends RequestHandler{
	public StreamDataHandler(ConnectionService service)
	{
		super(service);
	}

	public void execute(byte[] tokenByte)
	{
		// TODO: check isOnline
		ClientData clientData = GameDatabase.INSTANCE.getClientData(service.getId());

		if (clientData == null){
			service.closeService();
			return;
		}

		if (!clientData.isOnline()){
			service.closeService();
			return;
		}

		try{
			clientData.setPlayerData(PlayerData.parse(tokenByte));
		}
		catch(IOException e){
			e.printStackTrace();
		}

		GameDatabase.INSTANCE.updateClientData(service.getId(), clientData);
	}
}