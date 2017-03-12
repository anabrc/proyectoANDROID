package com.example.ana.proyectoabrc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @AUTOR: Ana Belén Ramírez Camas.
 */

public class MainActivity extends AppCompatActivity {

    private Button btnRegistrarse;
    private Button btnEntrar;
    private Button btnOlvide;
    private DatabaseManager manager;
    private EditText edtDni;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DatabaseManager(this);
        btnEntrar = (Button)findViewById(R.id.btnEntrar);
        btnOlvide = (Button)findViewById(R.id.btnOlvide);
        btnRegistrarse = (Button)findViewById(R.id.btnRegistrarse);
        edtDni = (EditText)findViewById(R.id.edtDNIAcceso);
        edtPassword = (EditText)findViewById(R.id.edtClave);

        /*
         * Insertar en la tabla pistas las cuatro que existen.
         * Baloncesto, Futbol Sala, Tenis y Padel
         * DISPONIBLE = 0 = SÍ
         * DISPOIBLE = 1 = NO
         *
         */


        manager.insertarPista(1000, "FUTBOL SALA", 0);
        manager.insertarPista(1100, "PADEL", 1);
        manager.insertarPista(1200, "TENIS", 0);
        manager.insertarPista(1300, "BALONCESTO", 0);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(siguiente);
            }
        });

        btnOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siguiente = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(siguiente);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (manager.getAcceso(edtDni.getText().toString(),edtPassword.getText().toString()).moveToFirst() == false){
                    Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
                } else {
                    Intent siguiente = new Intent(MainActivity.this, Main4Activity.class);
                    siguiente.putExtra("DNI",edtDni.getText().toString());
                    startActivity(siguiente);
                }

            }
        });
    }


}
