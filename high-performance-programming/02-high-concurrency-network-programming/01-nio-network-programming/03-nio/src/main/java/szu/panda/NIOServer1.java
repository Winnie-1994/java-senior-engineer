package szu.panda;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author: wunt
 * @date: 2019-04-06
 * @description:
 * 1、 这个例子对NIOServer.java的阻塞进行改造优化，建立连接的集合，一个线程处理轮询所有客户端连接请求
 * 2、 问题: 轮询通道的方式,低效,浪费CPU
 */
public class NIOServer1 {

	private static ArrayList<SocketChannel> channels = new ArrayList<SocketChannel>();

	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(8081));
		System.out.println("服务器启动成功");

		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();// 获取tcp连接通道，非阻塞
//			System.out.println("服务器在等待请求");
			// 客户端请求
			if (socketChannel != null) {
				System.out.println("收到新连接：" + socketChannel.getRemoteAddress());
				socketChannel.configureBlocking(false); // 收到连接后，默认是阻塞的，设置为非阻塞
				channels.add(socketChannel);
			} else {
				//使用迭代器，没有新连接的情况下,就去处理现有连接的数据,处理完的就删除掉
				Iterator<SocketChannel> iterator = channels.iterator();
				while(iterator.hasNext()) {
					SocketChannel s = iterator.next();
					try {
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						while (s.isOpen() && s.read(byteBuffer) != -1) {
							// 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
							if (byteBuffer.position() > 0) {
								break;
							}
						} // 造成了阻塞
						if (byteBuffer.position() == 0)
							continue; // 如果没数据了, 则不继续后面的处理
						byteBuffer.flip();
						byte[] msg = new byte[byteBuffer.limit()];
						byteBuffer.get(msg);
//						String content = new String(msg);
						System.out.println("收到来自客户端的请求：" + new String(msg));

						// 返回响应
						String response = "Hello!\r\n" + "I'm wuniting\r\n";
						ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
						while (responseBuffer.hasRemaining()) {
							s.write(responseBuffer);
						}
					} catch (Exception e) {
						e.printStackTrace();
						iterator.remove();
					} finally {
						iterator.remove();
					}
				}
				
			}
		}

	}

}
