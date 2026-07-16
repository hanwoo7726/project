package com.sparta.project.health;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {
    // 서버 배포 확인용

    @GetMapping("/health")
    public String health() {
        return "배포 성공";
    }



}
