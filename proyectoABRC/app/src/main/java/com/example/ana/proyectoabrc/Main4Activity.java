package com.example.ana.proyectoabrc;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Main4Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private Spinner spnPistas;
        private ImageButton bfecha;
        private TextView txtDateVaules;
        private ImageButton bhora;
        private ImageButton bhoraMaximo;
        private TextView txtTimeValues;
        private Button btnReservar;
        private TextView txtMaximo;
        private int hora, minutos, horaMax, minMax;
        private String pista, fecha, timeSelect, maxSelect;
        Calendar dateTime = Calendar.getInstance();
        DatabaseManager manager;
        private String dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        dni = bundle.getString("DNI").toString();

        spnPistas = (Spinner)findViewById(R.id.spnPistas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Pistas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPistas.setAdapter(adapter);

        bfecha = (ImageButton)findViewById(R.id.imageButton2);
        txtDateVaules = (TextView)findViewById(R.id.txtDateValues);

        bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        bhora = (ImageButton)findViewById(R.id.imageButton3);
        txtTimeValues = (TextView)findViewById(R.id.txtTimeValues);

        bhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = dateTime.get(Calendar.HOUR_OF_DAY);
                minutos = dateTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Main4Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTimeValues.setText(hourOfDay + ":" + minute);
                    }
                }, hora, minutos, false);
                timePickerDialog.show();
            }
        });

        bhoraMaximo = (ImageButton)findViewById(R.id.imageButton5);
        txtMaximo = (TextView)findViewById(R.id.txtSelectMaximo);

        bhoraMaximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horaMax = dateTime.get(Calendar.HOUR_OF_DAY);
                minMax = dateTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Main4Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtMaximo.setText(hourOfDay + ":" + minute);
                    }
                }, horaMax, minMax, false);
                timePickerDialog.show();
            }
        });

        btnReservar = (Button)findViewById(R.id.btnReservar);
        manager = new DatabaseManager(this);

        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pista = spnPistas.getSelectedItem().toString();
                fecha = txtDateVaules.getText().toString();
                timeSelect = txtTimeValues.getText().toString();
                maxSelect = txtMaximo.getText().toString();

                if (manager.getDisponible(pista).moveToFirst() == false){
                    // El cursor está vacío
                    Toast.makeText(getApplicationContext(),"PISTA NO DISPONIBLE",Toast.LENGTH_SHORT).show();
                } else {
                    if (manager.getReserva(pista,fecha,timeSelect).moveToFirst() == true){
                        Toast.makeText(getApplicationContext(),"ERROR. LA PISTA YA ESTÁ RESERVADA",Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder confirmarInsertar = new AlertDialog.Builder(Main4Activity.this);
                        confirmarInsertar.setTitle("Reservar");
                        confirmarInsertar.setMessage("¿Deseas reservar la pista?");
                        confirmarInsertar.setCancelable(false);
                        confirmarInsertar.setIcon(android.R.drawable.ic_menu_edit);

                        confirmarInsertar.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                manager.insertarReserva(dni,pista,fecha,timeSelect,maxSelect);
                                Toast.makeText(getApplicationContext(),"RESERVADA SATISFACTORIAMENTE",Toast.LENGTH_SHORT).show();
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
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Reserva) {
            Intent siguiente = new Intent(Main4Activity.this, Main5Activity.class);
            siguiente.putExtra("DNI", dni);
            startActivity(siguiente);
        } else if (id == R.id.nav_newR) {
            Intent siguiente = new Intent(Main4Activity.this, Main4Activity.class);
            siguiente.putExtra("DNI", dni);
            startActivity(siguiente);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel(year,monthOfYear,dayOfMonth);
        }
    };

    private void updateTextLabel(int year, int monthOfYear, int dayOfMonth){
        txtDateVaules.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
    }

}
