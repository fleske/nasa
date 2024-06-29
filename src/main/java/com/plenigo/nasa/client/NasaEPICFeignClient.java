package com.plenigo.nasa.client;

import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.plenigo.nasa.model.epic.NasaEpicImage;
import feign.Headers;
import feign.Response;

@FeignClient(name = "PowerCloudClient", url = "${nasa.api.url}/EPIC")
@Headers({
        "Content-Type: " + MediaType.APPLICATION_JSON_VALUE,
        "Accept: " + MediaType.APPLICATION_JSON_VALUE,
})
public interface NasaEPICFeignClient {

    @GetMapping("/api/natural/date/{date}")
    List<NasaEpicImage> getNaturalImages(@PathVariable(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

    @GetMapping("/archive/natural/{year}/{month}/{day}/{format}/{filename}")
    Response downloadImage(@PathVariable int year, @PathVariable int month, @PathVariable int day, @PathVariable String format, @PathVariable String filename);
}
