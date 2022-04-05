package uqac.dim.crypturmess.utils.validator;

public class PasswordValidator implements IValidator {
    @Override
    public boolean isValid(String password) {
        return password.length() > 6;
    }
}
