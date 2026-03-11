package com.natwest.traffic_light_controller.service;


import com.natwest.traffic_light_controller.entity.Direction;
import com.natwest.traffic_light_controller.entity.LightState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightServiceTest {

    private TrafficLightService service;

    @BeforeEach
    void setup() {
        service = new TrafficLightService();
        service.init();
    }

    @Test
    void shouldInitializeAllLightsToRed() {

        Map<Direction, LightState> state = service.getState();

        assertEquals(LightState.RED, state.get(Direction.NORTH_SOUTH));
        assertEquals(LightState.RED, state.get(Direction.EAST_WEST));
    }

    @Test
    void shouldAllowGreenForOneDirection() {

        service.changeLight(Direction.NORTH_SOUTH, LightState.GREEN);

        Map<Direction, LightState> state = service.getState();

        assertEquals(LightState.GREEN, state.get(Direction.NORTH_SOUTH));
    }

    @Test
    void shouldPreventConflictingGreenLights() {

        service.changeLight(Direction.NORTH_SOUTH, LightState.GREEN);

        assertThrows(IllegalStateException.class, () -> service.changeLight(Direction.EAST_WEST, LightState.GREEN));
    }

    @Test
    void shouldPauseSystem() {

        service.pause();

        assertThrows(IllegalStateException.class, () -> service.changeLight(Direction.NORTH_SOUTH, LightState.GREEN));
    }

    @Test
    void shouldResumeSystemAfterPause() {

        service.pause();
        service.resume();

        assertDoesNotThrow(() -> service.changeLight(Direction.NORTH_SOUTH, LightState.GREEN));
    }

    @Test
    void shouldChangeLightStateCorrectly() {

        service.changeLight(Direction.NORTH_SOUTH, LightState.GREEN);
        service.changeLight(Direction.NORTH_SOUTH, LightState.YELLOW);

        Map<Direction, LightState> state = service.getState();

        assertEquals(LightState.YELLOW, state.get(Direction.NORTH_SOUTH));
    }

}
