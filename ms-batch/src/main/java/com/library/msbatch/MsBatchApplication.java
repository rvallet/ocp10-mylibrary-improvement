package com.library.msbatch;

import com.library.msbatch.config.ApplicationPropertiesConfig;
import com.library.msbatch.config.MailProperties;
import com.library.msbatch.utils.DateTools;
import com.nimbusds.jwt.util.DateUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages="com.library")
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableFeignClients("com.library.msbatch")
public class MsBatchApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(MsBatchApplication.class);

	@Autowired
	ApplicationPropertiesConfig applicationPropertiesConfig;

	@Autowired
	MailProperties mailProperties;

	public static void main(String[] args) {
		SpringApplication.run(MsBatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("\nMailTemplate BookLoan =>\nObject: {}\nContent:\n{}",
				applicationPropertiesConfig.getBookLoanObject(),
				StringEscapeUtils.escapeHtml4(applicationPropertiesConfig.getBookLoanTemplate())
		);
		LOGGER.info("\nMailTemplate BookReservation =>\nObject: {}\nContent:\n{}\nDeadline:\n{}",
				applicationPropertiesConfig.getBookReservationObject(),
				StringEscapeUtils.escapeHtml4(applicationPropertiesConfig.getBookReservationTemplate()),
				DateTools.nbJourInHourToString(applicationPropertiesConfig.getBookReservationDeadline())
		);
		LOGGER.info("\nMailProperties =>\nHost : {} \nPort : {} \nUserName: {} \nPassword : {}",
				mailProperties.getHost(),
				mailProperties.getPort(),
				mailProperties.getUsername(),
				mailProperties.getPassword()
		);
	}
}
