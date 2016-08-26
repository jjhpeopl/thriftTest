package com.jjh.simpleServer;

import com.jjh.thrift.demo.HelloWorldService;
import com.jjh.thrift.demo.impl.HelloWorldImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * Created by jiajianhong on 16/8/17.
 */
public class HelloServerDemo {

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {

            System.out.println("HelloWorld TSimpleServer start ...");

            TProcessor tProcessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            // 简单的单线程模型
            TServerSocket serverSocket = new TServerSocket(SERVER_PORT);
            TServer.Args args = new TServer.Args(serverSocket);
            args.processor(tProcessor);
            args.protocolFactory(new TBinaryProtocol.Factory());

            TServer server = new TSimpleServer(args);
            server.serve();

        } catch (Exception e) {
            System.out.println("server start error!!!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloServerDemo server = new HelloServerDemo();
        server.startServer();
    }

}
