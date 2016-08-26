package com.jjh.thrift.demo.impl;

import com.jjh.thrift.demo.HelloWorldService;
import org.apache.thrift.TException;

/**
 * Created by jiajianhong on 16/8/17.
 */
public class HelloWorldImpl implements HelloWorldService.Iface {


    public String sayHello(String username) throws TException {
        return "hi " + username + " welcome!";
    }
}
