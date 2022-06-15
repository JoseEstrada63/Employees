package employees.prueba.MisColaboradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import employees.prueba.MenuPrincipal;
import employees.prueba.R;

public class MisColaboradores extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_colaboradores);
        tabLayout= findViewById(R.id.tab_layout);
        viewPager= findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Mis colaboradores").setIcon(R.drawable.ic_user));
        tabLayout.addTab(tabLayout.newTab().setText("Ubicaciones").setIcon(R.drawable.ic_map));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        ListaColaboradores listaColaboradores= new ListaColaboradores();
                        return listaColaboradores;
                    case 1:
                       Ubicaciones ubicaciones= new Ubicaciones();
                        return ubicaciones;
                    default:
                        return null;
                }

            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position,0,true);
                tabLayout.setSelected(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
            Intent intent= new Intent(MisColaboradores.this, MenuPrincipal.class);
            startActivity(intent);
            finish();
    }
}