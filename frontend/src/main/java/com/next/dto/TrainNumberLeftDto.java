package com.next.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TrainNumberLeftDto {

    private int id;

    private String number;

    private long leftCount;
}
