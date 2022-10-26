package com.appreciateme.awarding.model;

import com.appreciateme.reward.model.Reward;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AwardingUtils {

    public static final ZoneId ZONE = ZoneId.of("Europe/Warsaw");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static List<AwardingDTO> mapToDtoList(List<Awarding> awardings) {
        return awardings.stream()
                .map(AwardingUtils::mapToDto)
                .toList();
    }

    public static List<Awarding> mapToAwardingList(List<AwardingDTO> awardingDTOs) {
        return awardingDTOs.stream()
                .map(AwardingUtils::mapToAwarding)
                .toList();
    }

    public static AwardingDTO mapToDto(Awarding awarding) {
        return AwardingDTO.builder()
                .userId(awarding.getUserId())
                .rewards(awarding.getRewards())
                .build();
    }

    public static Awarding mapToAwarding(AwardingDTO awardingDTO) {
        return Awarding.builder()
                .userId(awardingDTO.getUserId())
                .rewards(awardingDTO.getRewards())
                .build();
    }

    public static OwnedReward mapToOwnedReward(Reward reward) {
        return OwnedReward.builder()
                .rewardId(reward.getId())
                .companyName(reward.getCompanyName())
                .isService(reward.isService())
                .value(reward.getValue())
                .description(reward.getDescription())
                .used(false)
                .build();
    }

    public static String getCurrentDate() {
        return LocalDateTime.ofInstant(
                        Instant.now(),
                        ZONE)
                .format(FORMATTER);
    }

    public static String getFutureDate(long daysOffset) {
        return LocalDateTime.ofInstant(
                        Instant.now(),
                        ZONE)
                .plusDays(daysOffset)
                .format(FORMATTER);
    }
}

