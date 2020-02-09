package szu.panda;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author: wunt
 * @date: 2019-04-05
 * @description:
 * 1、 这个例子只实现了Buffer缓冲区和Channel通道，还没有实现Selector选择器
 * 2、 问题：造成了阻塞
 */
public class NIOServer {

	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(8081));
		System.out.println("服务器启动成功");

		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();// 获取tcp连接通道，非阻塞
//			System.out.println("服务器在等待请求");
			// 读取客户端请求数据
			if (socketChannel != null) {
				System.out.println("收到新连接：" + socketChannel.getRemoteAddress());
				socketChannel.configureBlocking(false); //收到连接后，默认是阻塞的，设置为非阻塞
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					while (socketChannel.isOpen() && socketChannel.read(byteBuffer) != -1) {
						// 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
						if (byteBuffer.position() > 0) {
							break;
						}
					}//造成了阻塞
					if(byteBuffer.position() == 0) continue; // 如果没数据了, 则不继续后面的处理
					byteBuffer.flip();
					byte[] msg = new byte[byteBuffer.limit()];
					byteBuffer.get(msg);
//					String content = new String(msg);
					System.out.println("收到来自客户端的请求：" + new String(msg));

					// 返回响应
					String response = "Hello!\r\n" + "I'm wuniting\r\n";
					ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
					while (responseBuffer.hasRemaining()) {
						socketChannel.write(responseBuffer);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
