package com.ljqiii.hairlesscompetition.aspectj;

import com.ljqiii.hairlesscompetition.exception.UnJoinCompetition;
import com.ljqiii.hairlesscompetition.service.CompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class CompetitionPrivilegeAspect {

    @Autowired
    CompetitionService competitionService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Around("@annotation(com.ljqiii.hairlesscompetition.aspectj.annotation.CompetitionPrivilege)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //request中的竞赛id
        String competitionid = httpServletRequest.getParameter("competitionid");

        //身份认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || competitionid == null) {
            return joinPoint.proceed();
        } else {
            boolean joinedCompetition = competitionService.isJoinedCompetition(Integer.valueOf(competitionid), authentication.getName());
            if (!joinedCompetition) {
                throw new UnJoinCompetition();
            } else {
                return joinPoint.proceed();
            }
        }
    }
}
