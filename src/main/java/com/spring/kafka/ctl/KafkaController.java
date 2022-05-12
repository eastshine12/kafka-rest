package com.spring.kafka.ctl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.kafka.mdl.SimpleModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/kafka*")
public class KafkaController {
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	
	@PostMapping("/")
	public String publish(@RequestBody SimpleModel simpleModel) {
		
		log.info("Produce Topic : {}, Content: {}", simpleModel.getTopic(), simpleModel.getContent());
		
		SimpleDateFormat format = new SimpleDateFormat ("[yyyy-MM-dd HH:mm:ss.SSS]");
		String contents = format.format(new Date()) + " Content: " + simpleModel.getContent();
		
		kafkaTemplate.send(simpleModel.getTopic(), contents);
		
		return "Publish Success. -> " + contents;
	};
	
	
	
	@KafkaListener(topics = "topic2", containerFactory = "kafkaListenerContainerFactory")
	public void subscribe(String value, ConsumerRecord data) {
		
		System.out.println("Message Received : " + value);
		System.out.println("ConsumerRecord : " + data.toString());

	}
	
	
}
