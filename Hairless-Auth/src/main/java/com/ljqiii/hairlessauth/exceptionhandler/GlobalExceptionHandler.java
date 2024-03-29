package com.ljqiii.hairlessauth.exceptionhandler;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.exception.UserException;
import com.ljqiii.hairlesscommon.vo.HairlessResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *
     * 表单验证失败处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public HairlessResponse bindExceptionHandler(BindException e) {
        log.error("表单验证错误", e);
        BindingResult bindingResult = e.getBindingResult();
        List<String> errMsgs = new ArrayList<>();

        for (ObjectError error : bindingResult.getAllErrors()) {
            errMsgs.add(error.getDefaultMessage());
        }
        return new HairlessResponse<List<String>>(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage(), errMsgs);
    }


    /**
     *
     * 空指针处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public HairlessResponse nullPointerExceptionHandler(NullPointerException e) {
        log.error("NullPointerException", e);
        return new HairlessResponse<Void>(ResultEnum.SERVER_ERROR.getCode(), ResultEnum.SERVER_ERROR.getMessage(), null);
    }


    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public HairlessResponse UserExceptionHandler(UserException e) {
        log.error("UserException", e);
        return new HairlessResponse<Void>(ResultEnum.SERVER_ERROR.getCode(), e.getMessage(), null);
    }

}
