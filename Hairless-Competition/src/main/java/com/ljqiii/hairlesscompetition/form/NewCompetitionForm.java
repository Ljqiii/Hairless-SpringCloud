package com.ljqiii.hairlesscompetition.form;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewCompetitionForm {
    String title;
    Date startTime;
    Date endTime;
    Boolean isPublic;
    String password;
    String description;
    List<Integer> problemIds;
}
