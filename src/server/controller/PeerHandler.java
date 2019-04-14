package server.controller;
import java.net.*;
import java.io.*;
import common.*;

public class PeerHandler extends Thread
{
	private Socket client;
	private PeerTable info;
	
	public PeerHandler(Socket client, PeerTable info)
	{
		this.client = client;
		this.info = info;
	}
	
	@Override
	public void start()
	{
		try
		{
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(client.getInputStream());

			Message message = (Message) input.readObject();
			String userInput = message.getCommand();
			
			if(userInput.equals("JOIN"))
			{
				info.addUser(message.getUser());
				System.out.println("Player " + message.getUser().getHostName() + " has joined the game");
			}
			
			else if(userInput.equals("LEAVE"))
			{
				info.removeUser(message.getUser());
				System.out.println("Player " + message.getUser().getHostName() + " has left the game");
			}
			
			else if(userInput.equals("LIST"))
			{
				output.writeObject(info.getList());
			}
			
			else if(userInput.equals("TEST"))
			{
				output.writeObject("CONNECTED");
			}
			
			input.close();
			output.close();
			client.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong with the client");
		}
	}
}
