package test.java.util.concurrent;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
public class ExecutorService_submit {
		
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
		
		Task task = new Task();
		String result = "success";
		System.out.println("start task   at : " + new Date());
		Future<String> future = executor.submit(task, result);

		executor.shutdown();
		while (!future.isDone()) {
		}
		
		System.out.println("end   task   at : " + new Date());
		System.out.println("result is : " + future.get());
		
		/*
		 	start task   at : Tue Jun 18 16:28:22 CST 2013
			task running at : Tue Jun 18 16:28:22 CST 2013
			task running at : Tue Jun 18 16:28:23 CST 2013
			task running at : Tue Jun 18 16:28:24 CST 2013
			task running at : Tue Jun 18 16:28:25 CST 2013
			task running at : Tue Jun 18 16:28:26 CST 2013
			end   task   at : Tue Jun 18 16:28:27 CST 2013
			result is : success
		 */
	}

	static class Task implements Runnable {

		@Override
		public void run() {
			for(int i = 0; i < 5; i++) {
				System.out.println("task running at : " + new Date());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
