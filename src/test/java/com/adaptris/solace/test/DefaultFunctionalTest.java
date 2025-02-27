package com.adaptris.solace.test;

import com.adaptris.testing.DockerComposeFunctionalTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.io.File;
import java.net.InetSocketAddress;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class DefaultFunctionalTest extends DockerComposeFunctionalTest {
    protected static String INTERLOK_SERVICE_NAME = "interlok-1";
    protected static String SOLACE_SERVICE_NAME = "solace-1";
    protected static int INTERLOK_PORT = 8080;
    protected static int SOLACE_PORT = 8080;
    protected static WaitStrategy defaultWaitStrategy = Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30));

    protected ComposeContainer setupContainers() {
        return new ComposeContainer(new File("docker-compose.yaml"))
                .withExposedService(INTERLOK_SERVICE_NAME, INTERLOK_PORT, defaultWaitStrategy)
                .withExposedService(SOLACE_SERVICE_NAME, 55555, defaultWaitStrategy)
                .withExposedService(SOLACE_SERVICE_NAME, SOLACE_PORT, defaultWaitStrategy);
    }

    protected String getSolaceEndpoint(String path) {
        InetSocketAddress address = getHostAddressForService(SOLACE_SERVICE_NAME, SOLACE_PORT);
        if (!path.startsWith("/")) path = "/" + path;
        return "http://" + address.getHostString() + ":" + address.getPort() + path;
    }



    @Test
    public void test_startup() throws Exception{
        given().auth().basic("admin", "admin")
                .get(getSolaceEndpoint("/SEMP/v2/config/about"))
                .then().body("meta.responseCode", equalTo(200));
    }


}
