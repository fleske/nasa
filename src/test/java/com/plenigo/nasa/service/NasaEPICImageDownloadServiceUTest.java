package com.plenigo.nasa.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.FileSystemUtils;
import com.plenigo.nasa.model.NasaImageFormatEnum;

@SpringBootTest
@TestPropertySource("/test.properties")
public class NasaEPICImageDownloadServiceUTest {

    private static final String TARGET_DIR = "temp";

    @Autowired
    private ImageDownloadService imageDownloadService;

    @Test
    public void testDownloadImageWithDate() {
        var date = LocalDate.of(2015, 10, 31);
        imageDownloadService.downloadAndSaveImages(date, TARGET_DIR, NasaImageFormatEnum.PNG);

        var rootPath = Paths.get(TARGET_DIR + "/" + date.format(DateTimeFormatter.ISO_DATE));

        assertThat(rootPath.toFile().exists()).isTrue();
        assertThat(rootPath.toFile().isDirectory()).isTrue();
        assertThat(rootPath.toFile().listFiles().length).isEqualTo(12);
    }

    @Test
    public void testDownloadImageNoDate() {
        imageDownloadService.downloadAndSaveImages(null, TARGET_DIR, NasaImageFormatEnum.PNG);

        var rootPath = Paths.get(TARGET_DIR);

        assertThat(rootPath.toFile().exists()).isTrue();
        assertThat(rootPath.toFile().isDirectory()).isTrue();
        assertThat(rootPath.toFile().listFiles().length).isEqualTo(1);
    }

    @AfterEach
    public void cleanUp() throws IOException {
        FileSystemUtils.deleteRecursively(Paths.get(TARGET_DIR));
    }
}
