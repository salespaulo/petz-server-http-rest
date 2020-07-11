package com.petz.api.auth.token;

public interface TokenStringExtractor {

	String extract(final String header);

}
