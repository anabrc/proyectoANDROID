package com.example.ana.proyectoabrc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtApellidos;
    private EditText edtPassword;
    private EditText edtDNI;
    private EditText edtTlf;
    private Button btnRegistrar;
    private DatabaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        manager = new DatabaseManager(this);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        edtDNI = (EditText)findViewById(R.id.edtDNIRegistro);
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtApellidos = (EditText)findViewById(R.id.edtApellidos);
        edtTlf = (EditText)findViewById(R.id.edtTlf);
        edtPassword = (EditText)findViewById(R.id.edtPassword);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtDNI.getText().toString().equals("") || edtNombre.getText().toString().equals("") || edtPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Los campos DNI, NOMBRE Y PASSWORD deben estar rellenos",Toast.LENGTH_SHORT).show();
                }else{
                    confirmarInsertar();
                }
            }
        });

    }

    public void confirmarInsertar(){
        AlertDialog.Builder confirmarInsertar = new AlertDialog.Builder(this);
        confirmarInsertar.setTitle("Registrar usuario");
        confirmarInsertar.setMessage("¿Deseas registrarte?");
        confirmarInsertar.setCancelable(false);
        confirmarInsertar.setIcon(android.R.drawable.ic_menu_edit);

        confirmarInsertar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                registrarUsuario(edtDNI, edtNombre, edtApellidos, edtTlf, edtPassword);
            }
        });
        confirmarInsertar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Si pulsa en cancelar no hago nada.
            }
        });
        confirmarInsertar.show();
    }

    public void registrarUsuario (EditText dni, EditText nombre, EditText apellidos, EditText telefono, EditText password){
        if (manager.insertar(dni.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), telefono.getText().toString() , password.getText().toString()) != -1){
            Toast.makeText(getApplicationContext(),"Usuario registrado correctamente",Toast.LENGTH_LONG).show();
            Intent siguiente = new Intent(Main2Activity.this,MainActivity.class);
            startActivity(siguiente);
        } else {
            Toast.makeText(getApplicationContext(),"ERROR. El usuario ya existe.",Toast.LENGTH_LONG).show();
        }
    }

    //public void buscarDni(EditText dni, EditText nombre, EditText apellidos, EditText telefono, EditText password)


}
