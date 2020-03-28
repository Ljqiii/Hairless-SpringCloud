package com.ljqiii;

import com.ljqiii.hairlesscommon.domain.Point;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class HairlessAccountserviceApplicationTests {

    @Test
    void contextLoads() {
        Date a = new Date();
        System.out.println(a.toString());
    }

}
