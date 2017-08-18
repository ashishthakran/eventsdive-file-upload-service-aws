package io.cultr.file.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MetaData implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, String> userMetadata = new HashMap<String, String>();

	public Map<String, String> getUserMetadata() {
		return userMetadata;
	}

	public void setUserMetadata(Map<String, String> userMetadata) {
		this.userMetadata = userMetadata;
	}
}
