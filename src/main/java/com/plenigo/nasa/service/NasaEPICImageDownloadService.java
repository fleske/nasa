package com.plenigo.nasa.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.plenigo.nasa.client.NasaClient;
import com.plenigo.nasa.model.NasaImageFormatEnum;
import com.plenigo.nasa.model.epic.NasaEpicImage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NasaEPICImageDownloadService implements ImageDownloadService {

    @Value("${nasa.api.parallel.download:true}")
    private boolean parallelDownload;

    private NasaClient nasaClient;

    public NasaEPICImageDownloadService(NasaClient nasaClient) {
        this.nasaClient = nasaClient;
    }

    @Override
    public void downloadAndSaveImages(LocalDate date, String targetDirectory, NasaImageFormatEnum format) {
        var nasaImages = nasaClient.getNasaImages(date);
        log.info("Found {} images. Starting download.", nasaImages.size());

        Stream<NasaEpicImage> imageStream;
        if (parallelDownload) {
            imageStream = nasaImages.parallelStream();
        } else {
            imageStream = nasaImages.stream();
        }

        var count = new AtomicInteger(0);
        imageStream.forEach(nasaImage -> {
            try {
                var rootPath = Paths.get(targetDirectory + "/" + nasaImage.getDate().format(DateTimeFormatter.ISO_DATE));
                Files.createDirectories(rootPath);
                var fileName = nasaImage.getImage() + "." + format.toString();
                try (InputStream in = downloadImage(nasaImage, format)) {
                    var savePath = rootPath.resolve(Paths.get(fileName));
                    Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
                    log.info("Saved image: {}", savePath);
                    count.incrementAndGet();
                }
            } catch (Exception e) {
                log.error("Failed to save image", e);
                throw new RuntimeException(e);
            }
        });
        log.info("Successfully saved images: {}/{}", count, nasaImages.size());
    }

    public InputStream downloadImage(NasaEpicImage nasaImage, NasaImageFormatEnum format) throws IOException {
        return nasaClient.downloadImage(nasaImage.getImage(), nasaImage.getDate().toLocalDate(), format);
    }
}
