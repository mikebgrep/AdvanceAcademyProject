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
public class StationDetachBus {

    private Long id;

    private Set<Long> busesIds;

}
