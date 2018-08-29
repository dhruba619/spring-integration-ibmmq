package com.samplejmsapp.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath*:/springintegration-config.xml")
public class HelloWorldAmqApp {

    public static void main(String[] args) {

        /*
         * Uncomment below lines for SSL client
         * Also you can put these properties as VM Arguments like:
         * -Djavax.net.ssl.keyStore
         * -Djavax.net.ssl.keyStorePassword
         * -Djavax.net.ssl.trustStore
         * -Djavax.net.ssl.trustStorePassword
         */
        // System.setProperty("javax.net.ssl.keyStore", "/path/to/your/keystore.jks");
        // System.setProperty("javax.net.ssl.keyStorePassword", "your_key_store_password");
        // System.setProperty("javax.net.ssl.trustStore", "/path/to/your/truststore.jks");
        // System.setProperty("javax.net.ssl.trustStorePassword", "your_truststore_password");

        SpringApplication.run(HelloWorldAmqApp.class, args);
    }
}
