package com.next.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainUser {
    private Long id;

    private String name;

    private String password;

    private String telephone;

    private String mail;

    private Integer status;


}