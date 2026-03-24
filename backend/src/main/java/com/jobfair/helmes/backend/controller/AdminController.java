package com.jobfair.helmes.backend.controller;

import com.jobfair.helmes.backend.job.ExpiredSessionCleaner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ExpiredSessionCleaner cleaner;

    @PostMapping("/cleanup")
    public String cleanup() {
        cleaner.runNow();
        return "Cleanup triggered!";
    }
}
