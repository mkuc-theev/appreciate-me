package com.appreciateme.opinion.dto;

import com.appreciateme.opinion.model.Opinion;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Component
public class OpinionMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateFormat formatter;

    static {
        formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+2"));
    }

    /**
     * Mapping list of Opinions into list of OpinionDTOs
     * @param opinions  List of Opinions
     * @return          List of OpinionDTOs
     */
    public static List<OpinionDTO> toDtoList(List<Opinion> opinions) {
        return opinions.stream()
                .map(OpinionMapper::toDto)
                .toList();
    }

    /**
     * Mapping list of OpinionDTOs into list of Opinions
     * @param opinionDTOs   list of OpinionDTOs
     * @return              list of Opinions
     */
    public static List<Opinion> toOpinionList(List<OpinionDTO> opinionDTOs) {
        return opinionDTOs.stream()
                .map(OpinionMapper::toOpinion)
                .toList();
    }

    /**
     * Mapping specified Opinion object into OpinionDTO object
     * @param opinion   Opinion object
     * @return          OpinionDTO object
     */
    public static OpinionDTO toDto(Opinion opinion) {
        return OpinionDTO.builder()
                .id(opinion.getId())
                .opinionUserID(opinion.getOpinionUserID())
                .reviewedUserID(opinion.getReviewedUserID())
                .predefinedMessageID(opinion.getPredefinedMessageID())
                .opinionMessage(opinion.getOpinionMessage())
                .date(mapStringDateToLong(opinion.getDate()))
                .build();
    }

    /**
     * Mapping specified OpinionDTO object into Opinion object
     * @param opinionDTO    OpinionDTO object
     * @return              Opinion object
     */
    public static Opinion toOpinion(OpinionDTO opinionDTO) {
        return Opinion.builder()
                .id(opinionDTO.getId())
                .opinionUserID(opinionDTO.getOpinionUserID())
                .reviewedUserID(opinionDTO.getReviewedUserID())
                .predefinedMessageID(opinionDTO.getPredefinedMessageID())
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
        try {
            Date date = formatter.parse(dateString);

            return date.getTime();
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        return 0L;
    }

    /**
     * Mapping date as long timestamp to string representation of statically defined format
     * @param dateTimestamp timestamp representation as long
     * @return              string date of statically defined format
     */
    public static String mapLongToStringDate(long dateTimestamp) {
        Date date = new Date(dateTimestamp);

        return formatter.format(date);
    }

}
