package part2;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool; 
import java.util.concurrent.RecursiveTask;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

@SuppressWarnings("serial")
class DivideTask extends RecursiveTask <int[]> {

  int[] arrayToDivide;

  public DivideTask(int[] arrayToDivide) {
    this.arrayToDivide = arrayToDivide;
  }

  @Override
  protected int[] compute() {
    List<RecursiveTask<int[]>> forkedTasks = new ArrayList<>();

    /*
     * We divide the array till it has only 1 element. 
     * We can also custom define this value to say some 
     * 5 elements. In which case the return would be
     * Arrays.sort(arrayToDivide) instead.
     */
   

    
     if (arrayToDivide.length > 1) {
      
      List<int[]> partitionedArray = partitionArray();
      
      DivideTask task1 = new DivideTask(partitionedArray.get(0));
      DivideTask task2 = new DivideTask(partitionedArray.get(1));
      forkedTasks.add(task1);
      forkedTasks.add(task2);
      task1.fork();
      task2.fork();
      
      //Wait for results from both the tasks
      int[] array1 = task1.join();
      int[] array2 = task2.join();
      
      //Initialize a merged array
      int[] mergedArray = 
              new int[array1.length + array2.length];
      
      mergeArrays(task1.join(), task2.join(), mergedArray);

      return mergedArray;
    }
    return arrayToDivide;
  }
  
  private List<int[]> partitionArray(){
    
    int [] partition1 = Arrays.copyOfRange(arrayToDivide, 0,
              arrayToDivide.length / 2);
      
    int [] partition2 = Arrays.copyOfRange(arrayToDivide,
              arrayToDivide.length / 2,
              arrayToDivide.length);
    return Arrays.asList(partition1,partition2);
    
  }

  private void mergeArrays(
          int[] array1, 
          int[] array2, 
          int[] mergedArray) {
    
    int i = 0, j = 0, k = 0;
    
    while ((i < array1.length) && (j < array2.length)) {
    
      if (array1[i] < array2[j]) {
        mergedArray[k] = array1[i++];
      } else {
        mergedArray[k] = array2[j++];
      }
      
      k++;
    }
    
    if (i == array1.length) {
      
      for (int a = j; a < array2.length; a++) {
        mergedArray[k++] = array2[a];
      }
      
    } else {
      
      for (int a = i; a < array1.length; a++) {
        mergedArray[k++] = array1[a];
      }
      
    }
  }
}

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
                                 int[] numbers = new int[10];
    				 for(int i=0;i<10;i++){
                                        int x = sv.returnelement(i);
                                        numbers[i]=x; 
                                }
                                DivideTask task = new DivideTask(numbers);
			        ForkJoinPool forkJoinPool = new ForkJoinPool();
			        forkJoinPool.invoke(task);
                                numbers=task.join();
				for(int i=0;i<10;i++){
					int x = numbers[i];
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




public class Forkjoin {	
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

