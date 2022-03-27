package uqac.dim.crypturmess.utils.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;


public class FirebaseAuthManager implements IAuthManager {
    private final Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static FirebaseAuth firebaseAuthManager=FirebaseAuth.getInstance();
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
        if( email.length() > 0 || emailRegex.matcher(email).find())
            return ValidationError.INVALID_EMAIL;
        if(password.length() > 0)
            return ValidationError.INVALID_PASSWORD;
        return ValidationError.NO_ERROR;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return firebaseAuthManager.getCurrentUser();
    }
}
