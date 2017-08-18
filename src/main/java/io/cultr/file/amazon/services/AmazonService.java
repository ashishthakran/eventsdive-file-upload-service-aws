package io.cultr.file.amazon.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import io.cultr.file.amazon.builder.FileBuilder;
import io.cultr.file.amazon.configuration.AmazonConfiguration;
import io.cultr.file.amazon.configuration.AmazonProperties;
import io.cultr.file.amazon.factory.BucketFactory;
import io.cultr.file.amazon.factory.ClientFactory;
import io.cultr.file.amazon.transformers.FileTransformer;
import io.cultr.file.dto.FileDto;
import io.cultr.file.dto.ResponseDto;
import io.cultr.file.enums.GroupType;
import io.cultr.file.services.IFileService;

@Service(value = "amazonService")
public class AmazonService implements IFileService {
	
	@Autowired
	private AmazonConfiguration configuration;
	
	@Autowired
	private AmazonProperties amazonProperties;
	
	@Override
	/*@Timed(name = "upload", absolute = true)
    @ExceptionMetered(absolute = true, name = "uploadException")
	@HystrixCommand(groupKey = "Amazon", commandKey = "upload", fallbackMethod = "uploadFailure", 
	threadPoolKey = "s3bucket")*/
	public ResponseDto upload(FileDto fileDto) throws IOException {
		PutObjectResult putObjectResult = null;
		ObjectMetadata metaData = new ObjectMetadata();
		if(null != fileDto.getMetaData()) {
			metaData.setUserMetadata(fileDto.getMetaData().getUserMetadata());
		}			
		
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		String mimeType = mimeTypesMap.getContentType(fileDto.getName());
		metaData.setContentLength(fileDto.getContent().length);
		metaData.setContentType(mimeType);
		
		AmazonS3Client client = ClientFactory.getClient(configuration, amazonProperties);
		String filePath = FileBuilder.buildFileName(amazonProperties, fileDto);
		String bucketName = amazonProperties.getS3().getDefaultBucket().getName();
				
		try (InputStream input = new ByteArrayInputStream(fileDto.getContent())) {
			PutObjectRequest putObjectRequest = new PutObjectRequest(
					bucketName, filePath, input, metaData)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			putObjectResult = client.putObject(putObjectRequest)					;
		}
		
		return FileTransformer.transform(client, putObjectResult, bucketName, filePath);
	}

	@Override
	/*@Timed(name = "download", absolute = true)
    @ExceptionMetered(absolute = true, name = "downloadException")
	@HystrixCommand(groupKey = "Amazon", commandKey = "download", fallbackMethod = "downloadFailure", 
	threadPoolKey = "s3bucket")*/
	public byte[] download(String groupType, String parentId, String fileName) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(
				amazonProperties.getS3().getDefaultBucket().getName(), 
				FileBuilder.buildFileName(amazonProperties,
						new FileDto(fileName, parentId, 
								GroupType.valueOf(groupType.toUpperCase()))));
		S3Object s3Object = ClientFactory.getClient(configuration, amazonProperties)
				.getObject(getObjectRequest);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(inputStream);
		return bytes;
	}

	@Override
	/*@Timed(name = "download", absolute = true)
    @ExceptionMetered(absolute = true, name = "downloadException")
	@HystrixCommand(groupKey = "Amazon", commandKey = "download", fallbackMethod = "downloadFailure", 
	threadPoolKey = "s3bucket")*/
	public byte[] download(String fileName) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(
				amazonProperties.getS3().getDefaultBucket().getName(), 
				FileBuilder.buildFileName(amazonProperties, new FileDto(fileName, null, null)));
		S3Object s3Object = ClientFactory.getClient(configuration, amazonProperties)
				.getObject(getObjectRequest);
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		byte[] bytes = IOUtils.toByteArray(inputStream);
		return bytes;
	}

	@Override
	public void list(String groupType, String parentId) {
		ObjectListing objectListing = ClientFactory.getClient(configuration, amazonProperties)
				.listObjects(new ListObjectsRequest()
						.withBucketName(BucketFactory.getBucket(amazonProperties)));
		List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
		//return s3ObjectSummaries;
	}
	
	public ResponseDto uploadFailure(FileDto fileDto, Throwable ex) throws Throwable {
		throw ex;
	}
	
	public byte[] downloadFailure(String groupType, String parentId, String fileName, Throwable ex) throws Throwable {
		throw ex;
	}
	
	public byte[] download(String fileName, Throwable ex) throws Throwable {
		throw ex;
	}

}
