package com.next.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainTraveller {
    private Long id;

    private String name;

    private Byte adultFlag;

    private Byte sex;

    private Short idType;

    private String idNumber;

    private String contact;

    private String address;

    private String email;


}