package com.smartgrow.consumer;

import org.eclipse.paho.client.mqttv3.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws Exception {
        // Broker y topic (pueden cambiar según tu entorno)
        String broker = System.getenv().getOrDefault("MQTT_BROKER_URL", "tcp://localhost:1883");
        String topic  = System.getenv().getOrDefault("MQTT_TOPIC", "smartgrow/sensors/zone1/sensorHT1");
        String clientId = "consumer-" + Instant.now().toEpochMilli();

        System.out.println("[Consumer] Connecting to " + broker + " topic=" + topic);

        // Crear cliente MQTT
        MqttClient client = new MqttClient(broker, clientId, null);
        MqttConnectOptions opts = new MqttConnectOptions();
        opts.setAutomaticReconnect(true);
        opts.setCleanSession(true);
        client.connect(opts);

        // Suscribirse al topic
        client.subscribe(topic, (t, msg) -> {
            String payload = new String(msg.getPayload(), StandardCharsets.UTF_8);
            System.out.printf("[Consumer] %s => %s%n", t, payload);
        });

        // Mantener la aplicación viva
        while (true) {
            Thread.sleep(1000);
        }
    }
}
