package com.next.dto;

import com.next.model.TrainOrder;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainOrderExtDto {

    private TrainOrder trainOrder;

    private String fromStationName;

    private String toStationName;

    private String seatInfo;

    private boolean showPay;

    private boolean showCancel;
}
