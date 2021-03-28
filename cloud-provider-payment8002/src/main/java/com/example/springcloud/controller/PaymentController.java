package com.example.springcloud.controller;

import com.example.springcloud.entities.CommentResult;
import com.example.springcloud.entities.Payment;
import com.example.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommentResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("*******插入结果：" + result);
        if(result > 0) {
            return new CommentResult(200, "插入数据库成功 serverPort:" + serverPort, result);
        } else {
            return new CommentResult(444, "插入数据库失败 serverPort:" + serverPort, result);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommentResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("*******查询结果：" + payment);
        if(payment != null) {
            return new CommentResult(200, "查询成功 serverPort:" + serverPort, payment);
        } else {
            return new CommentResult(444, "查询失败 serverPort:" + serverPort, null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("******** service: " + service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance serviceInstance : instances) {
            log.info(serviceInstance.getInstanceId() + "\t" + serviceInstance.getHost() + "\t" + serviceInstance.getPort() + "\t" + serviceInstance.getUri());
        }
        return discoveryClient;
    }

}
