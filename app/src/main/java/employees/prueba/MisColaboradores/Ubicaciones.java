package employees.prueba.MisColaboradores;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import employees.prueba.R;
import employees.prueba.Ubicaciones.MapsActivity;

public class Ubicaciones extends Fragment implements OnMapReadyCallback{
    private GoogleMap mMap;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Ubicaciones() {
    }
    public static Ubicaciones newInstance(String param1, String param2) {
        Ubicaciones fragment = new Ubicaciones();
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
        View view= inflater.inflate(R.layout.fragment_ubicaciones, null, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
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
                    JSONObject location = jsonObj.getJSONObject("location");
                    String lat = location.getString("lat");
                    String log = location.getString("log");
                    mMap = googleMap;
                    double lt = Double.parseDouble(lat);
                    double lg = Double.parseDouble(log);
                    LatLng ubicacion = new LatLng(lt,lg);
                    mMap.addMarker(new MarkerOptions()
                            .position(ubicacion)
                            .title(name));
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(ubicacion, 11.0f);
                    mMap.animateCamera(yourLocation);


            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
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