package com.xiao.Socket.UDP.IpDetector;

import java.net.DatagramPacket;

public interface DataChunk
{
	public Object getObject(DatagramPacket packet) throws FormErrorException;
	public byte[] getSendByte() throws FormErrorException;
	public byte[] getBlankBuf();
}
