package szu.panda;
/** 
* @author: wunt
* @date: 2019-05-06 
* @description:ThreadLocal和栈封闭
*/
public class ThreadConfinementDemo {
	//threadLocal变量，每个线程都有一个副本，互不干扰。
	//它是以线程为准的，不是方法。只要在同一线程中，跨方法可用。
	public static ThreadLocal<String> value = new ThreadLocal<>();

	public static void main(String[] args) throws Exception {
		ThreadConfinementDemo test = new ThreadConfinementDemo();
		test.ThreadLocalTest();
	}

	public void ThreadLocalTest() throws Exception {
		value.set("主线程");
		String v = value.get();
		System.out.println("主线程对value赋值：" + v);
		
		Thread thread1 = new Thread(()->{
			System.out.println("新建线程1，获取value的值为：" + value.get());
			value.set("线程1");
			System.out.println("线程1设置value值：" + value.get());
			System.out.println("线程1执行结束");
		});
		thread1.start();
		Thread.sleep(7000); // 等待所有线程执行结束
		System.out.println("线程1结束后，主线程获取value的值：" + value.get());
	}
}
