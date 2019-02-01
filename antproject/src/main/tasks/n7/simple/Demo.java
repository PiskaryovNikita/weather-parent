package main.tasks.n7.simple;

import interfaces.task7.simple.NamePrinterRunnable;
import interfaces.task7.simple.NamePrinterThread;

public final class Demo {

	public static void main(String[] args) {
		NamePrinterRunnable npr = new NamePrinterRunnableImpl();
		npr.setPrintName("Su Yun");
		npr.setCount(3);
		npr.setInterval(200);
		npr.setStream(System.out);
		NamePrinterThread nprext = new NamePrinterThreadExt();
		nprext.setPrintName("Nikita");
		nprext.setCount(5);
		nprext.setInterval(100);
		nprext.setStream(System.out);
		Thread thread = new Thread(npr);
		thread.start();
		nprext.start();
		try {
			thread.join();
			nprext.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
