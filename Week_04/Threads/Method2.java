package Threads;

/*
 * 使用join
 * 等待计算线程执行完成
 */
public class Method2 {

    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        //启动线程执行计算
        Thread thread = new Thread(()->{
            count.sum(36);
        });
        thread.start();
        //main线程等待thread执行完成
        thread.join();
        int result = count.getResult();
        System.out.println("计算结果："+result);
    }
}
