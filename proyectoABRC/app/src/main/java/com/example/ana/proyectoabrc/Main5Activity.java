package com.example.ana.proyectoabrc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Main5Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String dni;
    DatabaseManager manager;
    private ListView listaReservas;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        dni = bundle.getString("DNI").toString();

        manager = new DatabaseManager(this);
        listaReservas = (ListView)findViewById(R.id.lvReservas);

        buscoPistas();

        listaReservas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView)findViewById(android.R.id.text1);
                final String nombrePista = tv.getText().toString();
                TextView tv2 = (TextView)findViewById(android.R.id.text2);
                final String fecha = tv2.getText().toString();
                AlertDialog.Builder diagBorrar = new AlertDialog.Builder(Main5Activity.this);
                diagBorrar.setTitle("Eliminar");
                diagBorrar.setMessage("¿Deseas eliminar la reserva?");
                diagBorrar.setCancelable(false);
                diagBorrar.setIcon(android.R.drawable.ic_delete);

                diagBorrar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        manager.eliminar(dni, nombrePista, fecha);
                        buscoPistas();
                        Toast.makeText(getApplicationContext(),"Reserva eliminada correctamente",Toast.LENGTH_SHORT).show();
                    }
                });

                diagBorrar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //si pulsa en cancelar no hago nada.
                    }
                });

                diagBorrar.show();

                return false;
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
        getMenuInflater().inflate(R.menu.main5, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Reservas) {
            Intent siguiente = new Intent(Main5Activity.this, Main5Activity.class);
            siguiente.putExtra("DNI", dni);
            startActivity(siguiente);
        } else if (id == R.id.nav_new) {
            Intent siguiente = new Intent(Main5Activity.this, Main4Activity.class);
            siguiente.putExtra("DNI", dni);
            startActivity(siguiente);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buscoPistas(){
        String[] from = new String[] { manager.CN_PISTA,manager.CN_FECHA,manager.CN_HORA};
        int[] to = new int[] {android.R.id.text1,android.R.id.text2};
        cursor = manager.getBuscaReservas(dni);
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                from,
                to,
                0);
        listaReservas.setAdapter(adapter);
    }



}
