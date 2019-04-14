package common;
import java.io.*;

public class Message implements Serializable
{
	private String command;
	private int row;
	private int column;
	private int piece;
	private User user;
	private boolean accept;
	
	public String getCommand()
	{
		return command;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public void setColumn(int column)
	{
		this.column = column;
	}
	
	public int getPiece()
	{
		return piece;
	}
	
	public void setPiece(int piece)
	{
		this.piece = piece;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public boolean isAccept()
	{
		return accept;
	}
	
	public void setAccept(boolean accept)
	{
		this.accept = accept;
	}
}
