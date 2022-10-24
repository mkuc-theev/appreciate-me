package com.appreciateme.notificationservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class NotificationDataTests {
    private static String uri;
    private final String testReceivingEmail = "recipient@example.com";
    private final String testContextEmail = "context@example.com";

    @BeforeAll
    static void setup(@Value("${wiremock.server.port}") String port) {
        uri = "http://localhost:" + port;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void correctlyFetchesUserData() throws Exception {
        NotificationData notificationData = new NotificationData(testReceivingEmail, testContextEmail, uri);
        assertAll(
                () -> assertEquals(notificationData.getReceivingEmail(), testReceivingEmail),
                () -> assertEquals(notificationData.getReceivingFirstName(), "John"),
                () -> assertEquals(notificationData.getReceivingLastName(), "Doe"),
                () -> assertEquals(notificationData.getContextFirstName(), "Will"),
                () -> assertEquals(notificationData.getContextLastName(), "Smith")
        );
    }
}
