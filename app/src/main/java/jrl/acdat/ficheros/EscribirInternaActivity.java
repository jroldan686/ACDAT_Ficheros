package jrl.acdat.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EscribirInternaActivity extends AppCompatActivity {

    public final static String NOMBREFICHERO = "resultado.txt";

    TextView txvSuma, txvPropiedades;
    EditText edtOperando1, edtOperando2;
    Button btnSuma;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escribir_interna);

        txvSuma = (TextView)findViewById(R.id.txvSuma);
        txvPropiedades = (TextView)findViewById(R.id.txvPropiedades);
        edtOperando1 = (EditText)findViewById(R.id.edtOperando1);
        edtOperando2 = (EditText)findViewById(R.id.edtOperando2);
        btnSuma = (Button)findViewById(R.id.btnSuma);
        btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int r;
                String op1 = edtOperando1.getText().toString();
                String op2 = edtOperando2.getText().toString();
                String texto = "0";

                try {
                    r = Integer.parseInt(op1) + Integer.parseInt(op2);
                    texto = String.valueOf(r);
                } catch (NumberFormatException e) {
                    Log.e("Error", e.getMessage());
                    texto = "0";
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                txvSuma.setText(texto);
                if(miMemoria.escribirInterna(NOMBREFICHERO, texto, false, "UTF-8"))
                    txvPropiedades.setText(miMemoria.mostrarPropiedadesInterna(NOMBREFICHERO));
                else
                    txvPropiedades.setText("Error al escribir en el fichero " + NOMBREFICHERO);
            }
        });

        miMemoria = new Memoria(getApplicationContext());
    }
}
