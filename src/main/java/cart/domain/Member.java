package cart.domain;

import cart.exception.MemberException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Member {

    public static final int MINIMUM_PASSWORD_LENGTH = 1;
    private static final String EMAIL_REGEX = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private final Long id;
    private final String email;
    private final String password;

    public Member(Long id, String email, String password) {
        email = email.strip();
        password = password.strip();
        validate(id, email, password);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    private void validate(Long id, String email, String password) {
        validateId(id);
        validateEmail(email);
        validatePassword(password);
    }

    private void validateId(Long id) {
        if (Objects.isNull(id)) {
            throw new MemberException.InvalidIdByNull();
        }
    }

    private void validateEmail(String email) {
        if (Objects.isNull(email)) {
            throw new MemberException.InvalidEmailByNull();
        }

        if (!pattern.matcher(email).matches()) {
            throw new MemberException.InvalidEmail();
        }
    }

    private void validatePassword(String password) {
        if (Objects.isNull(password)) {
            throw new MemberException.InvalidPasswordByNull();
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new MemberException.InvalidPasswordByLength(password);
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
