package com.jjh.TThreadPoolServer;

import com.jjh.thrift.demo.HelloWorldService;
import com.jjh.thrift.demo.impl.HelloWorldImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by jiajianhong on 16/9/5.
 */
public class HelloServerDemo {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloWorld TThreadPoolServer start ...");

            TProcessor processor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            // 开启端口
            TServerSocket serverSocket = new TServerSocket(SERVER_PORT);

            // 封装参数
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverSocket);

            args.processor(processor);
            // 设置参数的协议
            args.protocolFactory(new TBinaryProtocol.Factory());

            // 线程池服务模型,使用标准的阻塞式IO,预先创建一组线程处理请求
            TServer server = new TThreadPoolServer(args);
            server.serve();
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloServerDemo helloServerDemo = new HelloServerDemo();
        helloServerDemo.startServer();
    }

}
