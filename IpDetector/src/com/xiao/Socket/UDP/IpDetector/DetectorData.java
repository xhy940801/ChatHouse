package com.xiao.Socket.UDP.IpDetector;

import java.nio.charset.Charset;

public class DetectorData implements DataPacket
{
	private short freeConnection;
	private String username;
	public static final Charset charset = Charset.forName("utf-8");
	
	public DetectorData(String username)
	{
		this.username = username;
	}
	
	public DetectorData(short freeConnection, String username)
	{
		this.freeConnection = freeConnection;
		this.username = username;
	}
	
	@Override
	public DetectorData getDataPacket(byte[] data) throws FormErrorException
	{
		short freeConnection = (short) ((data[0] & 0x00ff) | (data[1] << 8));
		
		byte[] usernameBytes = new byte[data.length - 2];
		System.arraycopy(data, 2, usernameBytes, 0, usernameBytes.length);
		String username = new String(usernameBytes,charset);
		
		return new DetectorData(freeConnection, username);
	}

	@Override
	public byte[] toBytes()
	{
		byte[] intBytes = new byte[2];
		intBytes[0] = (byte)(freeConnection);
		intBytes[1] = (byte)(freeConnection >> 8);
		
		byte[] usernameBytes = username.getBytes(charset);
		byte[] dataBytes = new byte[2 + usernameBytes.length];
		System.arraycopy(intBytes, 0, dataBytes, 0, intBytes.length);
		System.arraycopy(usernameBytes, 0, dataBytes, intBytes.length, usernameBytes.length);
		return dataBytes;
	}
	
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj instanceof DetectorData)
		{
			return freeConnection == ((DetectorData)obj).getFreeConnection() &&
					username.equals(((DetectorData)obj).getUsername());
		}
		else
			return false;
	}

	public short getFreeConnection()
	{
		return freeConnection;
	}

	public void setFreeConnection(short freeConnection)
	{
		this.freeConnection = freeConnection;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public final void addOneFreeConnection()
	{
		++freeConnection;
	}
	
	public final void reduceOneFreeConnection()
	{
		--freeConnection;
	}
	
	public String toString()
	{
		return "Username: " + username +"\r\n" +
				"Free connection: " + freeConnection;
	}
}
