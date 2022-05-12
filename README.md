## Spring-boot를 활용한 kafka REST API 구현
----

### Version 정보

1. Spring-boot : 2.6.7
2. java : 11
3. kafka : 3.0.0
4. Zookeeper : 3.6.3

※ 가상 머신에 총 3개의 서버로 kafka와 Zookeeper가 설치되어 있는 상태.


### Maven 

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.kafka</groupId>
  <artifactId>spring-kafka</artifactId>
</dependency>
<dependency>
  <groupId>org.projectlombok</groupId>
  <artifactId>lombok</artifactId>
  <optional>true</optional>
</dependency>
```

### Producer config

```xml
spring:
  kafka:
    producer:
      bootstrap-servers: 192.168.0.42:9092,192.168.0.39:9092,192.168.0.149:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

### Consumer config

```xml
spring:
  kafka:
    consumer:
      bootstrap-servers: 192.168.0.42:9092,192.168.0.39:9092,192.168.0.149:9092
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      group-id: spring-boot-kafka-consumer
```



### Producer 구현체
#### - Post 방식으로 body에 객체 전달 (topic명, content)

``` java
@PostMapping("/")
public String publish(@RequestBody SimpleModel simpleModel) {

  log.info("Produce Topic : {}, Content: {}", simpleModel.getTopic(), simpleModel.getContent());

  SimpleDateFormat format = new SimpleDateFormat ("[yyyy-MM-dd HH:mm:ss.SSS]");
  String contents = format.format(new Date()) + " Content: " + simpleModel.getContent();

  kafkaTemplate.send(simpleModel.getTopic(), contents);

  return "Publish Success. -> " + contents;
};
```


### Consumer 구현체
#### - KafkaListener Annotation 활용하여 메시지 소비

``` java
@KafkaListener(topics = "토픽명", containerFactory = "kafkaListenerContainerFactory")
public void subscribe(String value, ConsumerRecord data) {

  System.out.println("Message Received : " + value);
  System.out.println("ConsumerRecord : " + data.toString());
};
```
