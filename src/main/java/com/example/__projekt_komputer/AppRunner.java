package com.example.__projekt_komputer;

import com.example.__projekt_komputer.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        new Main().run();
    }
}