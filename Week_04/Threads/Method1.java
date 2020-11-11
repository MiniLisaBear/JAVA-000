package Threads;

/*
 * 使用循环判断值是否计算完成
 */
public class Method1 {

    public static void main(String[] args) {
        Count count = new Count();
        //启动线程运行方法
        Thread thread = new Thread(()->{
            //计算
            count.sum(36);
        });
        thread.start();
        //拿到返回值,while直到计算完成
        int result = count.getResultWhile();
        System.out.println("计算结果："+result);
        //退出
    }
}
