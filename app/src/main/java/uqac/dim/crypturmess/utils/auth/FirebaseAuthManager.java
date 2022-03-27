package uqac.dim.crypturmess.utils.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import uqac.dim.crypturmess.utils.validator.EmailValidator;
import uqac.dim.crypturmess.utils.validator.IValidator;
import uqac.dim.crypturmess.utils.validator.PasswordValidator;


public class FirebaseAuthManager implements IAuthManager {
    private IValidator emailValidator = new EmailValidator();
    private IValidator passwordValidator = new PasswordValidator();
    private FirebaseAuth firebaseAuthManager=FirebaseAuth.getInstance();
    @Override
    public Task<AuthResult> createUser(String email, String password) {
        return firebaseAuthManager.createUserWithEmailAndPassword(email, password);
    }
    @Override
    public Task<AuthResult> signIn(String email, String password) {
        return firebaseAuthManager.signInWithEmailAndPassword(email, password);
    }

    @Override
    public Task<Void> resetPassword(String email) {
        return firebaseAuthManager.sendPasswordResetEmail(email);
    }

    @Override
    public ValidationError validateEmailAndPassword(String email, String password) {
        if(!emailValidator.isValid(email))
            return ValidationError.INVALID_EMAIL;
        if(!passwordValidator.isValid(password))
            return ValidationError.INVALID_PASSWORD;
        return ValidationError.NO_ERROR;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuthManager.getCurrentUser();
    }
}
