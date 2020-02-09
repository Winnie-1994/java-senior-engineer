package szu.panda;

import java.nio.ByteBuffer;

/**
 * @author: wunt
 * @date: 2019-04-05
 * @description: 
 * 1、Buffer的写入数据、模式转换、读取数据、清空
 * 2、直接内存和非直接内存
 */
public class BufferDemo {

	public static void main(String[] args) {
		// 构建一个byte字节缓冲区，容量是4
		ByteBuffer byteBuffer = ByteBuffer.allocate(4); //获取非直接内存（heap堆内存）
//		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4); //获取直接内存（堆外内存）
		// 查看缓冲区的三个重要属性：capacity、position、limit
		System.out.println(String.format("初始化：capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
				byteBuffer.position(), byteBuffer.limit()));
		//开始写入数据
		byteBuffer.put((byte) 1);
		byteBuffer.put((byte) 2);
		byteBuffer.put((byte) 3);
		System.out.println(String.format("写入3个数后，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
				byteBuffer.position(), byteBuffer.limit()));
		//转换为读取模式
		byteBuffer.flip();
		System.out.println(String.format("转换为读取模式，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
				byteBuffer.position(), byteBuffer.limit()));
		//从缓冲区读取数据
		byte a = byteBuffer.get();
		System.out.println("读取第一个数：" + a);
		byte b = byteBuffer.get();
		System.out.println("读取第二个数：" + b);
		System.out.println(String.format("读取2个数后，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
				byteBuffer.position(), byteBuffer.limit()));
		//清除缓冲区
		byteBuffer.compact(); //将已经读出的数据清除
//		byteBuffer.clear(); //只是把指针移到位置0，并没有真正清空数据。
		System.out.println(String.format("清除缓冲区后，capacity容量：%s, position位置：%s, limit限制：%s", byteBuffer.capacity(),
				byteBuffer.position(), byteBuffer.limit()));
	}

}
