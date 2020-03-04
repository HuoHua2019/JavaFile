import java.io.*;
import java.net.*;

public class TestUDPClient {

	public static void main(String[] args) throws Exception{
		Long l = 10000L;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeLong(l);

		byte[] b = baos.toByteArray();
		System.out.println(b.length);

		DatagramPacket dp = new DatagramPacket(b, b.length, new InetSocketAddress("127.0.0.1", 4567));
		DatagramSocket ds = new DatagramSocket(7777);
		ds.send(dp);
		ds.close();
	}
}
