import java.net.*;
import java.io.*;

	public class TestUDPServer {

	public static void main(String[] args) throws Exception{
		byte[] b = new byte[1024];
		DatagramPacket dp = new DatagramPacket(b, b.length);
		DatagramSocket ds = new DatagramSocket(4567);

		while (true) {
			ds.receive(dp);
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			DataInputStream dis = new DataInputStream(bais);
			System.out.println(dis.readLong());
		}
	}
}
