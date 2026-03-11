package com.natwest.traffic_light_controller.service;

import com.natwest.traffic_light_controller.entity.Direction;
import com.natwest.traffic_light_controller.entity.LightState;
import com.natwest.traffic_light_controller.entity.TrafficLight;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TrafficLightService {

    private final Map<Direction, TrafficLight> lights = new ConcurrentHashMap<>();
    private boolean paused = false;

    @PostConstruct
    public void init() {
        lights.put(Direction.NORTH_SOUTH, new TrafficLight(Direction.NORTH_SOUTH, LightState.RED));
        lights.put(Direction.EAST_WEST, new TrafficLight(Direction.EAST_WEST, LightState.RED));
    }

    public synchronized void changeLight(Direction direction, LightState newState) {

        if (paused) {
            throw new IllegalStateException("System paused");
        }

        if (newState == LightState.GREEN) {
            for (TrafficLight light : lights.values()) {
                if (light.getDirection() != direction && light.getState() == LightState.GREEN) {
                    throw new IllegalStateException("Conflicting green direction");
                }
            }
        }

        lights.get(direction).setState(newState);
    }

    public Map<Direction, LightState> getState() {

        Map<Direction, LightState> state = new HashMap<>();

        lights.forEach((k, v) -> state.put(k, v.getState()));

        return state;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
}
