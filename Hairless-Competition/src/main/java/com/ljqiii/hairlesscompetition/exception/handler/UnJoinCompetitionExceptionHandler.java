package com.ljqiii.hairlesscompetition.exception.handler;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import com.ljqiii.hairlesscompetition.exception.UnJoinCompetition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class UnJoinCompetitionExceptionHandler {

    @ExceptionHandler(value = UnJoinCompetition.class)
    @ResponseBody
    public HairlessResponse<Void> userExceptionHandler(UnJoinCompetition e) {
        log.error("UserException", e);
        return new HairlessResponse<>(ResultEnum.UNJOIN_COMPETITION.getCode(), ResultEnum.UNJOIN_COMPETITION.getMessage(), null);
    }
}
