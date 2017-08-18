package io.cultr.file.amazon.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.BasicAWSCredentials;

@Configuration
@Component
public class AmazonConfiguration {

	@Autowired
	private AmazonProperties amazonProperties;

	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(amazonProperties.getAws().getCredentials().getAccessKey(),
				amazonProperties.getAws().getCredentials().getSecretKey());
	}
}
