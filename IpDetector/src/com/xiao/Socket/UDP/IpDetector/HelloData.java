package com.xiao.Socket.UDP.IpDetector;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public class HelloData implements DataChunk
{
	byte[] data;
	static public final Charset charset = Charset.forName("utf-8");
	static public final byte[] sign = "ChatHouseByXXZH".getBytes(charset);
	static public final int chunkLength = 128;
	static public final int maxDataLength = chunkLength - sign.length - 2;
	public HelloData()
	{
		
	}
	
	public HelloData(byte[] data) throws FormErrorException
	{
		if(data.length > maxDataLength)
			throw new FormErrorException("The length of data is too long (over " + maxDataLength + ")");
		this.data = data;
	}
	
	public HelloData(String data) throws FormErrorException
	{
		this.data = data.getBytes(charset);
		if(this.data.length > maxDataLength)
		{
			this.data = null;
			throw new FormErrorException("The length of data is too long (over " + maxDataLength + ")");
		}
	}
	
	public void setData(byte[] data) throws FormErrorException
	{
		if(data.length > maxDataLength)
			throw new FormErrorException("The length of data is too long (over " + maxDataLength + ")");
		this.data = data;
	}
	
	public void setData(String data) throws FormErrorException
	{
		this.data = data.getBytes(charset);
		if(this.data.length > maxDataLength)
		{
			this.data = null;
			throw new FormErrorException("The length of data is too long (over " + maxDataLength + ")");
		}
	}

	@Override
	public UserInfo getObject(DatagramPacket packet) throws FormErrorException
	{
		byte[] data = packet.getData();
		int sign = 0;
		for(int i=0;i<data[0];++i)
		{
			sign += data[i+1];
		}
		if(((sign + ((data[data[0]+1] >> 4) & 0x0f)) % 11) != 0)
			throw new FormErrorException("Data format error!");
		if(((sign + (data[data[0]+1] & 0x0f)) % 13) != 0)
			throw new FormErrorException("Data format error!");
		for(int i=0;i<HelloData.sign.length;++i)
		{
			if(data[data.length - 1 - i] != HelloData.sign[HelloData.sign.length - 1 - i])
				throw new FormErrorException("Data format error!");
		}
		byte[] re = new byte[data[0]];
		System.arraycopy(data, 1, re, 0, re.length);
		return new UserInfo(new String(re, charset), packet.getAddress(), packet.getPort());
	}


	@Override
	public byte[] getSendByte()
	{
		byte[] sendByte = new byte[chunkLength];
		sendByte[0] = (byte)data.length;
		System.arraycopy(data, 0, sendByte, 1, data.length);
		int sign = 0;
		for(int i=0;i<data.length;++i)
		{
			sign += data[i];
		}
		byte mod11 = (byte)(11 - (sign % 11 + 11) % 11);
		byte mod13 = (byte)(13 - (sign % 13 + 13) % 13);
		sendByte[data.length+1] = (byte) ((byte)(mod11 << 4) | mod13);
		System.arraycopy(HelloData.sign, 0, sendByte, maxDataLength + 2, HelloData.sign.length);
		return sendByte;
	}

	@Override
	public byte[] getBlankBuf()
	{
		return new byte[chunkLength];
	}
}
