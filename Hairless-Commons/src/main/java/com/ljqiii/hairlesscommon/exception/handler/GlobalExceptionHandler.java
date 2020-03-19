package com.ljqiii.hairlesscommon.exception.handler;

import com.ljqiii.hairlesscommon.enums.ResultEnum;
import com.ljqiii.hairlesscommon.vo.ResultVO;
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
public class GlobalExceptionHandler {


    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     *
     * 表单验证失败处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultVO bindExceptionHandler(BindException e) {
        logger.error("表单验证错误", e);
        BindingResult bindingResult = e.getBindingResult();
        List<String> errMsgs = new ArrayList<>();

        for (ObjectError error : bindingResult.getAllErrors()) {
            errMsgs.add(error.getDefaultMessage());
        }
        return new ResultVO<List<String>>(ResultEnum.PARAM_ERROR.getCode(), ResultEnum.PARAM_ERROR.getMessage(), errMsgs);
    }


    /**
     *
     * 空指针处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultVO nullPointerExceptionHandler(NullPointerException e) {
        logger.error("NullPointerException", e);
        return new ResultVO<Void>(ResultEnum.SERVER_ERROR.getCode(), ResultEnum.SERVER_ERROR.getMessage(), null);
    }

}
