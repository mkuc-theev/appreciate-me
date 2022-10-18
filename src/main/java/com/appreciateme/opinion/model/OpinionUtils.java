package com.appreciateme.opinion.model;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class OpinionUtils {

    public static final ZoneId ZONE = ZoneId.of("Europe/Warsaw");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * Mapping list of Opinions into list of OpinionDTOs
     * @param opinions  List of Opinions
     * @return          List of OpinionDTOs
     */
    public static List<OpinionDTO> mapToDtoList(List<Opinion> opinions) {
        return opinions.stream()
                .map(OpinionUtils::mapToDto)
                .toList();
    }

    /**
     * Mapping list of OpinionDTOs into list of Opinions
     * @param opinionDTOs   list of OpinionDTOs
     * @return              list of Opinions
     */
    public static List<Opinion> mapToOpinionList(List<OpinionDTO> opinionDTOs) {
        return opinionDTOs.stream()
                .map(OpinionUtils::mapToOpinion)
                .toList();
    }

    /**
     * Mapping specified Opinion object into OpinionDTO object
     * @param opinion   Opinion object
     * @return          OpinionDTO object
     */
    public static OpinionDTO mapToDto(Opinion opinion) {
        return OpinionDTO.builder()
                .id(opinion.getId())
                .opinionUserID(opinion.getOpinionUserID())
                .reviewedUserID(opinion.getReviewedUserID())
                .opinionMessage(opinion.getOpinionMessage())
                .date(mapStringDateToLong(opinion.getDate()))
                .build();
    }

    /**
     * Mapping specified OpinionDTO object into Opinion object
     * @param opinionDTO    OpinionDTO object
     * @return              Opinion object
     */
    public static Opinion mapToOpinion(OpinionDTO opinionDTO) {
        return Opinion.builder()
                .id(opinionDTO.getId())
                .opinionUserID(opinionDTO.getOpinionUserID())
                .reviewedUserID(opinionDTO.getReviewedUserID())
                .opinionMessage(opinionDTO.getOpinionMessage())
                .date(mapLongToStringDate(opinionDTO.getDate()))
                .build();
    }

    /**
     * Mapping date as a string to timestamp representation as long
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
     * Mapping date as long timestamp to string representation of statically defined format
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
     * Static method to set a date of provided opinion to current time
     * @param opinion       opinion which date should be set to current time
     */
    public static void setCurrentDate(Opinion opinion) {
        String date = LocalDateTime.ofInstant(
                Instant.now(),
                ZONE)
                .format(FORMATTER);

        opinion.setDate(date);
    }

}
