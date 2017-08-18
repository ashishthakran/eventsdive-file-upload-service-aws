package io.cultr.file.services;

import java.io.IOException;

import io.cultr.file.dto.FileDto;
import io.cultr.file.dto.ResponseDto;

public interface IFileService {

	public ResponseDto upload(FileDto fileDto) throws IOException;
	
	public byte[] download(String groupType, String parentId, String fileName) throws IOException;
	
	public byte[] download(String fileName) throws IOException;
	
	public void list(String groupType, String parentId);
}
