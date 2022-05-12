package com.spring.kafka.mdl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleModel {
	
	private String topic;
	private String content;

}
