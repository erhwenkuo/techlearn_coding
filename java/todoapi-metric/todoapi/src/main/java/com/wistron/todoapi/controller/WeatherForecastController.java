package com.wistron.todoapi.controller;

import com.wistron.todoapi.model.WeatherForecast;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Tag(name="WeatherForecast")
@RestController
public class WeatherForecastController {
    private static String[] summaries = {"Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"};

    public WeatherForecastController() {
    }

    /**
     * HTTP GET ALL
     */
    @RequestMapping(value = "/WeatherForecast", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getWeatherForecast() {
        Date today = new Date();
        Random random = new Random();
        try {
            List<WeatherForecast> weatherForecasts = new ArrayList<>();
            for (int i = 1; i <=5; i++) {
                Date forecastDate = new Date(today.getTime() + i * (1000 * 60 * 60 * 24));
                int temperaturC = random.nextInt(75)-20;
                String summary = summaries[random.nextInt(10)];
                WeatherForecast weatherForecast = new WeatherForecast(forecastDate, temperaturC, summary);
                weatherForecasts.add(weatherForecast);
            }
            return new ResponseEntity<>(weatherForecasts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }
}
