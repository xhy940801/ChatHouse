package com.xiao.Socket.UDP.IpDetector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class IpDetector
{
	private InetAddress boardcastIpAddr;
	private int port;
	private MulticastListener multicastListener;
	private MulticastSender multicastSender;
	private MulticastSocket socket;
	private ThreadSafeList<UserInfo> userInfoThreadSafeList = new ThreadSafeList<>(10);
	
	private DetectorDataChanger detectorDataChanger;
	private DetectorData detectordata;
	
	private Thread listener;
	private Thread sender;
	
	public IpDetector(InetAddress boardcastIpAddr, int port, String user, short freeConnection) throws IOException, FormErrorException
	{
		this.boardcastIpAddr = boardcastIpAddr;
		this.port = port;
		socket = new MulticastSocket(port);
		socket.joinGroup(boardcastIpAddr);
		socket.setLoopbackMode(false);
		
		detectordata = new DetectorData(freeConnection, user);
		detectorDataChanger = new DetectorDataChanger(detectordata);
		
		this.multicastListener = new MulticastListener(socket, detectorDataChanger, userInfoThreadSafeList);
		this.multicastSender = new MulticastSender(socket, boardcastIpAddr, port, detectorDataChanger);
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
	
	public short getFreeConnection()
	{
		return this.detectordata.getFreeConnection();
	}
	
	public void setFreeConnection(short freeConnection)
	{
		this.detectordata.setFreeConnection(freeConnection);
	}
	
	public final void addOneFreeConnection()
	{
		this.detectordata.addOneFreeConnection();
	}
	
	public final void reduceOneFreeConnection()
	{
		this.detectordata.reduceOneFreeConnection();
	}
}
