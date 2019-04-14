package server.model;
import java.net.*;

import common.PeerTable;
import server.controller.PeerHandler;

public class Server 
{
	private static final int PORT = 1234;
	private ServerSocket serverSocket;
	private PeerTable info;
	
	public Server()
	{
		try
		{
			info = new PeerTable();
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started...");
			
			while(true)
			{
				Socket client = serverSocket.accept();
				System.out.println("New Client");
				Thread t = new PeerHandler(client, info);
				t.start();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
