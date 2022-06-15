package employees.prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextInputLayout userLayout,passwordLayout;
    TextInputEditText userEdit,passwordEdit;
    MaterialButton login;
    TextView registerUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLayout= findViewById(R.id.userTextField);
        passwordLayout=findViewById(R.id.passwordTextInput);
        userEdit= findViewById(R.id.emailUser);
        passwordEdit= findViewById(R.id.passwordUser);
        login= findViewById(R.id.login);
        registerUser= findViewById(R.id.registerUser);
        mAuth= FirebaseAuth.getInstance();
        fAuth= FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, RegistrarUsuario.class);
                startActivity(intent);
            }
        });

    }
    public void validate(){
        String email= userEdit.getText().toString().trim();
        String password= passwordEdit.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userLayout.setError("Correo invalido");
            return;
        }else{
            userLayout.setError(null);
        }

        if(password.isEmpty() || password.length() < 8){
            passwordLayout.setError("Se necesitan mas de 8 caracteres");
            return;
        }else if(!Pattern.compile("[0-9]").matcher(password).find()){
            passwordLayout.setError("Usa al menos un numero");
            return;
        } else{
            passwordLayout.setError(null);
        }
        iniciarSesion(email, password);
    }

    private void iniciarSesion(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Bienvenido Employees", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainActivity.this, MenuPrincipal.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "¡Las credenciales son erroneas, intentalo nuevamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}