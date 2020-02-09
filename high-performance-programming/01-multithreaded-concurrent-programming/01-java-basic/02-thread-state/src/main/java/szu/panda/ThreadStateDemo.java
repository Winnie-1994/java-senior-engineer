package szu.panda;
/** 
* @author: wunt
* @date: 2019-04-29 
* @description:
*/
public class ThreadStateDemo {

	public static void main(String[] args) throws Exception {
		System.out.println("#######第一种状态切换：新建 -> 运行 -> 终止################################");
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("调用run，线程执行了，线程状态：" + Thread.currentThread().getState().toString());
			}
		});
		System.out.println("新建thread，线程状态 ：" + thread1.getState().toString());
		thread1.start();
		System.out.println("调用start，线程状态：" + thread1.getState().toString());
		Thread.sleep(2000);
		System.out.println("等待2秒，线程状态：" + thread1.getState().toString());
		
		System.out.println();
		System.out.println("############第二种状态切换：新建 -> 运行 -> 等待 -> 运行 -> 终止(sleep方式)###########################");
		Thread thread2 = new Thread(()->{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("调用run，线程执行了，线程状态：" + Thread.currentThread().getState().toString());
		});
		thread2.start();
		System.out.println("调用start，线程状态：" + thread2.getState().toString());
		Thread.sleep(200);
		System.out.println("等待0.2秒，线程状态：" + thread2.getState().toString());
		Thread.sleep(3000);
		System.out.println("等待3秒，线程状态：" + thread2.getState().toString());
		
		System.out.println();
		System.out.println("############第三种状态切换：新建 -> 运行 -> 阻塞 -> 运行 -> 终止###########################");
		Thread thread3 = new Thread(()-> {
			synchronized (ThreadStateDemo.class) {
				System.out.println("调用run，线程执行了，线程状态：" + Thread.currentThread().getState().toString());
			}
		});
		
		synchronized (ThreadStateDemo.class) {
			thread3.start();
			System.out.println("调用start,线程状态：" + thread3.getState().toString());
			Thread.sleep(1000);
			System.out.println("锁住，线程状态：" + thread3.getState().toString());
		}
		Thread.sleep(2000);
		System.out.println("等待2秒，让线程3抢到锁，线程状态：" + thread3.getState().toString());
		
	}

}
