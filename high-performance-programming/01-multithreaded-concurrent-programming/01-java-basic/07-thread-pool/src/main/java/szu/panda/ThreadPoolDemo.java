package szu.panda;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.SysexMessage;

/**
 * @author: wunt
 * @date: 2019-05-06
 * @description:
 */
public class ThreadPoolDemo {

	public static void main(String[] args) throws Exception {
		ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
//		threadPoolDemo.threadPoolTest1();
//		threadPoolDemo.threadPoolTest2();
//		threadPoolDemo.threadPoolTest3();
		threadPoolDemo.threadPoolTest4();

	}

	/**
	 * 1、线程池信息： 核心线程数量5，最大数量10，无界队列，超出核心线程数量的线程存活时间：5秒
	 * 
	 * @throws Exception
	 */
	public void threadPoolTest1() throws Exception {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		testCommon(threadPoolExecutor);
		// 预计结果：线程池线程数量为：5,超出数量的任务，其他的进入队列中等待被执行
	}

	/**
	 * 2、 线程池信息： 核心线程数量5，最大数量10，队列大小3，超出核心线程数量的线程存活时间：5秒， 指定拒绝策略的
	 * 
	 * @throws Exception
	 */
	public void threadPoolTest2() throws Exception {
		// 创建一个 核心线程数量为5，最大数量为10,等待队列最大是3 的线程池，也就是最大容纳13个任务。
		// 默认的策略是抛出RejectedExecutionException异常，java.util.concurrent.ThreadPoolExecutor.AbortPolicy
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						System.err.println("有任务被拒绝执行了");
					}
				});
		testCommon(threadPoolExecutor);
		// 预计结果：
		// 1、 5个任务直接分配线程开始执行
		// 2、 3个任务进入等待队列
		// 3、 队列不够用，临时加开5个线程来执行任务(5秒没活干就销毁)
		// 4、 队列和线程池都满了，剩下2个任务，没资源了，被拒绝执行。
		// 5、 任务执行，5秒后，如果无任务可执行，销毁临时创建的5个线程
	}

	/**
	 * 3、 线程池信息： 核心线程数量5，最大数量5，无界队列，超出核心线程数量的线程存活时间：5秒
	 * 
	 * @throws Exception
	 */
	public void threadPoolTest3() throws Exception {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		testCommon(threadPoolExecutor);
		// 预计结果：线程池线程数量为5，超出数量的任务，进入队列中等待被执行
	}

	/**
	 * 4、 线程池信息：核心线程数量0，最大数量Integer.MAX_VALUE，SynchronousQueue队列，超出核心线程数量的线程存活时间：60秒
	 * 
	 * @throws Exception
	 */
	public void threadPoolTest4() throws Exception {
		// SynchronousQueue，实际上它不是一个真正的队列，因为它不会为队列中元素维护存储空间。与其他队列不同的是，它维护一组线程，这些线程在等待着把元素加入或移出队列。
		// 在使用SynchronousQueue作为工作队列的前提下，客户端代码向线程池提交任务时，而线程池中又没有空闲的线程能够从SynchronousQueue队列实例中取一个任务，
		// 那么相应的offer方法调用就会失败（即任务没有被存入工作队列）。
		// 此时，ThreadPoolExecutor会新建一个新的工作者线程用于对这个入队列失败的任务进行处理（假设此时线程池的大小还未达到其最大线程池大小maximumPoolSize）。
		// 和Executors.newCachedThreadPool()一样的
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		testCommon(threadPoolExecutor);
		Thread.sleep(60000L);
		System.out.println("60秒后，再看线程池中线程数量：" + threadPoolExecutor.getPoolSize());
		// 预计结果：
		// 1、 线程池线程数量为：15，超出线程池数量的任务，其他的进入队列中等待被执行
		// 2、 所有任务执行结束，60秒后，如果无任务可执行，所有线程全部被销毁，池的大小恢复为0
	}
	
	public void threadPoolTest5() {
		
	}

	public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws Exception {
		for (int i = 0; i < 15; i++) {
			final int n = i;
			threadPoolExecutor.submit(new Runnable() {
				public void run() {
					try {
						System.out.println("开始执行：" + n);
						Thread.sleep(3000);
						System.out.println("结束执行：" + n);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			System.out.println("任务提交成功" + i);
		}
		Thread.sleep(5000);
		System.out.println("当前线程池的线程数量为：" + threadPoolExecutor.getPoolSize());
		System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
		Thread.sleep(15000);
		System.out.println("当前线程池的线程数量为：" + threadPoolExecutor.getPoolSize());
		System.out.println("当前线程池等待的数量为：" + threadPoolExecutor.getQueue().size());
	}

}
