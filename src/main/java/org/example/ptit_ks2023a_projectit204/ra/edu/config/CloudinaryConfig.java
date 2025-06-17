package org.example.ptit_ks2023a_projectit204.ra.edu.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private static final String CLOUD_NAME = "dbvlx5k4e";
    private static final String API_KEY = "384366979862433";
    private static final String API_SECRET = "lBcg_a5lByRjDHt_w_T4Eag5HTI";


    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET));
    }

}