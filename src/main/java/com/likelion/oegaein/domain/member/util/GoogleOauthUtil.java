package com.likelion.oegaein.domain.member.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.oegaein.domain.member.dto.GoogleOauthToken;
import com.likelion.oegaein.domain.member.dto.GoogleOauthUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@PropertySource("classpath:application.yaml")
public class GoogleOauthUtil {
    // constant
    private final String PARAM_CODE = "code";
    private final String PARAM_CLIENT_ID = "client_id";
    private final String PARAM_SECRET_KEY = "client_secret";
    private final String PARAM_REDIRECT_URI = "redirect_uri";
    private final String PARAM_GRANT_TYPE = "grant_type";
    private final String GOOGLE_TOKEN_REQ_URL = "https://oauth2.googleapis.com/token";
    private final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    // env
    @Value("${google-oauth-client-id}")
    private String param_client_id_value;
    @Value("${google-oauth-secret-key}")
    private String param_secret_key_value;
    @Value("${google-oauth-redirect-uri}")
    private String param_redirect_uri_value;
    @Value("${google-oauth-grant-type}")
    private String param_grant_type_value;

    public ResponseEntity<String> requestAccessToken(String code){
        // params setting
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(PARAM_CODE, code);
        params.add(PARAM_CLIENT_ID, param_client_id_value);
        params.add(PARAM_SECRET_KEY, param_secret_key_value);
        params.add(PARAM_REDIRECT_URI, param_redirect_uri_value);
        params.add(PARAM_GRANT_TYPE, param_grant_type_value);

        // http headers setting
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // http entity setting
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        // request token to resource server
        ResponseEntity<String> responseEntity = restTemplate.exchange(GOOGLE_TOKEN_REQ_URL, HttpMethod.POST, request, String.class);
        // check response ok
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity;
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> requestUserInfo(GoogleOauthToken googleOauthToken){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + googleOauthToken.getAccess_token());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(GOOGLE_USER_INFO_URL, HttpMethod.GET, request, String.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity;
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public GoogleOauthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(response.getBody(), GoogleOauthToken.class);
    }

    public GoogleOauthUserInfo getUserInfo(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(response.getBody(), GoogleOauthUserInfo.class);
    }
}
