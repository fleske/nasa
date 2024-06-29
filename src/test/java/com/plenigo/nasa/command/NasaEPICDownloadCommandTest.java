package com.plenigo.nasa.command;


import static org.awaitility.Awaitility.await;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.autoconfigure.AutoConfigureShell;
import org.springframework.shell.test.autoconfigure.AutoConfigureShellTestClient;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureShell
@AutoConfigureShellTestClient
@TestPropertySource("/test.properties")
public class NasaEPICDownloadCommandTest {

    @Autowired
    ShellTestClient client;

    @Test
    void downloadCommandExistTest() {
        ShellTestClient.NonInteractiveShellSession session = client
                .nonInterative("help")
                .run();

        await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
            ShellAssertions.assertThat(session.screen()).containsText("Download images from NASA EPIC");
        });
    }
}
