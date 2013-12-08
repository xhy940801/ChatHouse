package com.xiao.Socket.UDP.IpDetector;

public interface DataPacket
{
	public byte[] toBytes();
	public DataPacket getDataPacket(byte[] data) throws FormErrorException;
}
