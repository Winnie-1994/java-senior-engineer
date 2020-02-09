package szu.panda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wunt
 * @date: 2019-04-05
 * @description: 增加多线程支持、增加返回客户端的结果
 */
public class BIOServer1 {

	private static ExecutorService threadpool = Executors.newCachedThreadPool();

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8888);
		System.out.println("服务器开启成功");
		while (!serverSocket.isClosed()) {
			System.out.println("等待请求中...");
			Socket socket = serverSocket.accept(); // 阻塞
			System.out.println("收到新连接 : " + socket.toString());
			threadpool.execute(() -> {
				try {
					InputStream iStream = socket.getInputStream();
					BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
					String msg;
					while ((msg = bReader.readLine()) != null) { // 没有数据，阻塞
						if (msg.length() == 0)
							break;
						System.out.println("收到来自" + socket.toString() + "的数据：" + msg);
					}
					// 返回响应结果 200
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
                    outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
                    outputStream.write("Hello World".getBytes());
                    outputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						socket.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			});
		}
		serverSocket.close();
	}

}
