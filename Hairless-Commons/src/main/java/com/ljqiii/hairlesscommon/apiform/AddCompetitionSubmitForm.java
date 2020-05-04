package com.ljqiii.hairlesscommon.apiform;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCompetitionSubmitForm {
    int competitionId;
    int submitId;
}
