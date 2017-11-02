package jrl.acdat.ficheros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LeerFicherosActivity extends AppCompatActivity {

    public static final String NUMERO = "numero";
    public static final String VALOR = "valor.txt";
    public static final String DATO = "dato.txt";
    public static final String DATO_SD = "dato_sd.txt";
    public static final String OPERACIONES = "operaciones.txt";
    public static final String CODIFICACION = "UTF-8";

    TextView txvResultado;
    EditText edtOperando1, edtOperando2, edtOperando3, edtOperando4;
    Button btnSuma;
    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_ficheros);

        iniciar();
    }

    private void iniciar() {
        Resultado resultado;

        txvResultado = (TextView)findViewById(R.id.txvResultado);
        edtOperando1 = (EditText)findViewById(R.id.edtOperando1);
        edtOperando2 = (EditText)findViewById(R.id.edtOperando2);
        edtOperando3 = (EditText)findViewById(R.id.edtOperando3);
        edtOperando4 = (EditText)findViewById(R.id.edtOperando4);
        btnSuma = (Button)findViewById(R.id.btnSuma);
        btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1 = edtOperando1.getText().toString();
                String op2 = edtOperando2.getText().toString();
                String op3 = edtOperando3.getText().toString();
                String op4 = edtOperando4.getText().toString();
                int cantidad = 0;
                String operacion, mensaje;
                try {
                    cantidad = Integer.parseInt(op1) + Integer.parseInt(op2) +
                            Integer.parseInt(op3) + Integer.parseInt(op4);
                    operacion = op1 + "+" + op2 + "+" + op3 + "+" + op4 + "=" + String.valueOf(cantidad);
                } catch (NumberFormatException e) {
                    Log.e("Error", e.getMessage());
                    operacion = "0";
                    mensaje = e.getMessage();
                }
                txvResultado.setText(String.valueOf(cantidad));
                if(miMemoria.disponibleEscritura())
                    if(miMemoria.escribirExterna(OPERACIONES, operacion + "\n", true, CODIFICACION))
                        mensaje = "Operación escrita correctamente";
                    else
                        mensaje = "Error al escribir en la memoria externa";
                else
                    mensaje = "Memoria externa no disponible";
                Toast.makeText(LeerFicherosActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });

        miMemoria = new Memoria(getApplicationContext());

        resultado = miMemoria.leerRaw(NUMERO);
        if(resultado.getCodigo())
            edtOperando1.setText(resultado.getContenido());
        else {
            edtOperando1.setText("0");
            Toast.makeText(this, "Error al leer " + NUMERO + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }
        resultado = miMemoria.leerAsset(VALOR);
        if(resultado.getCodigo())
            edtOperando2.setText(resultado.getContenido());
        else {
            edtOperando2.setText("0");
            Toast.makeText(this, "Error al leer " + VALOR + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
        }

        if(miMemoria.escribirInterna(DATO, "7", false, CODIFICACION)) {
            resultado = miMemoria.leerInterna(DATO, CODIFICACION);
            if(resultado.getCodigo())
                edtOperando3.setText(resultado.getContenido());
            else {
                edtOperando3.setText("0");
                Toast.makeText(this, "Error al leer " + DATO + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Error al escribir " + DATO, Toast.LENGTH_SHORT).show();

        if(miMemoria.disponibleEscritura()) {
            if(miMemoria.escribirExterna(DATO_SD, "9", false, CODIFICACION)) {
                resultado = miMemoria.leerExterna(DATO_SD, CODIFICACION);
                if(resultado.getCodigo())
                    edtOperando4.setText(resultado.getContenido());
                else {
                    edtOperando4.setText("0");
                    Toast.makeText(this, "Error al leer " + DATO_SD + " " + resultado.getMensaje(), Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(this, "Error al escribir " + DATO_SD, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Memoria externa no disponible", Toast.LENGTH_SHORT).show();
    }
}
