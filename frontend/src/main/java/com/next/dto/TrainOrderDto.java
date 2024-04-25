package com.next.dto;

import com.next.model.TrainOrder;
import com.next.model.TrainOrderDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainOrderDto {

    private TrainOrder trainOrder;

    private List<TrainOrderDetail> trainOrderDetailList;
}
