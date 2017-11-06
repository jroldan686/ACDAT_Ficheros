package jrl.acdat.ficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

public class CodificacionActivity extends AppCompatActivity implements View.OnClickListener {

    public static int FILE_PICKER_OK = 1;

    public static String UTF8 = "UTF-8";
    public static String UTF16 = "UTF-16";
    public static String ISO = "ISO-8859-15";

    EditText edtFicheroLectura, edtFicheroEscritura, edtContenido;
    Button btnLeer, btnGuardar;
    RadioButton rdbUtf8, rdbUtf16, rdbIso;

    Memoria miMemoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codificacion);

        iniciar();
    }

    public void iniciar() {
        edtFicheroLectura = (EditText)findViewById(R.id.edtFicheroLectura);
        edtFicheroLectura.setEnabled(false);
        edtContenido = (EditText)findViewById(R.id.edtContenido);
        edtFicheroEscritura = (EditText)findViewById(R.id.edtFicheroEscritura);
        btnLeer = (Button)findViewById(R.id.btnLeer);
        btnLeer.setOnClickListener(this);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(this);
        rdbUtf8 = (RadioButton)findViewById(R.id.rdbUtf8);
        rdbUtf16 = (RadioButton)findViewById(R.id.rdbUtf16);
        rdbIso = (RadioButton)findViewById(R.id.rdbIso);
        miMemoria = new Memoria(this);
    }

    @Override
    public void onClick(View view) {
        String cadena1 = edtFicheroLectura.getText().toString();
        String cadena2 = edtContenido.getText().toString();
        String cadena3 = edtFicheroEscritura.getText().toString();
        String mensaje = "";
        String codificacion = UTF8;
        Resultado resultado;

        if(rdbUtf8.isChecked())
            codificacion = UTF8;
        else
            if(rdbUtf16.isChecked())
                codificacion = UTF16;
            else
                if(rdbIso.isChecked())
                    codificacion = ISO;

        if(view == btnLeer) {
            openFilePicker();
            /*resultado = miMemoria.leerExterna(cadena1, codificacion);
            if(resultado.getCodigo()) {
                edtContenido.setText(resultado.getContenido());
                mensaje = "Fichero leído correctamente";
            }
            else {
                //edtContenido.setText("");
                mensaje = "Error al leer el fichero " + cadena1 + " " + resultado.getMensaje();
            }*/
        }
        if(view == btnGuardar) {
            if(cadena3.isEmpty())
                mensaje = "Debe introducir un nombre en el fichero a guardar";
            else {
                if (miMemoria.disponibleEscritura())
                    if (miMemoria.escribirExterna(cadena3, cadena2, false, codificacion))
                        mensaje = "Fichero escrito correctamente";
                    else
                        mensaje = "Error al escribir el fichero";
                else
                    mensaje = "Memoria externa no está disponible";
            }
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    private void openFilePicker() {
        new MaterialFilePicker()
                .withActivity(this)
                .withRequestCode(1)
                .withFilter(Pattern.compile(".*\\.txt$")) // Filtering files and directories by file name using regexp
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_OK) {
            String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            if(path != null) {
                //Log.d("Path: ", path);
                //Toast.makeText(this, "Fichero leido con exito", Toast.LENGTH_SHORT).show();
                edtContenido.setText(miMemoria.leerRutaExterna(path, "UTF-8"));
            }
        }
    }
}
