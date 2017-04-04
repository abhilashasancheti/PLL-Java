package part2;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class sensor implements Runnable{
	protected BlockingQueue<Integer>[] queue = null;
	int value;
	public sensor(int parameter, BlockingQueue<Integer>[] queue) {
		value = parameter;
		this.queue = queue;
	}
	public static String binary(){
		 
		 String s="";
		 for(int i=0;i<8;i++){
		     int x;
		     if(Math.random() < 0.5)
		    	 x=0;
		     else 
		    	 x=1;
		     s += Integer.toString(x);
		 }
		// System.out.println(s);
		 return s;
	}

	public static Integer stringToint(String s){
		 int j=0,res=0;
		for(int i=s.length()-1;i>=0;i--)
		{
			if(s.charAt(i)=='1')
				res += Math.pow(2, j);
			j++;
		}
		//System.out.println(res);
		return res;
	}
	public void run() {
		while(true){
			String bins = binary();
			int n = stringToint(bins);
			try {
				queue[value].put(n);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class calculate implements Runnable{
	protected BlockingQueue<Integer>[] queue = null;
	public calculate(BlockingQueue<Integer>[] queue) {
		this.queue = queue;
	}

	public void run(){
		while(true){
			int flag = 1;
			if(flag == 1){
				int sum = 0; long mult = 1;
				for(int i=0;i<10;i++){
					int x;
					try {
						x = queue[i].take();
						sum += x;
						mult *= x;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(mult >= Concurrent.multt)
						mult = Concurrent.multt;
				}
				int avg = sum/10;
				if(avg>=Concurrent.avgt)
					System.out.println("state detected from (ops)");
				else
					System.out.println("state not detected from (ops)");
				if(sum>=Concurrent.addt)
					System.out.println("state detected from (ops)");
				else
					System.out.println("state not detected from (ops)");
				if(mult>=Concurrent.multt)
					System.out.println("state detected from (ops)");
				else
					System.out.println("state not detected from (ops)");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class Concurrent {	
	public static int addt = 1000, avgt = 100;
	public static long multt = 1000000;
	public static void main(String[] args){
		Concurrent app = new Concurrent();
		@SuppressWarnings("unchecked")
		BlockingQueue<Integer>[] queue = new LinkedBlockingQueue[10]; 
		for(int i=0; i<queue.length; i++){
		    queue[i]=new LinkedBlockingQueue<Integer>(); //change constructor as needed
		}
		app.runprogram(queue);
	}
	public void runprogram(BlockingQueue<Integer>[] queue){
		Thread t1 = new Thread(new sensor(0,queue));
		Thread t2 = new Thread(new sensor(1, queue));
		Thread t3 = new Thread(new sensor(2, queue));
		Thread t4 = new Thread(new sensor(3, queue));
		Thread t5 = new Thread(new sensor(4, queue));
		Thread t6 = new Thread(new sensor(5, queue));
		Thread t7 = new Thread(new sensor(6, queue));
		Thread t8 = new Thread(new sensor(7, queue));
		Thread t9 = new Thread(new sensor(8, queue));
		Thread t10 = new Thread(new sensor(9, queue));
		Thread cal = new Thread(new calculate(queue));
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t10.start();
		cal.start();
	}
}

