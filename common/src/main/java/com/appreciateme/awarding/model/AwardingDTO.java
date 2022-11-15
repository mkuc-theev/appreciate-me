package com.appreciateme.awarding.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "awarding")
@Data
@Builder
@AllArgsConstructor
public class AwardingDTO {

    @Id
    private String userId;
    private List<OwnedRewardDTO> rewards;

}

