package com.natwest.traffic_light_controller.controller;

import com.natwest.traffic_light_controller.entity.Direction;
import com.natwest.traffic_light_controller.entity.LightState;
import com.natwest.traffic_light_controller.service.TrafficLightService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/traffic")
public class TrafficLightController {

    private final TrafficLightService service;

    public TrafficLightController(TrafficLightService service) {
        this.service = service;
    }

    @PostMapping("/change")
    public void change(@RequestParam Direction direction, @RequestParam LightState state) {

        service.changeLight(direction, state);
    }

    @GetMapping("/state")
    public Map<Direction, LightState> state() {
        return service.getState();
    }

    @PostMapping("/pause")
    public void pause() {
        service.pause();
    }

    @PostMapping("/resume")
    public void resume() {
        service.resume();
    }
}
