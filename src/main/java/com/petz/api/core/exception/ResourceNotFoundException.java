package com.petz.api.core.exception;

import java.io.Serializable;
import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 5390091506149407665L;

	public ResourceNotFoundException(final String name, final Serializable id) {
		super(name + ".id=" + id + " not found.");
	}

	public static Supplier<ResourceNotFoundException> supplier(final String name, final Serializable id) {
		return () -> new ResourceNotFoundException(name, id);
	}

}
