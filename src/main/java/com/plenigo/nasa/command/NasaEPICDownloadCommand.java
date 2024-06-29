package com.plenigo.nasa.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import com.plenigo.nasa.model.NasaImageFormatEnum;
import com.plenigo.nasa.service.ImageDownloadService;

@ShellComponent
public class NasaEPICDownloadCommand {

    private ImageDownloadService imageDownloadService;

    public NasaEPICDownloadCommand(ImageDownloadService imageDownloadService) {
        this.imageDownloadService = imageDownloadService;
    }

    @ShellMethod(key = "download", value = "Download images from NASA EPIC")
    public String download(@ShellOption(value = "date", defaultValue = "") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                           @ShellOption(value = "targetDirectory") String targetDirectory) {
        imageDownloadService.downloadAndSaveImages(date, targetDirectory, NasaImageFormatEnum.PNG);
        return String.format("Downloaded images for date: %s to directory: %s", date == null ? "latest" : date, targetDirectory);
    }
}
