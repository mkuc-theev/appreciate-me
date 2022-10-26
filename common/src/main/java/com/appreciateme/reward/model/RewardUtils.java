package com.appreciateme.reward.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RewardUtils {

    public static final ZoneId ZONE = ZoneId.of("Europe/Warsaw");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static List<RewardDTO> mapToDtoList(List<Reward> rewards) {
        return rewards.stream()
                .map(RewardUtils::mapToDto)
                .toList();
    }

    public static List<Reward> mapToRewardList(List<RewardDTO> rewardDTOs) {
        return rewardDTOs.stream()
                .map(RewardUtils::mapToReward)
                .toList();
    }

    public static RewardDTO mapToDto(Reward reward) {
        return RewardDTO.builder()
                .id(reward.getId())
                .companyName(reward.getCompanyName())
                .isService(reward.isService())
                .value(reward.getValue())
                .requiredOpinionAmount(reward.getRequiredOpinionAmount())
                .description(reward.getDescription())
                .dateFrom(mapStringDateToLong(reward.getDateFrom()))
                .dateTo(mapStringDateToLong(reward.getDateTo()))
                .availabilityDays(reward.getAvailabilityDays())
                .build();
    }

    public static Reward mapToReward(RewardDTO rewardDTO) {
        return Reward.builder()
                .id(rewardDTO.getId())
                .companyName(rewardDTO.getCompanyName())
                .isService(rewardDTO.isService())
                .value(rewardDTO.getValue())
                .requiredOpinionAmount(rewardDTO.getRequiredOpinionAmount())
                .description(rewardDTO.getDescription())
                .dateFrom(mapLongToStringDate(rewardDTO.getDateFrom()))
                .dateTo(mapLongToStringDate(rewardDTO.getDateTo()))
                .availabilityDays(rewardDTO.getAvailabilityDays())
                .build();
    }

    public static long mapStringDateToLong(String dateString) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, FORMATTER);

        return localDateTime.atZone(ZONE)
                .toInstant()
                .toEpochMilli();
    }

    public static String mapLongToStringDate(long dateTimestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateTimestamp),
                ZONE);

        return localDateTime.format(FORMATTER);
    }

    public static String setCurrentDate() {
        return LocalDateTime.ofInstant(
                        Instant.now(),
                        ZONE)
                .format(FORMATTER);
    }
}

