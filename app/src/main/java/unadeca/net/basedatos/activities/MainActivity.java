package unadeca.net.basedatos.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import unadeca.net.basedatos.R;
import unadeca.net.basedatos.database.models.Arbolito;
import unadeca.net.basedatos.database.models.Arbolito_Table;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Arbolito cedro = new Arbolito();
        cedro.altura = 10;
        cedro.fecha_plantado = "2017-01-01";
        cedro.fecha_plantado = "2019-02-01";
        cedro.plantado_por = "Martin Perez";
        cedro.save();



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //EJEMPLO1 Mostrar y guardar un arbolito
                // Arbolito test = SQLite.select().from(Arbolito.class).querySingle();
               // Arbolito cedro = SQLite.select().from(Arbolito.class).where(Arbolito_Table.altura.eq(10)).querySingle();

                //Snackbar.make(view, cedro.altura +" " + cedro.plantado_por, Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //cedro.plantado_por = "Pablito";
                //cedro.save();

                //EJEMPLO2 Mostrar la cantidad de arbolitos registrados
                long contadoArbolitos = SQLite.selectCountOf().from(Arbolito.class).count();
                Snackbar.make(view, "Hay " + contadoArbolitos + " arbolitos registrados ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
