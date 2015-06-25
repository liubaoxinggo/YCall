package com.fhit.ycall.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	private ExecutorService service;
	
	private ThreadPoolManager(){
//		int num = Runtime.getRuntime().availableProcessors();// 
//		service = Executors.newFixedThreadPool(num*2);
		service = Executors.newCachedThreadPool();//创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们
	}
	
	private static ThreadPoolManager manager;
	
	
	public static ThreadPoolManager getInstance(){
		if(manager==null){
			synchronized (ThreadPoolManager.class) {
				if(manager == null){
					manager= new ThreadPoolManager();
				} 
			}
		}
		return manager;
	}
	
	public void addTask(Runnable runnable){
		service.submit(runnable);
	}
	public void removeAllTask(){
//		service.shutdown();// 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用
//		service.shutdownNow();//试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表。
	}
}
