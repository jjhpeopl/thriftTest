package com.jjh.asyncServer;

import com.jjh.thrift.demo.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TNonblockingSocket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiajianhong on 16/9/8.
 */
public class HelloClientDemo {

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 2000;

    public void startClient(String username) {
        try {
            TAsyncClientManager tAsyncClientManager = new TAsyncClientManager();

            // 连接端口
            TNonblockingSocket socket = new TNonblockingSocket(SERVER_IP, SERVER_PORT, TIMEOUT);

            // 协议
            TCompactProtocol.Factory protocol = new TCompactProtocol.Factory();

            HelloWorldService.AsyncClient asyncClient = new HelloWorldService.AsyncClient(protocol, tAsyncClientManager, socket);

            System.out.println("Client start ...");

            CountDownLatch latch = new CountDownLatch(1);

            AsynCallback callback = new AsynCallback(latch);
            System.out.println("call method sayHello start");
            asyncClient.sayHello(username, callback);
            System.out.println("call method sayHello end");
            boolean wait = latch.await(30, TimeUnit.SECONDS);

            System.out.println("latch wait:" + wait);


        } catch (Exception e) {

        }
    }

    public class AsynCallback implements AsyncMethodCallback<HelloWorldService.AsyncClient.sayHello_call> {

        private CountDownLatch latch;

        public AsynCallback(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onComplete(HelloWorldService.AsyncClient.sayHello_call sayHello_call) {
            System.out.println("onComplete");

            try {
                Thread.sleep(3000);
                System.out.println("Async result=" + sayHello_call.getResult().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TException e) {
                e.printStackTrace();
            } finally {
                this.latch.countDown();
            }
        }

        @Override
        public void onError(Exception e) {
            System.out.println("onError :" + e.getMessage());
            latch.countDown();
        }
    }

    public static void main(String[] args) {
        HelloClientDemo helloClientDemo = new HelloClientDemo();
        helloClientDemo.startClient("jjh");
    }

}
