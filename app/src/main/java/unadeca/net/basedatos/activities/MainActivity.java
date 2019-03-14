package unadeca.net.basedatos.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import unadeca.net.basedatos.R;
import unadeca.net.basedatos.database.models.Arbolito;
import unadeca.net.basedatos.database.models.Arbolito_Table;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout view;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lista = findViewById(R.id.lista);
        view = findViewById(R.id.content); //Coordinador que permite anclar vista

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getArbolitos());

        lista.setAdapter(adaptador);

        //Metodo para mostrar
        setAdapter();

        //Arbolito cedro = new Arbolito();
        //cedro.altura = 10;
        //cedro.fecha_plantado = "2017-01-01";
        //cedro.fecha_plantado = "2019-02-01";
        //cedro.plantado_por = "Martin Perez";
        //cedro.save();

        //borrarArbolito();



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
                //long contadoArbolitos = SQLite.selectCountOf().from(Arbolito.class).count();
                //Snackbar.make(view, "Hay " + contadoArbolitos + " arbolitos registrados ", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

                //EJEMPLO3 Implementacion dialogo
                mostrarDialogo();
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

    private String[] getArbolitos() {
        List<Arbolito> listado = SQLite.select().from(Arbolito.class).queryList();
        String[] array = new String[listado.size()];
        for (int c = 0; c < listado.size(); c++) {
            array[c] = listado.get(c).toString();
        }
        return array;
    }
    //Adapdaor
    private void setAdapter() {
        lista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getArbolitos()));
    }

    public void mostrarDialogo(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View formulario = layoutInflater.inflate(R.layout.formulari, null);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(formulario);
        final TextInputLayout altura = formulario.findViewById(R.id.txtAltura);
        final TextInputLayout siembra = formulario.findViewById(R.id.txtFechaSiembra);
        final TextInputLayout revision = formulario.findViewById(R.id.txtChequeo);
        final TextInputLayout encargado = formulario.findViewById(R.id.txtEncargado);



        builder.setMessage("LLene toda la información solicitada").setTitle("Crear nuevo arbolito").setCancelable(false)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Try valida y el catch agarra los errores y muestra un mensaje.
                try {
                    validate(altura, siembra, revision, encargado);
                    guardarABD(altura, siembra, revision, encargado);

                }catch(Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
                dialog.dismiss();

            }
            //Boton Negtivo
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    //Excepción por si hay espacios en blanco(Validación)
    private void validate(TextInputLayout a, TextInputLayout s, TextInputLayout c, TextInputLayout e) throws Exception{
        if(a.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la altura del albolito");
        if(s.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la fecha de siembra del albolito");
        if(a.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la fecha del chequeo del albolito");
        if(a.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa el nomre del encargado de albolito");
    }

    //Guardar en la base de datos
    private void guardarABD(TextInputLayout a, TextInputLayout s, TextInputLayout c, TextInputLayout e){
        Arbolito arbolito = new Arbolito();
        arbolito.altura = Integer.parseInt(a.getEditText().getText().toString());
        arbolito.fecha_plantado = s.getEditText().getText().toString();
        arbolito.plantado_por = e.getEditText().getText().toString();
        arbolito.fecha_ultima_revision = c.getEditText().getText().toString();
        arbolito.save();
        Snackbar.make(view, "Se ha guardado el arbolito", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        setAdapter();

    }

    //Borrar algunos registros de las bases de datos
    private void borrarArbolito(){
        //Delete.table(Arbolito.class);
        setAdapter();
        SQLite.delete().from(Arbolito.class).where(Arbolito_Table.altura.between(1).and(15)).execute();

        Snackbar.make(view, "Se han borrado los listados de arbolitos", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
