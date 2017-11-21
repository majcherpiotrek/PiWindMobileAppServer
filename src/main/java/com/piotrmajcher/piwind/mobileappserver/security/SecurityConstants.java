package com.piotrmajcher.piwind.mobileappserver.security;

public class SecurityConstants {
	
	public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_STRING = "Authorization";
    public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    
	//REGISTRATION AND PASSWORD
    public static final String SIGN_UP_URL = "/register-user";
    public static final String REGISTRATION_CONFIRM_URL = "/confirm/**";
    public static final String PASSWORD_RETRIEVE_URL = "/password/retrieve/**";
    public static final String REGISTER_STATION_URL = "/stations/register";
    
    //H2
    public static final String H2_URL = "/h2-console/**";
}
