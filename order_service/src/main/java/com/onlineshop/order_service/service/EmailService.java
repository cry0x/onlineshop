package com.onlineshop.order_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendStatusUpdateEmail(String emailAddress, long orderId, String orderStatus){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("spangelow@gmail.com");
        simpleMailMessage.setTo(emailAddress);
        simpleMailMessage.setSubject("We updated your order status (Order id: " + orderId + ")");
        simpleMailMessage.setText(String.format(
                "Dear Customer, \n\n" +
                "Your order with the order id %d has been updated. \n The order status is now: %s \n\n " +
                "This E-Mail has been automatically generated - Please do not respond to this E-Mail! \n\n " +
                "________________________________________________\n\n" +
                "Online-Shop for Microservices in industrial Applications \n\nGroup 3", orderId, orderStatus)); // by Jens Krämer, Nico Welsch and Simon Spang ?
                                                                                                            // Otional TODO: Customer name adden in Anrede?
        javaMailSender.send(simpleMailMessage);
    }

}
