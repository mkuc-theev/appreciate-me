package com.appreciateme.reward.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "rewards")
@Data
@Builder
@AllArgsConstructor
public class RewardDTO {

    @Id
    private String id;
    private String companyName;
    private boolean isService;
    private double value;
    private int requiredOpinionAmount;
    private String description;
    private long dateFrom;
    private long dateTo;
    private long availabilityDays;
}
