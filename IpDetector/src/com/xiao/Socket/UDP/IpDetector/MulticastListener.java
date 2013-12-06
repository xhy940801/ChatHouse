package com.xiao.Socket.UDP.IpDetector;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class MulticastListener implements Runnable
{
	private MulticastSocket socket;
	private DataChunk dataChunk;
	boolean run = true;
	private ThreadSafeList<?> threadSafeList;
	public MulticastListener(MulticastSocket socket, DataChunk dataChunk, ThreadSafeList<?> threadSafeList)
	{
		this.socket = socket;
		this.dataChunk = dataChunk;
		this.threadSafeList = threadSafeList;
	}

	@Override
	public void run()
	{
		while(run)
		{
			byte[] buf = this.dataChunk.getBlankBuf();
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try
			{
				socket.receive(packet);
				this.threadSafeList.addElementNoRepeat(this.dataChunk.getObject(packet));
			} catch (IOException | FormErrorException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
