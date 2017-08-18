package io.cultr.file.dto;

import java.io.Serializable;

import io.cultr.file.enums.GroupType;

public class FileDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private byte[] content;
	private String parentId;
	private GroupType groupType;
	private MetaData metaData;

	public FileDto() {}
	
	public FileDto(String name, String parentId, GroupType groupType) {
		this.name = name;
		this.parentId = parentId;
		this.groupType = groupType;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
}
