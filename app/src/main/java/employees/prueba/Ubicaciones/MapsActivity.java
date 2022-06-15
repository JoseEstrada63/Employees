package employees.prueba.Ubicaciones;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import employees.prueba.Modelos.MyAppModel;
import employees.prueba.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String jsonFileContent = null;
        String idR= getIntent().getStringExtra("id");
        try {
            jsonFileContent = readFile(MapsActivity.this,"employees_data.json");
            JSONObject jsonObject= new JSONObject(jsonFileContent).getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("employees");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String id = jsonObj.getString("id");
                if (id.equals(idR)){
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