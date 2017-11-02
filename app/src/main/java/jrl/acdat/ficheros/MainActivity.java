package jrl.acdat.ficheros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnEscribirInterna, btnEscribirExterna, btnLeerFicheros, btnCodificacion, btnExploracion;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEscribirInterna = (Button)findViewById(R.id.btnEscribirInterna);
        btnEscribirInterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, EscribirInternaActivity.class);
                startActivity(i);
            }
        });
        btnEscribirExterna = (Button)findViewById(R.id.btnEscribirExterna);
        btnEscribirExterna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, EscribirExternaActivity.class);
                startActivity(i);
            }
        });
        btnLeerFicheros = (Button)findViewById(R.id.btnLeerFicheros);
        btnLeerFicheros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, LeerFicherosActivity.class);
                startActivity(i);
            }
        });
        btnCodificacion = (Button)findViewById(R.id.btnCodificacion);
        btnCodificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, CodificacionActivity.class);
                startActivity(i);
            }
        });
        btnExploracion = (Button)findViewById(R.id.btnExploracion);
        btnExploracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(MainActivity.this, ExploracionActivity.class);
                startActivity(i);
            }
        });
    }
}
