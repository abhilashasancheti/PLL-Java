package part1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class sensor implements Runnable{
	int value;
	public sensor(int parameter) {
		value = parameter;
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
			sensorvalues sv = new sensorvalues();
			sv.updatequeue(value, n);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class calculate implements Runnable{
	public void run(){
		while(true){
			sensorvalues sv = new sensorvalues();
			int flag = 1;
			for(int i=0;i<10;i++){
				if(sensorvalues.queues[i].isEmpty() == true)
					flag = 0;
			}
			if(flag == 1){
				int sum = 0; long mult = 1;
				for(int i=0;i<10;i++){
					int x = sv.returnelement(i);
					sum += x;
					mult *= x;
					if(mult >= Synchronized.multt)
						mult = Synchronized.multt;
				}
				int avg = sum/10;
				if(avg>=Synchronized.avgt)
					System.out.println("state detected from (ops)");
				else
					System.out.println("state not detected from (ops)");
				if(sum>=Synchronized.addt)
					System.out.println("state detected from (ops)");
				else
					System.out.println("state not detected from (ops)");
				if(mult>=Synchronized.multt)
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

class sensorvalues{
	@SuppressWarnings("unchecked")
	public static Queue<Integer>[] queues = new Queue[10]; 
	public synchronized void updatequeue(int i, int value){
		@SuppressWarnings("unused")
		int x = accessqueue(i,value,0);
	}
	public synchronized int returnelement(int i){
		int x = accessqueue(i,0,1);
		return x;
	}
	
	public synchronized int accessqueue(int i, int value, int flag){
		int x=0;
		if(flag == 1)
			x = queues[i].remove();
		else
			queues[i].add(value);
		return x;
	}
}

public class Synchronized {	
	public static int addt = 1000, avgt = 100;
	public static long multt = 1000000;
	public static void main(String[] args){
		Synchronized app = new Synchronized();
		for (int i = 0; i < 10; i++) {
	        sensorvalues.queues[i] = new LinkedList<Integer>();
	    }
		app.runprogram();
	}
	public void runprogram(){
		Thread t1 = new Thread(new sensor(0));
		Thread t2 = new Thread(new sensor(1));
		Thread t3 = new Thread(new sensor(2));
		Thread t4 = new Thread(new sensor(3));
		Thread t5 = new Thread(new sensor(4));
		Thread t6 = new Thread(new sensor(5));
		Thread t7 = new Thread(new sensor(6));
		Thread t8 = new Thread(new sensor(7));
		Thread t9 = new Thread(new sensor(8));
		Thread t10 = new Thread(new sensor(9));
		Thread cal = new Thread(new calculate());
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

