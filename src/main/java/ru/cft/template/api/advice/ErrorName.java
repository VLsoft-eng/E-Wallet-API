package ru.cft.template.api.advice;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorName {

    public static final String DOEST_HAVE_RIGHTS = "Doesn't have rights";
    public static final String CREDENTIALS_ERROR = "Credentials error";
    public static final String NOT_FOUND = "Not found";
    public static final String AUTHENTICATION_ERROR = "Authentication error";
}
