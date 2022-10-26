package com.appreciateme.awarding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OwnedReward {

    private String rewardId;
    private String companyName;
    private boolean isService;
    private double value;
    private String description;
    private String dateFrom;
    private String dateTo;
    private boolean used;
}
