package io.cultr.file.amazon.factory;

import java.io.File;

import io.cultr.file.amazon.configuration.AmazonProperties;

public class BucketFactory {

	public static String getBucket(AmazonProperties amazonProperties) {
		StringBuilder sb = new StringBuilder();		
		sb.append(amazonProperties.getS3().getDefaultBucket().getName())
		.append(File.separator)
		.append(amazonProperties.getS3().getDefaultBucket().getPath());
		return sb.toString();
	}
}
