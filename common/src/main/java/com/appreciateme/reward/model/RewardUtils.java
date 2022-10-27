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

    /**
     * Map list of Rewards into list of RewardDTOs
     * @param rewards   list of Rewards
     * @return          same list of RewardDTOs
     */
    public static List<RewardDTO> mapToDtoList(List<Reward> rewards) {
        return rewards.stream()
                .map(RewardUtils::mapToDto)
                .toList();
    }

    /**
     * Map list of RewardDTOs into list of Reward
     * @param rewardDTOs   list of RewardDTOs
     * @return              list of Rewards
     */
    public static List<Reward> mapToRewardList(List<RewardDTO> rewardDTOs) {
        return rewardDTOs.stream()
                .map(RewardUtils::mapToReward)
                .toList();
    }

    /**
     * Map specified Reward object into RewardDTO object
     * @param reward    Reward object
     * @return          RewardDTO object
     */
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

    /**
     * Map specified RewardDTO object into Reward object
     * @param rewardDTO     RewardDTO object
     * @return              Reward object
     */
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

    /**
     * Map date as a string to timestamp representation as long
     * @param dateString    string date of statically defined format
     * @return              timestamp representation as long
     */
    public static long mapStringDateToLong(String dateString) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, FORMATTER);

        return localDateTime.atZone(ZONE)
                .toInstant()
                .toEpochMilli();
    }

    /**
     * Map date as long timestamp to string representation of statically defined format
     * @param dateTimestamp timestamp representation as long
     * @return              string date of statically defined format
     */
    public static String mapLongToStringDate(long dateTimestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateTimestamp),
                ZONE);

        return localDateTime.format(FORMATTER);
    }

    /**
     * Get current date in correct format as String
     */
    public static String getCurrentDate() {
        return LocalDateTime.ofInstant(
                        Instant.now(),
                        ZONE)
                .format(FORMATTER);
    }
}

