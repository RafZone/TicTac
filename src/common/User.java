package common;
import java.io.*;

public class User implements Serializable
{
	private String hostName;
	private String userName;
	private String ipAddress;
	
	public User(String hostName, String userName, String ipAddress)
	{
		this.hostName = hostName;
		this.userName = userName;
		this.ipAddress = ipAddress;
	}
	
	public String getHostName()
	{
		return this.hostName;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
	public String getIpAddress()
	{
		return this.ipAddress;
	}
}
