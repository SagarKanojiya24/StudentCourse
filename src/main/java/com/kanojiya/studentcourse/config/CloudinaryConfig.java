package com.kanojiya.studentcourse.config;


import com.cloudinary.Cloudinary;
// 👆 Cloudinary ka main class

import org.springframework.context.annotation.Bean;
// 👆 @Bean annotation

import org.springframework.context.annotation.Configuration;
// 👆 @Configuration annotation

import java.util.HashMap;
import java.util.Map;

@Configuration
// 👆 Spring ko bolta hai: ye configuration class hai
public class CloudinaryConfig {

    @Bean
    // 👆 Ye method ka return object Spring container me register hoga
    public Cloudinary cloudinary() {

        Map<String, String> config = new HashMap<>();
        // 👆 Cloudinary ke credentials rakhne ke liye Map

        config.put("cloud_name", "djvd8uec5");
        // 👆 Cloudinary account ka naam

        config.put("api_key", "224542782365611");
        // 👆 Public key

        config.put("api_secret", "7GA9yAcpUgSl9JJdP6CzTM-KA4k");
        // 👆 Secret key (PRIVATE)

        return new Cloudinary(config);
        // 👆 Cloudinary ka object Spring ko de diya
    }
}
