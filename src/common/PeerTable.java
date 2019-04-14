package common;
import java.util.concurrent.*;
import java.util.*;

public class PeerTable 
{
	private int MAX_CONNECTIONS = 1;
	private final Semaphore semaphore = new Semaphore(MAX_CONNECTIONS);
	private ArrayList<User> users;
	
	public PeerTable()
	{
		users = new ArrayList<User>();
	}
	
	public void addUser(User newUser) throws Exception
	{
		while(semaphore.tryAcquire() == false)
		{
			Thread.sleep(5);
		}
		
		boolean canAdd = true;
		for(User temp: users)
		{
			if(temp.getHostName().equals(newUser.getHostName()))
			{
				System.out.println("User is already in the List");
				canAdd = false;
			}
		}
		
		if(canAdd)
		{
			users.add(newUser);
		}
		
		semaphore.release();
	}
	
	
	public void removeUser(User oldUser)
	{
		try
		{
			while(semaphore.tryAcquire() == false)
			{
				Thread.sleep(5);
			}
			
			for(int i = 0; i < users.size(); i++)
			{
				if(users.get(i).getHostName().equals(oldUser.getHostName()))
				{
					users.remove(i);
					break;
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		semaphore.release();
	}
	
	public ArrayList<User> getList()
	{
		return users;
	}
	
}
