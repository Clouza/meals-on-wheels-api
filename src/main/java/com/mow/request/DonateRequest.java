package com.mow.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonateRequest {

    private String name;
    private String email;
    private String comment;
    private double total;

}
