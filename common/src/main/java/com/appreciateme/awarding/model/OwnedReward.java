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

    /**
     * ID of reward
     */
    private String rewardId;

    /**
     * Name of company that is responsible for this reward
     */
    private String companyName;

    /**
     * Flag that inform user if this reward is a service or an item
     */
    private boolean isService;

    /**
     * Value of discount that user gets
     */
    private double value;

    /**
     * Short description of reward details
     */
    private String description;

    /**
     * Date when user claimed particular reward
     */
    private String dateFrom;

    /**
     * Date when reward won't be available anymore (user has to use it before that date)
     */
    private String dateTo;

    /**
     * Flag to mark if user already used that reward
     */
    private boolean used;
}
