package io.cultr.file.amazon.factory;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import io.cultr.file.amazon.configuration.AmazonConfiguration;
import io.cultr.file.amazon.configuration.AmazonProperties;

public class ClientFactory {

	public static AmazonS3Client getClient(
			AmazonConfiguration configuration, 
			AmazonProperties properties) {
		AmazonS3Client client = new AmazonS3Client(configuration.basicAWSCredentials());
		client.setRegion(Region.getRegion(Regions.US_WEST_2));	
		return client;
	}
}
