package Threads;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Lock Condition
 */
public class Method4 {
    private volatile Integer value = null;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void sum(int num) {
        lock.lock();
        try {
            value = fibonacci(num);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
    public int fibonacci(int num) {
        if(num<=2) return num;
        int a = 1;
        int b = 1;
        int result = num;
        for (int i=3;i<=num;i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    public int getResult() throws InterruptedException {
       lock.lock();
       try {
           while (value==null) {
               condition.await();
           }
       } finally {
           lock.unlock();
       }
       return this.value;
    }

    public static void main(String[] args) throws InterruptedException {
        Method4 method4 = new Method4();
        Thread thread = new Thread(()->{
            method4.sum(36);
        });
        thread.start();
        int result = method4.getResult();
        System.out.println("计算结果："+result);
    }
}
