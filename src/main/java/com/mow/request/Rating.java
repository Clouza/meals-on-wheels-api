package com.mow.request;

import com.mow.entity.Meals;
import com.mow.entity.Riders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    Riders riders;
    double ridersRating;
    Meals meals;
    double mealsRating;
}
