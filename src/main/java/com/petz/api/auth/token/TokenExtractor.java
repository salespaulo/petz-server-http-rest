package com.petz.api.auth.token;

public interface TokenExtractor {

	String extract(final String header);

}
