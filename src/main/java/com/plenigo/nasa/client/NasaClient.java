package com.plenigo.nasa.client;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plenigo.nasa.model.NasaImageFormatEnum;
import com.plenigo.nasa.model.epic.NasaEpicImage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NasaClient {

    private NasaEPICFeignClient nasaFeignClient;

    @Autowired
    public NasaClient(NasaEPICFeignClient nasaFeignClient) {
        this.nasaFeignClient = nasaFeignClient;
    }

    public List<NasaEpicImage> getNasaImages(LocalDate date) {
        log.info("Fetching NASA EPIC images for date: {}", date == null ? "latest" : date);
        return nasaFeignClient.getNaturalImages(date);
    }

    public InputStream downloadImage(String name, LocalDate date, NasaImageFormatEnum format) throws IOException {
        var fileName = name + "." + format;
        log.info("Downloading image: {}", fileName);
        var response = nasaFeignClient.downloadImage(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), format.toString(), fileName);
        return response.body().asInputStream();
    }
}
