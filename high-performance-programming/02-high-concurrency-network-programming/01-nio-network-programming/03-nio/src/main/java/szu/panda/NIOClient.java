package szu.panda;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author: wunt
 * @date: 2019-04-05
 * @description: NIO的三个核心组件：Buffer缓冲区、Channel通道、Selector选择器
 */
public class NIOClient {

	public static void main(String[] args) throws Exception {
		// 客户端主动发起连接的方式
		SocketChannel socketChannel = SocketChannel.open();
		// Socket默认都是阻塞的，需要配置成非阻塞
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8081));
		while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入：");
		String sysin = scanner.nextLine();
		try {
			// 写数据缓冲区
			ByteBuffer byteBuffer = ByteBuffer.wrap(sysin.getBytes());
			// 发送请求数据，向通道写数据
			while (byteBuffer.hasRemaining()) {
				socketChannel.write(byteBuffer);
			}

			// 读数据缓冲区
			ByteBuffer requestBuffer = byteBuffer.allocate(1024);
			while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
				// 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
				if (requestBuffer.position() > 0)
					break;
			}
			requestBuffer.flip();
			byte[] msg = new byte[requestBuffer.limit()];
			requestBuffer.get(msg);
			System.out.println("收到来自服务器的响应：" + new String(msg));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
			socketChannel.close();// 关闭连接
		}

	}

}
