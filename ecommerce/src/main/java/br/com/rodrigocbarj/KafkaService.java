package br.com.rodrigocbarj;

import br.com.rodrigocbarj.util.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    KafkaService(String groupName, String topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
        this(groupName, parse, type, properties);
        consumer.subscribe(Collections.singleton(topic));
    }

    KafkaService(String groupName, Pattern topic, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
        this(groupName, parse, type, properties);
        consumer.subscribe(topic);
    }

    private KafkaService(String groupName, ConsumerFunction parse, Class<T> type, Map<String, String> properties) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getproperties(groupName, type, properties));
    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(5000));

            if (!records.isEmpty()) {
                for (var record : records) parse.consume(record);
            }
        }
    }

    private Properties getproperties(String groupName, Class<T> type, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupName);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        properties.putAll(overrideProperties);
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
