package szu.panda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CustomerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient; //使用原生态ribbon

    @GetMapping("index")
    public Object getIndex(){
//        return restTemplate.getForObject("http://HELLOSERVER/",String.class,"");
        //自定义ribbon
        ServiceInstance serviceInstance = loadBalancerClient.choose("hello-server"); //拿到服务实例
        String ip = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        return restTemplate.getForObject("http://" + ip + ":" + port ,String.class,"");
    }


}
