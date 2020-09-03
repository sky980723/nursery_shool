package com.start.springbootdemo.util;

import com.start.springbootdemo.entity.Student;
import com.start.springbootdemo.service.IStudentService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestBatchInsertThread implements Runnable {
	/** 每个线程处理的起始数据 */
	private CountDownLatch begin;
	/** 每个线程处理的结束数据 */
	private CountDownLatch end;
	/** excel中读取的首行 */
	private int sta;
	/** excel中读取的条数 */
	private int size;

	private List<Student> list;

	public TestBatchInsertThread() {
	}

	public TestBatchInsertThread(List<Student> list, CountDownLatch begin, CountDownLatch end, int sta, int size) {
		this.begin = begin;
		this.end = end;
		this.sta = sta;
		this.size = size;
		this.list = list;
	}

	@Override
	public void run() {
		try {
			if (list != null && list.size() > 0) {
				// 执行真正的处理
				// seaCustomerServiceImpl.ExcelDoing(sheet, sta, size, secondaryId);
				SpringUtils.getBean(IStudentService.class).ExcelDoing(list, sta, size);
			}
			// 执行完让线程直接进入等待
			begin.await();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 当一个线程执行完 了计数要减一不然这个线程会被一直挂起
			end.countDown();
		}
	}
}
