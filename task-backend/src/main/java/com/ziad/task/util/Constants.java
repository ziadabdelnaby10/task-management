package com.ziad.task.util;

import org.springframework.http.HttpHeaders;

public final class Constants {
    public static final String JWT_ISSUER = "Task Management";

    public static final String JWT_HEADER = HttpHeaders.AUTHORIZATION;

    public static final String CLAIM_EMAIL = "email";

    public static final String CLAIM_AUTHORITIES = "authorities";
}
