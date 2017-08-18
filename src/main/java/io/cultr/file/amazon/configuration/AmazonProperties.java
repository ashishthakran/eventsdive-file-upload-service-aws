package io.cultr.file.amazon.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "amazon")
public class AmazonProperties {

	@NestedConfigurationProperty
	private Aws aws;

	@NestedConfigurationProperty
	private S3 s3;

	public Aws getAws() {
		return aws;
	}

	public void setAws(Aws aws) {
		this.aws = aws;
	}

	public S3 getS3() {
		return s3;
	}

	public void setS3(S3 s3) {
		this.s3 = s3;
	}

	public static class Aws {

		@NestedConfigurationProperty
		private Credentials credentials;

		public Credentials getCredentials() {
			return credentials;
		}

		public void setCredentials(Credentials credentials) {
			this.credentials = credentials;
		}

		public static class Credentials {

			private String accessKey;
			private String secretKey;

			public String getAccessKey() {
				return accessKey.trim();
			}

			public void setAccessKey(String accessKey) {
				this.accessKey = accessKey;
			}

			public String getSecretKey() {
				return secretKey.trim();
			}

			public void setSecretKey(String secretKey) {
				this.secretKey = secretKey;
			}
		}
	}

	public static class S3 {

		@NestedConfigurationProperty
		private List<Bucket> buckets;
		
		@NestedConfigurationProperty
		private Bucket defaultBucket;
		
		@NestedConfigurationProperty
		private Path paths;

		public List<Bucket> getBuckets() {
			return buckets;
		}

		public void setBuckets(List<Bucket> buckets) {
			this.buckets = buckets;
		}

		public Bucket getDefaultBucket() {
			return defaultBucket;
		}

		public void setDefaultBucket(Bucket defaultBucket) {
			this.defaultBucket = defaultBucket;
		}
		
		public Path getPaths() {
			return paths;
		}

		public void setPaths(Path paths) {
			this.paths = paths;
		}

		public static class Bucket {

			private String name;
			private String region;
			private String path;

			public String getRegion() {
				return region.trim();
			}

			public void setRegion(String region) {
				this.region = region;
			}

			public String getName() {
				return name.trim();
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPath() {
				return path.trim();
			}

			public void setPath(String path) {
				this.path = path;
			}
		}
		
		public static class Path {

			private String events;
			private String separator;

			public String getEvents() {
				return events.trim();
			}

			public void setEvents(String events) {
				this.events = events;
			}

			public String getSeparator() {
				return separator;
			}

			public void setSeparator(String separator) {
				this.separator = separator;
			}
		}
	}
}
