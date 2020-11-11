package Threads;

/*
 * 使用synchronized和notify/wait
 */
public class Method3 {
    private volatile Integer value = null;

    synchronized public void sum(int num) {
        value = fibonacci(num);
        notifyAll();
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

    synchronized public int getResult() throws InterruptedException {
        while (value==null){
            wait();
        }
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        Method3 method3 = new Method3();
        Thread thread = new Thread(()->{
            method3.sum(36);
        });
        thread.start();
        int result = method3.getResult();
        System.out.println("计算结果："+result);
    }
}
