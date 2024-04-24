package utilities;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ContentTypeUtility {
	private static HashSet<String> validContentTypes = new HashSet<>(List.of("application/xml", "application/json", "text/plain"));
	
	public String getAccept(HttpServletRequest httpServletRequest) {
		String acceptValue = httpServletRequest.getHeader("Accept");
		if (!validContentTypes.contains(acceptValue)) {
			return "application/json";
		}
		return acceptValue;
	}
	
	public String getContentTypeValue(HttpServletRequest httpServletRequest) {
		String contentTypeValue = httpServletRequest.getHeader("Content-Type");
		if (!validContentTypes.contains(contentTypeValue)) {
			return "application/json";
		}
		return contentTypeValue;
	}
}
