package io.cultr.file.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import io.cultr.file.dto.FileDto;
import io.cultr.file.dto.ResponseDto;
import io.cultr.file.services.IFileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class FileController {

	@Autowired
	@Qualifier(value = "amazonService")
	private IFileService amazonFileService;

	@ApiOperation(value = "Upload File to Cloud Storage", notes = "Upload File to Cloud Storage")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = PutObjectResult.class),
			@ApiResponse(code = 401, message = "Unauthorized"), 
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/files", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> upload(@RequestBody FileDto fileDto) throws IOException {
		return new ResponseEntity<ResponseDto>(amazonFileService.upload(fileDto), HttpStatus.OK);
	}

	@ApiOperation(value = "Download File from Cloud Storage", notes = "Download File from Cloud Storage")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = PutObjectResult.class),
			@ApiResponse(code = 401, message = "Unauthorized"), 
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/{groupType}/{parentId}/files/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable String groupType, @PathVariable String parentId,
			@PathVariable String fileName) throws IOException {
		return new ResponseEntity<byte[]>(
				amazonFileService.download(groupType, parentId, fileName), 
				HttpStatus.OK);
	}

	@ApiOperation(value = "Download File from Cloud Storage", notes = "Download File from Cloud Storage")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = PutObjectResult.class),
			@ApiResponse(code = 401, message = "Unauthorized"), 
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/files/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
		return new ResponseEntity<byte[]>(
				amazonFileService.download(fileName), 
				HttpStatus.OK);
	}

	@ApiOperation(value = "Download File from Cloud Storage", notes = "Download File from Cloud Storage")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success", response = PutObjectResult.class),
			@ApiResponse(code = 401, message = "Unauthorized"), 
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 404, message = "Not Found"), 
			@ApiResponse(code = 500, message = "Failure") })
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public List<S3ObjectSummary> list(@PathVariable String groupType, @PathVariable String parentId)
			throws IOException {
		return null;//bucketWrapper.list();
	}
}
