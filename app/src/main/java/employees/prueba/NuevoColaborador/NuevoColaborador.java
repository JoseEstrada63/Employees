package employees.prueba.NuevoColaborador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;
import employees.prueba.MenuPrincipal;
import employees.prueba.Modelos.MyAppModel;
import employees.prueba.R;

public class NuevoColaborador extends AppCompatActivity {
    private static final String TAG = "Tag";
    TextInputLayout nameLayout, mailLayout;
    TextInputEditText nameEdit,mailEdit;
    MaterialButton confirm;
    long maxid= 0;
    DatabaseReference databaseReference;
    ArrayList<MyAppModel> mExampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_colaborador);
        nameLayout= findViewById(R.id.nameTextFielCol);
        mailLayout= findViewById(R.id.emailTextFielCol);
        nameEdit= findViewById(R.id.nameEditTextCol);
        mailEdit= findViewById(R.id.emailEditCol);
        confirm= findViewById(R.id.confirmCol);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        databaseReference.child("data").child("employees").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    maxid=(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void validate() {
        String name= nameEdit.getText().toString().trim();
        String email= mailEdit.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mailLayout.setError("Formato de correo invalido");
            return;
        }else{
            mailLayout.setError(null);
        }

        if(name.isEmpty()){
            nameLayout.setError("Ingresa el nombre del colaborador");
            return;
        }else{
            nameLayout.setError(null);
        }
        registrarColaborador(name, email);
    }

    private void registrarColaborador(String name, String email) {
        Random generadorAleatorios = new Random();
        int numeroAleatorio = 1+generadorAleatorios.nextInt(1000);
        String id= String.valueOf(numeroAleatorio);
        double lat= generadorAleatorios.nextDouble();
        double log= generadorAleatorios.nextDouble();
        String latitud= String.valueOf(lat);
        String longitud= String.valueOf(log);
        databaseReference.child("data").child("employees").child(String.valueOf(maxid)).child("name").setValue(name);
        databaseReference.child("data").child("employees").child(String.valueOf(maxid)).child("mail").setValue(email);
        databaseReference.child("data").child("employees").child(String.valueOf(maxid)).child("id").setValue(id);
        databaseReference.child("data").child("employees").child(String.valueOf(maxid)).child("location").child("lat").setValue(latitud);
        databaseReference.child("data").child("employees").child(String.valueOf(maxid)).child("location").child("log").setValue(longitud);
        Toast.makeText(NuevoColaborador.this,"Colaborador guardado",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(NuevoColaborador.this, MenuPrincipal.class);
        startActivity(intent);
        finish();
    }
}