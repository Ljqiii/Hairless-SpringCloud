package com.ljqiii.hairlesscompetition.form;

import lombok.Data;

import java.util.Date;

@Data
public class NewCompetitionForm {

    int id;
    String title;
    String createUsername;
    Date startTime;
    Date endTime;
    Boolean isPublic;
    String description;
    boolean isDel;
}
