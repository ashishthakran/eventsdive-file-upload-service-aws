package io.cultr.file.amazon.transformers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectResult;

import io.cultr.file.dto.ResponseDto;

public class FileTransformer {

	public static ResponseDto transform(
			AmazonS3Client client, PutObjectResult result, 
			String bucketName, String filePath) {
		ResponseDto response = new ResponseDto();
		if(null != result) {
			response.setUrl(client.getResourceUrl(bucketName, filePath));			
		}
		return response;
	}
}
