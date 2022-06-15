package employees.prueba.MisColaboradores;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import employees.prueba.Adaptadores.MyAppAdapter;
import employees.prueba.Modelos.MyAppModel;
import employees.prueba.R;

import static android.content.Context.MODE_PRIVATE;

public class ListaColaboradores extends Fragment {
    RecyclerView emp_recycler_view;
    List<MyAppModel> employeeModelArrayList = new ArrayList<>();
    MyAppAdapter employeeAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ListaColaboradores() {
    }
    public static ListaColaboradores newInstance(String param1, String param2) {
        ListaColaboradores fragment = new ListaColaboradores();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_lista_colaboradores, container, false);
        emp_recycler_view = (RecyclerView) view.findViewById(R.id.contenedor);
        emp_recycler_view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        emp_recycler_view.setItemAnimator(new DefaultItemAnimator());
        employeeAdapter = new MyAppAdapter(getContext() , employeeModelArrayList);
        emp_recycler_view.setAdapter(employeeAdapter);
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();


        String jsonFileContent = null;
        try {
            jsonFileContent = readFile(getContext(),"employees_data.json");
            JSONObject jsonObject= new JSONObject(jsonFileContent).getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("employees");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                String name = jsonObj.getString("name");
                String mail = jsonObj.getString("mail");
                MyAppModel employeeModel = new MyAppModel();
                employeeModel.setNombre(""+name);
                employeeModel.setEmail(""+mail);
                employeeModel.setId(""+id);
                employeeModelArrayList.add(employeeModel);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return view;

    }
    public String readFile(Context context, String fileName) throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

        String content = "";
        String line;
        while ((line = reader.readLine()) != null)
        {
            content = content + line;
        }

        return content;

    }
}