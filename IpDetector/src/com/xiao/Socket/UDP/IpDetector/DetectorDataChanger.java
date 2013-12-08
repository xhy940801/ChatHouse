package com.xiao.Socket.UDP.IpDetector;

import java.net.DatagramPacket;
import java.nio.charset.Charset;

public class DetectorDataChanger implements DataChunk
{
	private DataPacket dataPacket;
	
	static public final int chunkLength = 128;
	static public final Charset charset = Charset.forName("utf-8");
	static public final byte[] sign = "ChatHouseByXXZH".getBytes(charset);
	
	static public final int maxDataLength = chunkLength - sign.length - 2;
	
	public DetectorDataChanger(DataPacket dataPacket)
	{
		this.dataPacket = dataPacket;
	}

	@Override
	public Object getObject(DatagramPacket packet) throws FormErrorException
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
		for(int i=0;i<DetectorDataChanger.sign.length;++i)
		{
			if(data[data.length - 1 - i] != DetectorDataChanger.sign[DetectorDataChanger.sign.length - 1 - i])
				throw new FormErrorException("Data format error!");
		}
		byte[] re = new byte[data[0]];
		System.arraycopy(data, 1, re, 0, re.length);
		return new UserInfo(this.dataPacket.getDataPacket(re), packet.getAddress(), packet.getPort());
	}

	@Override
	public byte[] getSendByte() throws FormErrorException
	{
		byte[] sendByte = new byte[chunkLength];
		byte[] data = dataPacket.toBytes();
		
		if(data.length > maxDataLength)
			throw new FormErrorException("The DataPacket is over the buf");
		
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
		
		System.arraycopy(DetectorDataChanger.sign, 0, sendByte, maxDataLength + 2, DetectorDataChanger.sign.length);
		return sendByte;
	}

	@Override
	public byte[] getBlankBuf()
	{
		return new byte[chunkLength];
	}

}
