package com.appreciateme.awarding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Awarding {

    /**
     * ID of user which owned associated rewards
     */
    private String userId;

    /**
     * list of rewards that this particular user ever owned (both used and unused)
     */
    private List<OwnedReward> rewards;
}

