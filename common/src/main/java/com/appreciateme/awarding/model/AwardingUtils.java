package com.appreciateme.awarding.model;

import com.appreciateme.reward.model.Reward;
import com.appreciateme.reward.model.RewardUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AwardingUtils {

    public static final ZoneId ZONE = ZoneId.of("Europe/Warsaw");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * Map list of Awardings into list of AwardingDTOs
     * @param awardings     list of Awardings
     * @return              same list of AwardingDTOs
     */
    public static List<AwardingDTO> mapToDtoList(List<Awarding> awardings) {
        return awardings.stream()
                .map(AwardingUtils::mapToDto)
                .toList();
    }

    /**
     * Map list of AwardingDTOs into list of Awardings
     * @param awardingDTOs  list of AwardingDTOs
     * @return              same list of Awardings
     */
    public static List<Awarding> mapToAwardingList(List<AwardingDTO> awardingDTOs) {
        return awardingDTOs.stream()
                .map(AwardingUtils::mapToAwarding)
                .toList();
    }

    /**
     * Map specified Awarding object into AwardingDTO object
     * @param awarding      Awarding object
     * @return              AwardingDTO object
     */
    public static AwardingDTO mapToDto(Awarding awarding) {
        return AwardingDTO.builder()
                .userId(awarding.getUserId())
                .rewards(awarding.getRewards())
                .build();
    }

    /**
     * Map specified AwardingDTO object into Awarding object
     * @param awardingDTO   AwardingDTO object
     * @return              Awarding object
     */
    public static Awarding mapToAwarding(AwardingDTO awardingDTO) {
        return Awarding.builder()
                .userId(awardingDTO.getUserId())
                .rewards(awardingDTO.getRewards())
                .build();
    }

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
                .used(false)
                .build();
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

    /**
     * Get future date
     * @param daysOffset    amount of days to add
     * @return              future date (current date + provided days amount)
     */
    public static String getFutureDate(long daysOffset) {
        return LocalDateTime.ofInstant(
                        Instant.now(),
                        ZONE)
                .plusDays(daysOffset)
                .format(FORMATTER);
    }

    public static OwnedReward getOwnedReward(Reward reward) {
        OwnedReward ownedReward = mapToOwnedReward(reward);
        ownedReward.setDateFrom(AwardingUtils.getCurrentDate());
        ownedReward.setDateTo(AwardingUtils.getFutureDate(reward.getAvailabilityDays()));

        return ownedReward;
    }


    public static boolean isAvailable(String dateFrom, String dateTo) {
        long currentDate = RewardUtils.mapStringDateToLong(getCurrentDate());
        long rewardDateFrom = RewardUtils.mapStringDateToLong(dateFrom);
        long rewardDateTo = RewardUtils.mapStringDateToLong(dateTo);

        return rewardDateFrom <= currentDate && currentDate <= rewardDateTo;
    }
}

