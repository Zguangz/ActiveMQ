package com.spring.activemq.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@RestController
@RequestMapping("/rest/publish")
public class ProducerResource {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    Queue queue;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH.mm.ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @GetMapping(path = "/{message}")
    public String publish(@PathVariable(name = "message") final String message) {
        jmsTemplate.convertAndSend( queue, message + " :: " + sdf.format(timestamp.getTime()));
        return "Published Successfully";
    }
}
