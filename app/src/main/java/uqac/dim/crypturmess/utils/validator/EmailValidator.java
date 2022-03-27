package uqac.dim.crypturmess.utils.validator;

import java.util.regex.Pattern;

public class EmailValidator implements IValidator {
    private final Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String email) {
        return email.length() > 0 && emailRegex.matcher(email).find();
    }
}
