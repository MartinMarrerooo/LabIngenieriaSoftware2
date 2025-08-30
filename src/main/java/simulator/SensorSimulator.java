package com.smartgrow.simulator;

import org.eclipse.paho.client.mqttv3.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class SensorSimulator {
    public static void main(String[] args) throws Exception {
        String broker = System.getenv().getOrDefault("MQTT_BROKER_URL", "tcp://localhost:1883");
        String topic  = System.getenv().getOrDefault("MQTT_TOPIC", "smartgrow/sensors/zone1/sensorHT1");
        MqttClient client = new MqttClient(broker, "simulator", null);
        client.connect();

        Random random = new Random();
        while (true) {
            double temp = 18 + random.nextDouble() * 10; // 18°C a 28°C
            double humidity = 40 + random.nextDouble() * 20; // 40% a 60%
            String payload = String.format("{\"temperature\":%.1f,\"humidity\":%.1f}", temp, humidity);
            client.publish(topic, new MqttMessage(payload.getBytes(StandardCharsets.UTF_8)));
            System.out.println("[Simulator] Sent: " + payload);
            Thread.sleep(1000);
        }
    }
}
