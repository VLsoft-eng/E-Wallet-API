package ru.cft.template.core.service;

import ru.cft.template.api.model.auth.LoginRequest;
import ru.cft.template.api.model.auth.LoginResponse;
import ru.cft.template.api.model.auth.RegistrationRequest;
import ru.cft.template.api.model.auth.RegistrationResponse;

public interface AuthService {
    RegistrationResponse signUp(RegistrationRequest registrationRequest);

    LoginResponse signIn(LoginRequest request);
}
