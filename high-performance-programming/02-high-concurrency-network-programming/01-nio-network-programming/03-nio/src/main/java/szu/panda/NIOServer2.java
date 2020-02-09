package szu.panda;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: wunt
 * @date: 2019-04-06
 * @description:结合Selector实现的非阻塞服务端，放弃对channel的轮询,借助消息通知机制，注册+通知
 *   问题: 此处一个selector监听所有事件,一个线程处理所有请求事件. 会成为瓶颈! 要有多线程的运用
 */
public class NIOServer2 {

	public static void main(String[] args) throws Exception {
		// 创建网络服务
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		// 构建一个selector选择器，并将channel注册上去
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel); //serverSocketChannel对accept事件感兴趣
		// 绑定端口
		serverSocketChannel.socket().bind(new InetSocketAddress(8081));
		System.out.println("服务器启动成功");

		while (true) { // 由accept轮询，变成了事件通知的方式
			// 不再轮询通道,改用下面轮询事件的方式.select方法有阻塞效果,直到有事件通知才会有返回
			selector.select();
//			int readychannels = 
//			if (readychannels == 0)
//				continue;
			// 获取事件
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			// 遍历查询结果
			Iterator<SelectionKey> keyiterator = selectedKeys.iterator();
			while (keyiterator.hasNext()) {
				// 取其中一个查询结果
				SelectionKey key = keyiterator.next();
				keyiterator.remove();

				// accept事件
				if (key.isAcceptable()) {
					// 将拿到的客户端连接通道,注册到selector上面
					ServerSocketChannel serverChannel = (ServerSocketChannel) key.attachment();
					SocketChannel socketChannel = serverChannel.accept();// 获取tcp连接通道，非阻塞
					// 读取客户端请求数据
					if (socketChannel != null) {
						socketChannel.configureBlocking(false); // 收到连接后，默认是阻塞的，设置为非阻塞
						socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
						System.out.println("收到新连接：" + socketChannel.getRemoteAddress());
					}
				}

				// read事件
				if (key.isReadable()) {
					SocketChannel socketChannel = (SocketChannel) key.attachment();
					try {
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						while (socketChannel.isOpen() && socketChannel.read(byteBuffer) != -1) {
							// 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
							if (byteBuffer.position() > 0) {
								break;
							}
						} // 造成了阻塞
						if (byteBuffer.position() == 0) {
							key.cancel(); // 取消事件订阅
							continue; // 如果没数据了, 则不继续后面的处理
						}
						byteBuffer.flip();
						byte[] msg = new byte[byteBuffer.limit()];
						byteBuffer.get(msg);
//						String content = new String(msg);
						System.out.println("收到来自客户端的请求：" + new String(msg));

						// 返回响应
						String response = "Hello!\r\n" + "I'm wuniting\r\n";
						ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
						while (responseBuffer.hasRemaining()) {
							socketChannel.write(responseBuffer);
						}
					} catch (Exception e) {
						e.printStackTrace();
						key.cancel(); // 取消事件订阅
					}
				}
			}
			selector.selectNow();
		}
	}
}
