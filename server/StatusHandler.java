import java.util.*;
import java.io.*;
import java.nio.*;

public class StatusHandler extends RequestHandler {
	
	public StatusHandler(ConnectionService service)
	{
		super(service);
	}

	public void execute(byte[] tokenByte)
	{
		int status;
		try {
			String statusString = new String(tokenByte, "UTF-8");
			status = Integer.parseInt(statusString);
		}
		catch(Exception e) {
			QFLogger.INSTANCE.Log("Status parse error.");
			return;
		}

		// Set client data status
		ClientData clientData = GameDatabase.INSTANCE.getClientData(service.getId());
		clientData.setStatus(status);
		GameDatabase.INSTANCE.updateClientData(clientData.getId(), clientData);


		// Check another client in the room by query roomId in database, if all same , all send StatusChange response.
		List<ClientData> otherClientDatas = GameDatabase.INSTANCE.getClientDataByRoomNumber(clientData.getId(), clientData.getRoomNumber());
		if (otherClientDatas.size() == 0)
			return;
		for (ClientData otherClientData : otherClientDatas){
			if (otherClientData.getStatus() != status)
				return;
		}


		String requestType = "NEWSTATUS ";
		byte[] packet = new byte[requestType.getBytes().length + 1];
		System.arraycopy(requestType.getBytes(), 0, packet, 0, requestType.getBytes().length);
		packet[packet.length - 1] = (byte)status;
		for (ClientData otherClientData : otherClientDatas)
			otherClientData.getService().sendMessage(packet);
		service.sendMessage(packet);
	}
}