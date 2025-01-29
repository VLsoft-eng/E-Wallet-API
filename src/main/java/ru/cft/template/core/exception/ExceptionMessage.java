package ru.cft.template.core.exception;

public final class ExceptionMessage {

    public static final String USER_NOT_FOUND_MESSAGE = "Пользователь c указанным Id не найден";
    public static final String EMAIL_ALREADY_USED_MESSAGE = "Почта уже используется";
    public static final String PHONE_ALREADY_USED_MESSAGE = "Телефон уже используется";
    public static final String EDIT_OTHER_USER_NOT_ALLOWED_MESSAGE = "Запрещено редактировать чужой аккаунт";

    private ExceptionMessage() {}
}
