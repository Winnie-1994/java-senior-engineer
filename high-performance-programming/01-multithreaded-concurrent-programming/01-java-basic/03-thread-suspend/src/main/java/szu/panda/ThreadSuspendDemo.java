package szu.panda;
/** 
* @author: wunt
* @date: 2019-05-01 
* @description:线程中止，比较stop()、interrupt()和标志位
*/
public class ThreadSuspendDemo {
	 public volatile static boolean flag = true;
	
	public static void main(String[] args) throws InterruptedException {
		StopThread thread1 = new StopThread();
		thread1.start();
		Thread.sleep(1000);
//		thread1.stop(); //错误的终止，会强行中断程序。不安全，禁用
		thread1.interrupt();//正确终止，控制程序的执行处于线程安全状态，抛出异常
		while (thread1.isAlive()) {}
		thread1.print();
		
		//采用标志位进行中止
		Thread thread2 = new Thread(()-> {
			while (flag) {
				try {
					Thread.sleep(1000);
					System.out.println("执行中");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread2.start();
		Thread.sleep(3000);
		flag = false;
		System.out.println("线程2运行结束");
		

	}

}
