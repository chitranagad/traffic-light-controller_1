package com.natwest.traffic_light_controller.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrafficLight {

    private Direction direction;
    private LightState state;
}
