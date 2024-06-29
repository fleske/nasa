package com.plenigo.nasa.service;

import java.time.LocalDate;
import com.plenigo.nasa.model.NasaImageFormatEnum;

public interface ImageDownloadService {

    void downloadAndSaveImages(LocalDate date, String targetDirectory, NasaImageFormatEnum format);
}
