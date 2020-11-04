import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * 绑定8803端口
 * 使用线程池创建线程
 * @Author: th
 * @Date: 2020/10/28 20:40
 */
public class HttpServer03 {
    public static void main(String[] args) throws IOException {
        //创建一个serverSocket，绑定8803端口
        //没指定ip，默认为本地
        ServerSocket serverSocket = new ServerSocket(8803);
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        while (true) {
            try {
                //当有客户端请求时通过accept方法拿到Socket进行处理
                final Socket socket = serverSocket.accept();
                executorService.execute(()->service(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void service(Socket socket) {
        try {
            //sleep 20ms,模拟业务操作(IO)
            Thread.sleep(20);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
            //模拟输出HTTP报文头和hello
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;character=utf-8");
            printWriter.println();
            printWriter.write("hello,nio");
            printWriter.close();
            //关闭socket
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//版本2，每次请求打开一个线程
//能够更好的使用电脑上多核的优势
//RPS每秒处理的请求变大了
//问题
//1.sleep 20ms，每次请求都会停一下，再执行
//java的线程会去创建一个os真正的线程
//这样线程的使用效率比较低，所以优先考虑线程池
//改进：看版本3
