package com.next.dto;

import com.next.model.TrainStation;
import lombok.Getter;
import lombok.Setter;

public class TrainStationDto extends TrainStation {

    @Getter
    @Setter
    private String cityName;
}
