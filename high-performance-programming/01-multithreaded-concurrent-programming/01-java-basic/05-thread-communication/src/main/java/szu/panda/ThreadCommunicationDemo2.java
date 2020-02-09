package szu.panda;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: wunt
 * @date: 2019-05-02
 * @description:这里实现线程协调通信API：suspend/resume
 */
public class ThreadCommunicationDemo2 {

	public static Object baozidian = null;

	public static void main(String[] args) throws Exception {
		ThreadCommunicationDemo2 test = new ThreadCommunicationDemo2();
//		test.suspendResumeTest1();
//		test.suspendResumeTest2();
//		test.suspendResumeTest3();
//		test.waitNotifyTest1();
//		test.waitNotifyTest2();
//		test.parkUnparkTest1();
		test.parkUnparkTest2();

	}

	// 正常的suspend/resume
	public void suspendResumeTest1() throws Exception {
		Thread thread = new Thread(() -> {
			if (baozidian == null) {
				System.out.println("包子店没有包子，等待包子产出");
				Thread.currentThread().suspend();
			}
			System.out.println("买到了包子，好开心");
		});
		thread.start();
		Thread.sleep(3000);
		baozidian = new Object();
		thread.resume();
		System.out.println("通知消费者");
	}

	// 同步死锁的suspend/resume
	public void suspendResumeTest2() throws Exception {
		Thread thread = new Thread(() -> {
			if (baozidian == null) {
				System.out.println("包子店没有包子，等待包子产出");
				synchronized (this) {
					Thread.currentThread().suspend();
				}
			}
			System.out.println("买到了包子，好开心");
		});
		thread.start();
		Thread.sleep(3000);
		baozidian = new Object();
		synchronized (this) {
			thread.resume();
		}
		System.out.println("通知消费者");
	}

	// resume比suspend先执行的suspend/resume,导致程序永久挂起
	public void suspendResumeTest3() throws Exception {
		Thread thread = new Thread(() -> {
			try {
				if (baozidian == null) {
					System.out.println("包子店没有包子，等待包子产出");
					Thread.sleep(5000);
					Thread.currentThread().suspend();
				}
				System.out.println("买到了包子，好开心");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.start();
		Thread.sleep(1000);
		baozidian = new Object();
		thread.resume();
		System.out.println("通知消费者");
	}

	// 正常的wait/notify，会自动解锁，但对顺序有要求。另外，只能由同一对象锁的持有者线程调用，即要调用方法，必须拿到锁
	public void waitNotifyTest1() throws Exception {
		Thread thread = new Thread(() -> {
			synchronized (this) {
				while (baozidian == null) {
					try {
						System.out.println("包子店没有包子，等待包子产出");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("买到了包子，好开心");
			}
		});
		thread.start();
		Thread.sleep(2000);
		baozidian = new Object();
		synchronized (this) {
			this.notify();
			System.out.println("通知消费者");
		}

	}

	// notify比wait先执行的wait/notify，会导致程序永久等待
	public void waitNotifyTest2() throws Exception {
		Thread thread = new Thread(() -> {

			if (baozidian == null) {
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (this) {
					try {
						System.out.println("包子店没有包子，等待包子产出");
						this.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("买到了包子，好开心");
		});
		thread.start();
		Thread.sleep(2000);
		baozidian = new Object();
		synchronized (this) {
			this.notify();
			System.out.println("通知消费者");
		}
	}

	// 正常的park/unpark,不要求park和unpark的先后顺序,若先调用unpark，许可已经存在了，再调用park，可以正常执行。
	//Park/unpark不会对锁进行释放，是直接操作线程，让线程处于唤醒状态。不是基于监视器实现的，是JVM底层提供的另外一种挂起的方式，和wait/notify有点区别。
	//Unpark调用时，需指定线程。LockSupport工具类
	public void parkUnparkTest1() throws Exception {
		Thread thread = new Thread(() -> {
			while (baozidian == null) {
				System.out.println("包子店没有包子，等待包子产出");
				LockSupport.park();
			}
			System.out.println("买到了包子，好开心");
		});
		thread.start();
		Thread.sleep(2000);
		baozidian = new Object();
		LockSupport.unpark(thread);
		System.out.println("通知消费者");
	}
	
	//死锁的park/unpark
	public void parkUnparkTest2() throws Exception {
		Thread thread = new Thread(() -> {
			if (baozidian == null) {
				System.out.println("包子店没有包子，等待包子产出");
				synchronized (this) {
					LockSupport.park();
				}
			}
			System.out.println("买到了包子，好开心");
		});
		thread.start();
		Thread.sleep(2000);
		baozidian = new Object();
		synchronized (this) {
			LockSupport.unpark(thread);
		}
		System.out.println("通知消费者");
	}
}
