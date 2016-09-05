package com.jjh.TNonBlockingServer;

import com.jjh.thrift.demo.HelloWorldService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by jiajianhong on 16/9/5.
 */
public class HelloClientDemo {

    public static final String SERVER_IP = "127.0.0.1";

    public static final int SERVER_PORT = 8090;

    public static final int TIMEOUT = 3000;

    public void startClient() {
        TTransport transport = null;

        try {
            // 生成相应的传输方式
            transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));

            // 协议要和服务端一致
            TProtocol protocol = new TCompactProtocol(transport);

            // 生成客户端
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);

            transport.open();

            String result = client.sayHello("jjh");
            System.out.println("服务端返回结果为:" + result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public static void main(String[] args) {
        HelloClientDemo clientDemo = new HelloClientDemo();
        clientDemo.startClient();
    }

}
