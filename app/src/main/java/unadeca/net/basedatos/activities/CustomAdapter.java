package unadeca.net.basedatos.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import unadeca.net.basedatos.R;
import unadeca.net.basedatos.database.models.Arbolito;

public class CustomAdapter extends ArrayAdapter<Arbolito>{

    private List<Arbolito> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtArbolito;
        ImageView delete;
        ImageView update;

    }

    public CustomAdapter(List<Arbolito> data, Context context) {
        super(context, R.layout.item, data);
        this.dataSet = data;
        this.mContext=context;

    }




    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Arbolito Arbolito = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.txtArbolito = convertView.findViewById(R.id.texto);
            viewHolder.delete = convertView.findViewById(R.id.delete);
            viewHolder.update = convertView.findViewById(R.id.update);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtArbolito.setText(Arbolito.toString());

        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Arbolito.delete();
                dataSet.remove(Arbolito);
                notifyDataSetChanged();
                Toast.makeText(getContext(), "Se ha eliminado el arbolito ", Toast.LENGTH_LONG).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public void mostrarDialogo(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View formulario = layoutInflater.inflate(R.layout.formulari, null);



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(formulario);
        final TextInputLayout altura = formulario.findViewById(R.id.txtAltura);
        final TextInputLayout siembra = formulario.findViewById(R.id.txtFechaSiembra);
        final TextInputLayout revision = formulario.findViewById(R.id.txtChequeo);
        final TextInputLayout encargado = formulario.findViewById(R.id.txtEncargado);



      /*  builder.setMessage("LLene toda la información solicitada").setTitle("Crear nuevo arbolito").setCancelable(false)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Try valida y el catch agarra los errores y muestra un mensaje.
                        try {
                            validate(altura, siembra, revision, encargado);
                            guardarABD(altura, siembra, revision, encargado);

                        }catch(Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG)
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

    private void validate(TextInputLayout a, TextInputLayout s, TextInputLayout c, TextInputLayout e) throws Exception{
        if(a.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la altura del albolito");
        if(s.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la fecha de siembra del albolito");
        if(a.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa la fecha del chequeo del albolito");
        if(a.getEditText().getText().toString().isEmpty()) throw  new Exception("Se ocupa el nomre del encargado de albolito");
    }*/

    /*private void guardarABD(TextInputLayout a, TextInputLayout s, TextInputLayout c, TextInputLayout e,
                            Arbolito arbolito){
        //Arbolito arbolito = new Arbolito();
        arbolito.altura = Integer.parseInt(a.getEditText().getText().toString());
        arbolito.fecha_plantado = s.getEditText().getText().toString();
        arbolito.plantado_por = e.getEditText().getText().toString();
        arbolito.fecha_ultima_revision = c.getEditText().getText().toString();
        arbolito.save();
        Snackbar.make(view, "Se ha guardado el arbolito", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        setAdapter();*/

    }


}