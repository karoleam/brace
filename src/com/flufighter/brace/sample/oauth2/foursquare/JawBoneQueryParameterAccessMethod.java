package com.flufighter.brace.sample.oauth2.foursquare;

import java.io.IOException;

import com.google.api.client.auth.oauth2.Credential.AccessMethod;
import com.google.api.client.http.HttpRequest;

  /**
   * Immutable and thread-safe OAuth 2.0 method for accessing protected resources using the <a
   * href="http://tools.ietf.org/html/rfc6750#section-2.3">URI Query Parameter</a>.
   * 
   * 
   */
  public final class JawBoneQueryParameterAccessMethod implements AccessMethod {

	private static final String PARAM_NAME = "oauth_token";

	private static final JawBoneQueryParameterAccessMethod instance = new JawBoneQueryParameterAccessMethod();
	
	private JawBoneQueryParameterAccessMethod() {
    }
	
	public static JawBoneQueryParameterAccessMethod getInstance() {
		return instance;
	}

    public void intercept(HttpRequest request, String accessToken) throws IOException {
      request.getUrl().set(PARAM_NAME, accessToken);
    }

    public String getAccessTokenFromRequest(HttpRequest request) {
      Object param = request.getUrl().get(PARAM_NAME);
      return param == null ? null : param.toString();
    }
  }