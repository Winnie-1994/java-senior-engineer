package szu.panda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: wunt
 * @date: 2019-04-05
 * @description:net+io，阻塞式的
 */
public class BIOServer {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out.println("服务器开启成功");
		while (!serverSocket.isClosed()) {
			System.out.println("等待请求中...");
			Socket socket = serverSocket.accept(); //阻塞
			System.out.println("收到新连接 : " + socket.toString());
			try {
				InputStream iStream = socket.getInputStream();
				BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
				String msg;
				while ((msg = bReader.readLine()) != null) { // 没有数据，阻塞
					if (msg.length() == 0)
						break;
					System.out.println("收到来自" + socket.toString() + "的数据：" + msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		serverSocket.close();
	}
}
