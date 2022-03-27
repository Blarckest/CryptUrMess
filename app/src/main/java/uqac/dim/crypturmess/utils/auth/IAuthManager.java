package uqac.dim.crypturmess.utils.auth;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public interface IAuthManager {
    Task<AuthResult> createUser(String email, String password);
    Task<AuthResult> signIn(String email, String password);
    Task<Void> resetPassword(String email);
    ValidationError validateEmailAndPassword(String email, String password);
    FirebaseUser getCurrentUser();
}
