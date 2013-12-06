import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.xiao.Socket.UDP.IpDetector.FormErrorException;
import com.xiao.Socket.UDP.IpDetector.IpDetector;
import com.xiao.Socket.UDP.IpDetector.UserInfo;


public class test
{
	static final byte[] ipAddr = {(byte) 229,(byte) 201,(byte) 137,5};
	static final int port = 10490;
	public test()
	{
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException, FormErrorException, InterruptedException
	{
		ArrayList<String> he= new ArrayList<>();
		he.toArray(new String[1]);
		
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String sign = read.readLine();
		IpDetector ipDetector = new IpDetector(InetAddress.getByAddress(ipAddr), port, sign);
		ipDetector.setSenderSleepTime(1000);
		ipDetector.startListen();
		ipDetector.startSender();
		
		while(true)
		{
			Thread.sleep(5000);
			UserInfo[] u = ipDetector.getUserInfos();
			if(u == null)
				continue;
			for(int i=0;i<u.length;++i)
			{
				System.out.println(u[i]);
				System.out.println();
			}
			System.out.println("___________________________________________________");
		}
	}
}
