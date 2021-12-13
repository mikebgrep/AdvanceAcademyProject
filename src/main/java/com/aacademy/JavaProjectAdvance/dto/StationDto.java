package com.aacademy.JavaProjectAdvance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StationDto {

    private Long id;

    private String name;

    private Set<Long> busesIds;
}
