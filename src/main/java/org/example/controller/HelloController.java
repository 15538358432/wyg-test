
package org.example.controller;

import org.example.service.HelloService;
import org.example.service.impl.HelloServiceImpl;

public class HelloController {
    private final HelloService helloService;

    public HelloController() {
        this.helloService = new HelloServiceImpl();
    }

    public String handleRequest() {
        return helloService.sayHello();
    }
}