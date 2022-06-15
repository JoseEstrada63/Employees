package employees.prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

public class RegistrarUsuario extends AppCompatActivity {
    TextInputLayout userLayoutRegister,passwordLayoutRegister;
    TextInputEditText userEditRegister,passwordEditRegister;
    MaterialButton cancelar, aceptar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        userLayoutRegister= findViewById(R.id.userTextFieldRegister);
        passwordLayoutRegister=findViewById(R.id.passwordTextInputRegister);
        userEditRegister= findViewById(R.id.emailUserRegister);
        passwordEditRegister= findViewById(R.id.passwordUserRegister);
        cancelar= findViewById(R.id.cancelRegister);
        aceptar= findViewById(R.id.confirmRegister);
        mAuth = FirebaseAuth.getInstance();
        fAuth = FirebaseFirestore.getInstance();
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegistrarUsuario.this,MainActivity.class);
                startActivity(intent);
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegister();
            }
        });

    }

    private void validateRegister() {
        String email= userEditRegister.getText().toString().trim();
        String password= passwordEditRegister.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userLayoutRegister.setError("Correo invalido");
            return;
        }else{
            userLayoutRegister.setError(null);
        }

        if(password.isEmpty() || password.length() < 8){
            passwordLayoutRegister.setError("Se necesitan mas de 8 caracteres");
            return;
        }else if(!Pattern.compile("[0-9]").matcher(password).find()){
            passwordLayoutRegister.setError("Usa al menos un numero");
            return;
        } else{
            passwordLayoutRegister.setError(null);
        }
        registrar(email, password);
    }

    public void registrar(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (task.isSuccessful()){
                            DocumentReference df = fAuth.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("Email", userEditRegister.getText().toString());
                            userInfo.put("Contraseña",passwordEditRegister.getText().toString());
                            df.set(userInfo);
                            Toast.makeText(RegistrarUsuario.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(RegistrarUsuario.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegistrarUsuario.this, "Fallo al registrarse", Toast.LENGTH_SHORT).show();
                        }
                    }});
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(RegistrarUsuario.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}