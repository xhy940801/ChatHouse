package com.xiao.Socket.UDP.IpDetector;

import java.net.InetAddress;

public class UserInfo
{
	private DataPacket dataPacket;
	private InetAddress ipAddr;
	private int port;
	
	public UserInfo()
	{
		
	}
	
	public UserInfo(DataPacket dataPacket, InetAddress ipAddr, int port)
	{
		this.dataPacket = dataPacket;
		this.ipAddr = ipAddr;
		this.port = port;
	}
	
	public DataPacket getDataPacket()
	{
		return dataPacket;
	}
	
	public void setDataPacket(DataPacket dataPacket)
	{
		this.dataPacket = dataPacket;
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
			return dataPacket.equals(((UserInfo) obj).getDataPacket()) &&
					ipAddr.equals(((UserInfo) obj).getIpAddr()) &&
					port == ((UserInfo)obj).getPort();
		}
		else
			return false;
	}
	
	public String toString()
	{
		return dataPacket + "\r\n" +
				"Ip address: " + ipAddr + "\r\n" +
				"Port: " + port;
	}
}
