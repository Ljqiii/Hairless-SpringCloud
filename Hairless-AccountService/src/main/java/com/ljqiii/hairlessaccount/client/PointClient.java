package com.ljqiii.hairlessaccount.client;

import com.ljqiii.hairlesscommon.domain.Point;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "point-service", fallback = PointClient.PointServiceFallback.class)
public interface PointClient {

    @PostMapping("/addpoint")
    void addPoint(@RequestBody Point point);

    public static class PointServiceFallback {

    }
}
