package com.appreciateme.awarding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OwnedRewardDTO {

    private String rewardId;
    private String companyName;
    private boolean isService;
    private double value;
    private String description;
    private long dateFrom;
    private long dateTo;
    private boolean used;
}
