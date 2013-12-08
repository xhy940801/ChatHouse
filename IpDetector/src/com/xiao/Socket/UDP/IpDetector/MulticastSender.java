package com.xiao.Socket.UDP.IpDetector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSender implements Runnable
{
	private int sleepTime = 30000;
	private MulticastSocket socket;
	private InetAddress boardcastIpAddr;
	private int port;
	private DataChunk dataChunk;
	boolean run = true;
	
	public MulticastSender(MulticastSocket socket, InetAddress boardcastIpAddr, int port, DataChunk dataChunk)
	{
		this.socket = socket;
		this.boardcastIpAddr = boardcastIpAddr;
		this.port = port;
		this.dataChunk = dataChunk;
	}
	
	@Override
	public void run()
	{
		while(run)
		{
			byte[] buf;
			try
			{
				buf = dataChunk.getSendByte();
			} catch (FormErrorException e1)
			{
				e1.printStackTrace();
				run = false;
				break;
			}
			DatagramPacket packet = new DatagramPacket(buf, buf.length, this.boardcastIpAddr, this.port);
			try
			{
				this.socket.send(packet);
				Thread.sleep(sleepTime);
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				this.run = false;
			}
		}
	}
	
	public void setSleepTime(int sleepTime)
	{
		this.sleepTime = sleepTime;
	}
	
	public int getSleepTime()
	{
		return sleepTime;
	}
}
