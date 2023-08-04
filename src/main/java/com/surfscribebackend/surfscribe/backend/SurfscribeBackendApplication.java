package com.surfscribebackend.surfscribe.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class SurfscribeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurfscribeBackendApplication.class, args);

		String uploadDirectoryPath = "uploads";
		File uploadDirectory = new File(uploadDirectoryPath);

		if (!uploadDirectory.exists()) {
			if (uploadDirectory.mkdirs()) {
				System.out.println("Upload directory created successfully.");
			} else {
				System.err.println("Failed to create upload directory.");
			}
		}
	}

}
