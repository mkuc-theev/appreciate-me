package com.appreciateme.awarding.model;

import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OwnedRewardUtils {

    public static final ZoneId ZONE = ZoneId.of("Europe/Warsaw");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * Map specified Reward object into OwnedReward object
     * @param reward        Reward object
     * @return              OwnedReward object
     */
    public static OwnedReward mapToOwnedReward(Reward reward) {
        return OwnedReward.builder()
                .rewardId(reward.getId())
                .companyName(reward.getCompanyName())
                .isService(reward.isService())
                .value(reward.getValue())
                .description(reward.getDescription())
                .dateFrom(reward.getDateFrom())
                .dateTo(reward.getDateTo())
                .used(false)
                .build();
    }

    public static OwnedReward mapToOwnedReward(OwnedRewardDTO ownedRewardDTO) {
        return OwnedReward.builder()
                .rewardId(ownedRewardDTO.getRewardId())
                .companyName(ownedRewardDTO.getCompanyName())
                .isService(ownedRewardDTO.isService())
                .value(ownedRewardDTO.getValue())
                .description(ownedRewardDTO.getDescription())
                .dateFrom(
                        mapLongToStringDate(ownedRewardDTO.getDateFrom()))
                .dateTo(
                        mapLongToStringDate(ownedRewardDTO.getDateTo()))
                .used(ownedRewardDTO.isUsed())
                .build();
    }

    public static OwnedRewardDTO mapToDto(OwnedReward ownedReward) {
        return OwnedRewardDTO.builder()
                .rewardId(ownedReward.getRewardId())
                .companyName(ownedReward.getCompanyName())
                .isService(ownedReward.isService())
                .value(ownedReward.getValue())
                .description(ownedReward.getDescription())
                .dateFrom(mapStringDateToLong(
                        ownedReward.getDateFrom()))
                .dateTo(mapStringDateToLong(
                        ownedReward.getDateTo()))
                .used(ownedReward.isUsed())
                .build();
    }

    public static List<OwnedReward> mapToOwnedRewardList(List<OwnedRewardDTO> ownedRewardDTOs) {
        return ownedRewardDTOs.stream()
                .map(OwnedRewardUtils::mapToOwnedReward)
                .toList();
    }

    public static List<OwnedRewardDTO> mapToDtoList(List<OwnedReward> ownedRewards) {
        return ownedRewards.stream()
                .map(OwnedRewardUtils::mapToDto)
                .toList();
    }

    public static OwnedReward getOwnedReward(Reward reward) {
        OwnedReward ownedReward = mapToOwnedReward(reward);
        ownedReward.setDateFrom(AwardingUtils.getCurrentDate());
        ownedReward.setDateTo(AwardingUtils.getFutureDate(reward.getAvailabilityDays()));

        return ownedReward;
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
     * Get current date in correct form as String
     * @return      date as String in format 'yyyy-MM-dd HH:mm:ss'
     */
    public static String getCurrentDate() {
        return LocalDateTime.ofInstant(
                        Instant.now(),
                        ZONE)
                .format(FORMATTER);
    }

    public static boolean isAvailable(String dateFrom, String dateTo) {
        long currentDate = RewardUtils.mapStringDateToLong(getCurrentDate());
        long rewardDateFrom = RewardUtils.mapStringDateToLong(dateFrom);
        long rewardDateTo = RewardUtils.mapStringDateToLong(dateTo);

        return rewardDateFrom <= currentDate && currentDate <= rewardDateTo;
    }

    public static boolean isAvailable(long dateFrom, long dateTo) {
        long currentDate = mapStringDateToLong(getCurrentDate());

        return dateFrom <= currentDate && currentDate <= dateTo;
    }
}
