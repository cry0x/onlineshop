package com.onlineshop.product_service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerServiceClient {

    private final EurekaClient eurekaClient;
    private final WebClient webClient;
    private InstanceInfo instanceInfo;

    @Autowired
    public CustomerServiceClient(EurekaClient eurekaClient, WebClient.Builder webClientBuilder) {
        this.eurekaClient = eurekaClient;
        this.instanceInfo = eurekaClient.getNextServerFromEureka("PRODUCT-SERVICE", false);

        this.webClient = webClientBuilder.baseUrl("http://192.168.100.174:9001").build();
    }

    public void testCustomerService() {
        this.webClient.get()
                .uri("/v1/customer/customer/")
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println(response))
                .subscribe();
    }
}
