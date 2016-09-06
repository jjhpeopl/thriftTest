package com.jjh.THsHaServer;

import com.jjh.thrift.demo.HelloWorldService;
import com.jjh.thrift.demo.impl.HelloWorldImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Created by jiajianhong on 16/9/6.
 * 半同步半异步的服务端模型
 */
public class HelloServerDemo {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {

            System.out.println("HelloWorld THsHaServer start...");

            TProcessor processor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            // 打开端口
            TNonblockingServerSocket socket = new TNonblockingServerSocket(SERVER_PORT);

            // 封装参数
            THsHaServer.Args args = new THsHaServer.Args(socket);
            args.processor(processor);
            args.transportFactory(new TFramedTransport.Factory());
            args.protocolFactory(new TBinaryProtocol.Factory());

            // 半同步半异步的服务模型
            THsHaServer server = new THsHaServer(args);
            server.serve();
        } catch (Exception e) {
            System.out.println("THsHaServer start error!!!");
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        HelloServerDemo helloServerDemo = new HelloServerDemo();
        helloServerDemo.startServer();
    }

}
