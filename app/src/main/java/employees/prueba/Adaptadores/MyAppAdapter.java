package employees.prueba.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import employees.prueba.Modelos.MyAppModel;
import employees.prueba.R;
import employees.prueba.Ubicaciones.MapsActivity;

public class MyAppAdapter extends RecyclerView.Adapter<MyAppAdapter.MyViewHolder> {
    private Context context;
    private List<MyAppModel> myappModelArrayList;

    public MyAppAdapter(Context context,List<MyAppModel> myappModelArrayList){

        this.context= context;
        this.myappModelArrayList = myappModelArrayList;
    }

    @Override
    public MyAppAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAppAdapter.MyViewHolder holder, int position) {
        holder.nombre.setText(myappModelArrayList.get(position).getNombre());
        holder.email.setText(myappModelArrayList.get(position).getEmail());
        holder.numeroColaborador.setText(myappModelArrayList.get(position).getId());
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("id",myappModelArrayList.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myappModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre, email,numeroColaborador;
        LinearLayout linear;

        public MyViewHolder(View itemView) {
            super(itemView);
            linear= (LinearLayout) itemView.findViewById(R.id.linearList);
            numeroColaborador = (TextView) itemView.findViewById(R.id.idtext);
            nombre = (TextView) itemView.findViewById(R.id.nametext);
            email = (TextView) itemView.findViewById(R.id.emailtext);
        }

    }

}