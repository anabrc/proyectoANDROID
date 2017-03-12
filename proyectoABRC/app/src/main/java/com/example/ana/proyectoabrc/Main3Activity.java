package com.example.ana.proyectoabrc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {


    private Button btnCambiar;
    private EditText edtDNI;
    private EditText edtNewPassword;
    private EditText edtRepetirPassword;
    private DatabaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        manager = new DatabaseManager(this);
        btnCambiar = (Button)findViewById(R.id.btnCambiar);
        edtDNI = (EditText)findViewById(R.id.edtDniCambiar);
        edtNewPassword = (EditText)findViewById(R.id.edtNewPass);
        edtRepetirPassword = (EditText)findViewById(R.id.edtRepetir);

        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtDNI.getText().toString().equals("") || edtNewPassword.getText().toString().equals("") || edtRepetirPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Los campos deben estar rellenos",Toast.LENGTH_SHORT).show();
                }else{
                    if (edtNewPassword.getText().toString().equals(edtRepetirPassword.getText().toString())){
                        confirmarCambiar();
                    } else {
                        Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void confirmarCambiar(){
        AlertDialog.Builder confirmarCambiar = new AlertDialog.Builder(this);
        confirmarCambiar.setTitle("Cambiar contraseña");
        confirmarCambiar.setMessage("¿Deseas cambiar la contraseña?");
        confirmarCambiar.setCancelable(false);
        confirmarCambiar.setIcon(android.R.drawable.ic_menu_edit);

        confirmarCambiar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (manager.modificarPassword(edtDNI.getText().toString(),edtNewPassword.getText().toString()) != 0){
                    Toast.makeText(getApplicationContext(), "CONTRASEÑA CAMBIADA", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR AL CAMBIAR LA CONTRASEÑA", Toast.LENGTH_SHORT).show();
                }
            }
        });
        confirmarCambiar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si pulsa en cancelar no hago nada.
            }
        });

        confirmarCambiar.show();
    }
}