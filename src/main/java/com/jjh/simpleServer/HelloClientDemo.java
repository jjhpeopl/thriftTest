package com.jjh.simpleServer;

import com.jjh.thrift.demo.HelloWorldService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by jiajianhong on 16/8/19.
 */
public class HelloClientDemo {

    public static final String SERVER_IP = "127.0.0.1";

    public static final int SERVER_PORT = 8090;

    public static final int timeout = 3000;

    public void startClient () {
        TTransport tTransport = null;

        try {
            tTransport = new TSocket(SERVER_IP, SERVER_PORT, timeout);

            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(tTransport);

            HelloWorldService.Client client = new HelloWorldService.Client(protocol);

            tTransport.open();
            String result = client.sayHello("jjh");
            System.out.println("Thrift client result is " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != tTransport) {
                tTransport.close();
            }
        }
    }

    public static void main(String[] args) {
        HelloClientDemo clientDemo = new HelloClientDemo();
        clientDemo.startClient();
    }

}
