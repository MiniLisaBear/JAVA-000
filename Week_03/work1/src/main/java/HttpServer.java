import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        //创建一个serverSocket，绑定8801端口
        //没指定ip，默认为本地
        ServerSocket serverSocket = new ServerSocket(8801);
        while (true) {
            try {
                //当有客户端请求时通过accept方法拿到Socket进行处理
                Socket socket = serverSocket.accept();
                service(socket);
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
//吞吐量低的原因
//1.sleep 20ms，每次请求都会停一下，再执行
//2.是一个阻塞IO，阻塞整个处理过程
//3.本质是一个单线程
//改进1:httpServer02