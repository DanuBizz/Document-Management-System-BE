package org.fh.documentmanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentManagementServiceDriver {
    public static void main(String[] args) {
        SpringApplication.run(DocumentManagementServiceDriver.class, args);
    }
    // TODO: hot loading, with maven dependency
    // TODO: init, lazy loading
}
