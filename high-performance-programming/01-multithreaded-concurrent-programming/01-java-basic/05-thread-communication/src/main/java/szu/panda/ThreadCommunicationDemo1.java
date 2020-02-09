package szu.panda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;

/**
 * @author: wunt
 * @date: 2019-05-01
 * @description:线程之间的相互通信，分为四类：文件共享、网络共享、变量共享、jdk提供的线程协调API
 * 这里实现线程之间的文件共享、变量共享
 */
public class ThreadCommunicationDemo1 {

	public static String content = "空";

	public static void main(String[] args) {
		// 文件共享
		// 线程1：写入数据
		Thread thread1 = new Thread(() -> {
			try {
				while (true) {
					Files.write(Paths.get("ThreadCommunicationDemo.log"),
							("当前时间：" + String.valueOf(System.currentTimeMillis())).getBytes());
					Thread.sleep(1000);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread1.start();
		// 线程2：读取文件数据
		Thread thread2 = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					byte[] allBytes = Files.readAllBytes(Paths.get("ThreadCommunicationDemo.log"));
					System.out.println(new String(allBytes));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread2.start();

		// 共享变量
		Thread thread3 = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(1000);
					content = String.valueOf(System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread3.start();
		Thread thread4 = new Thread(() -> {
			try {
				while (true) {
					System.out.println("content:" + content);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread4.start();

	}

}
