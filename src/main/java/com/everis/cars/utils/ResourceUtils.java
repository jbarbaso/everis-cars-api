package com.everis.cars.utils;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

public class ResourceUtils {
	
	public static URI getCreatedResourceUriWithIdPath (String id, UriInfo uriInfo) {
		return uriInfo.getAbsolutePathBuilder().path(id).build();
	}

}
