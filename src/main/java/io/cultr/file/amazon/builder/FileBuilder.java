package io.cultr.file.amazon.builder;

import io.cultr.file.amazon.configuration.AmazonProperties;
import io.cultr.file.dto.FileDto;
import io.cultr.file.enums.GroupType;

public class FileBuilder {

	public static String buildFileName(AmazonProperties amazonProperties, FileDto fileDto) {
		StringBuilder sb = new StringBuilder();
		
		if(GroupType.EVENTS.equals(fileDto.getGroupType())) {
			sb.append(amazonProperties.getS3().getDefaultBucket().getPath())
			.append(amazonProperties.getS3().getPaths().getEvents())
			.append(fileDto.getParentId())
			.append(amazonProperties.getS3().getPaths().getSeparator())
			.append(fileDto.getName());
		}
		return sb.toString();
	}
}
