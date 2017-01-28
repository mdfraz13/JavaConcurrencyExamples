package demo10_separate_tasks;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CompletionService<String> service = new ExecutorCompletionService<>(executor);

		// ReportRequest
		ReportRequest faceRequest = new ReportRequest("Face", service);
		ReportRequest onlineRequest = new ReportRequest("Online", service);
		Thread faceThread = new Thread(faceRequest);
		Thread onlineThread = new Thread(onlineRequest);

		// ReportProcess
		ReportProcessor processor = new ReportProcessor(service);
		Thread senderThread = new Thread(processor);

		System.out.printf("Main: Starting the Threads\n");
		// 
		faceThread.start();
		onlineThread.start();
		senderThread.start();

		try {
			System.out.printf("Main: Waiting for the report generators.\n");
			//ReportGenerator
			faceThread.join();
			onlineThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.printf("Main: Shuting down the executor.\n");
		executor.shutdown();
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		processor.setEnd(true);
		System.out.printf("Main: Ends\n");
	}
	
	
	/**
	 	Main: Starting the Threads
		Main: Waiting for the report generators.
		Main: Shuting down the executor.
		Online_Report: ReportGenerator: Generating a report during 1 seconds
		Face_Report: ReportGenerator: Generating a report during 4 seconds
		ReportReceiver: Report Recived: Online: Report
		ReportReceiver: Report Recived: Face: Report
		ReportSender: End
		Main: Ends
	 */
}
