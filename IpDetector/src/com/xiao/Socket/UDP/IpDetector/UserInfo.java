package com.xiao.Socket.UDP.IpDetector;

import java.net.InetAddress;

public class UserInfo
{
	private String username;
	private InetAddress ipAddr;
	private int port;
	
	public UserInfo()
	{
		
	}
	
	public UserInfo(String username, InetAddress ipAddr, int port)
	{
		this.username = username;
		this.ipAddr = ipAddr;
		this.port = port;
	}
	
	public String getUserName()
	{
		return username;
	}
	
	public void setUserName(String username)
	{
		this.username = username;
	}
	
	public InetAddress getIpAddr()
	{
		return ipAddr;
	}
	
	public void setIpAddr(InetAddress ipAddr)
	{
		this.ipAddr = ipAddr;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}
	
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj instanceof UserInfo)
		{
			return username.equals(((UserInfo) obj).getUserName()) &&
					ipAddr.equals(((UserInfo) obj).getIpAddr()) &&
					port == ((UserInfo)obj).getPort();
		}
		else
			return false;
	}
	
	public String toString()
	{
		return "Username: " + username +"\r\n" +
				"Ip address: " + ipAddr + "\r\n" +
				"Port: " + port;
	}
}
