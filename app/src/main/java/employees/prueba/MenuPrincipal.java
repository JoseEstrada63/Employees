package employees.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;

import java.util.ArrayList;
import java.util.List;

import employees.prueba.MisColaboradores.MisColaboradores;
import employees.prueba.NuevoColaborador.NuevoColaborador;

public class MenuPrincipal extends AppCompatActivity {
    ImageCarousel carousel;
    MaterialButton misColboradores, nuevoColaborador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        carousel= findViewById(R.id.imageMenu);
        misColboradores= findViewById(R.id.misColaboradores);
        nuevoColaborador= findViewById(R.id.nuevoColaborador);

        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(R.drawable.colaboradores,"Employees"));
        list.add(new CarouselItem(R.drawable.imagen,"Employees"));
        list.add(new CarouselItem(R.drawable.experiencia_empleado,"Employees"));
        carousel.setShowTopShadow(true);
        carousel.setTopShadowAlpha(0.6f);
        carousel.setShowBottomShadow(true);
        carousel.setBottomShadowAlpha(0.6f);
        carousel.setShowIndicator(true);
        carousel.setShowNavigationButtons(false);
        carousel.setAutoPlay(false);
        carousel.setAutoPlayDelay(3000);
        carousel.start();
        carousel.addData(list);

        misColboradores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MenuPrincipal.this, MisColaboradores.class);
                startActivity(intent);
                finish();
            }
        });
        nuevoColaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MenuPrincipal.this, NuevoColaborador.class);
                startActivity(intent);
                finish();
            }
        });


    }
}