package peer.model;
import java.io.*;
import java.util.*;
import java.net.*;
import common.*;

public class Peer 
{
	
	private int board[][] = new int[3][3];
	private static final int MAIN_SERVER_PORT = 1234;
	private static final int GAME_PORT = 2345;
	private static final int CONTROL_PORT = 2346;
	private ArrayList<User> activeUsers;
	private String server;
	private String hostName;
	private String ipAddress;
	private String userName;
	private boolean gameStarted = false;
	private boolean playerTurn = false;
	private int piece;
	private User opponent;
	private TicTacToeGame game;
	
	public Peer()
	{
		try
		{
			hostName = InetAddress.getLocalHost().getHostName();
			ipAddress = InetAddress.getByName(hostName).getHostAddress();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Could not initialize peer");
		}
	}
	
	public boolean setServer(String server)
	{
		boolean confirmed = false;
		
		if(server.length() > 0)
		{
			Socket test;
			try
			{
				test = new Socket(server, MAIN_SERVER_PORT);
				ObjectOutputStream output = new ObjectOutputStream(test.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(test.getInputStream());
				Message  message = new Message();
				message.setCommand("TEST");
				output.writeObject(message);
				String reply = (String) input.readObject();
				if(reply.equals("CONNECTED"))
				{
					confirmed = true;
					this.server = server;
				}
				output.close();
				input.close();
				test.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return confirmed;
	}
	
	public void joinServer(String userName)
	{
		try
		{
			this.userName = userName;
			Socket client = new Socket(server, MAIN_SERVER_PORT);
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			User newUser = new User(hostName, userName, ipAddress);
			Message message = new Message();
			message.setCommand("JOIN");
			message.setUser(newUser);
			output.writeObject(message);
		}
		catch(Exception e)
		{
			System.out.println("Could not join server");
		}
	}
	
	public void leaveServer()
	{
		try
		{
			Socket client = new Socket(server, MAIN_SERVER_PORT);
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			User oldUser = new User(hostName, userName, ipAddress);
			Message message = new Message();
			message.setCommand("LEAVE");
			message.setUser(oldUser);
			output.writeObject(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getOnlineUsers()
	{
		try
		{
			Socket client = new Socket(server, MAIN_SERVER_PORT);
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(client.getInputStream());
			Message message = new Message();
			message.setCommand("LIST");
			output.writeObject(message);
			this.activeUsers = (ArrayList<User>) input.readObject();
			output.close();
			input.close();
			client.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return activeUsers;
	}
	
	public void sendInvite(String userName)
	{
		String user = userName.split(": ")[1];
		User host = null;
		for(User i: activeUsers)
		{
			if(i.getHostName().equals(user))
			{
				host = i;
				break;
			}
		}
		
		try
		{
			Socket client = new Socket(host.getIpAddress(), this.CONTROL_PORT);
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			Message message = new Message();
			message.setCommand("INVITE");
			User me = new User(this.hostName, this.userName, this.ipAddress);
			message.setUser(me);
			piece = randomGenerator();
			message.setPiece(piece);
			output.writeObject(message);
//			opponent = host;
			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void acceptInvite(User opponent, int newPiece)
	{
		this.opponent = opponent;
		this.piece = newPiece;
		try
		{
			Socket client = new Socket(opponent.getIpAddress(), CONTROL_PORT);
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			Message message = new Message();
			User me = new User(this.hostName, this.userName, this.ipAddress);
			message.setCommand("ACCEPT");
			message.setUser(me);
			output.writeObject(message);
			this.game = new TicTacToeGame();
			gameStarted = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void rejectInvite()
	{
		
	}
	
	public void setHaveGame()
	{
		this.gameStarted = true;
		this.playerTurn = true;
	}
	
	public boolean getHaveGame()
	{
		return this.gameStarted;
	}
	
	public int randomGenerator()
	{
		Random i = new Random();
		int num = i.nextInt(10);
		if(num%2 == 0)
			return 1;
		else
			return -1;
	}
	
	
}
