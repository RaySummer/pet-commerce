package com.pet.commerce;

import com.pet.commerce.core.module.base.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

}
