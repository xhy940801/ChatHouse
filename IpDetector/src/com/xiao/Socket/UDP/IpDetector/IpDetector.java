package com.xiao.Socket.UDP.IpDetector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class IpDetector
{
	private InetAddress boardcastIpAddr;
	private int port;
	MulticastListener multicastListener;
	MulticastSender multicastSender;
	MulticastSocket socket;
	ThreadSafeList<UserInfo> userInfoThreadSafeList = new ThreadSafeList<>(10);
	HelloData helloData = new HelloData();
	Thread listener;
	Thread sender;
	
	public IpDetector(InetAddress boardcastIpAddr, int port, String user) throws IOException, FormErrorException
	{
		this.boardcastIpAddr = boardcastIpAddr;
		this.port = port;
		socket = new MulticastSocket(port);
		socket.joinGroup(boardcastIpAddr);
		socket.setLoopbackMode(false);
		helloData.setData(user);
		this.multicastListener = new MulticastListener(socket, helloData, userInfoThreadSafeList);
		this.multicastSender = new MulticastSender(socket, boardcastIpAddr, port, helloData);
	}
	
	public InetAddress getBoardcastIpAddr()
	{
		return boardcastIpAddr;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void startListen()
	{
		listener = new Thread(multicastListener);
		listener.start();
	}
	
	public void startSender()
	{
		sender = new Thread(multicastSender);
		sender.start();
	}
	
	public UserInfo[] getUserInfos()
	{
		return userInfoThreadSafeList.getAllAndFlush(UserInfo[].class);
	}
	
	public void setSenderSleepTime(int time)
	{
		this.multicastSender.setSleepTime(time);
	}
	
	public int getSenderSleepTime()
	{
		return this.multicastSender.getSleepTime();
	}
}
