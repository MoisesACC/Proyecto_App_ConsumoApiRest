package com.app.consumoapirest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etAlumnoId, etNombres, etApellidos, etCarrera, etCorreo, etTelefono;
    private TextView tvAlumnoInfo;
    private Button btnGetAlumno, btnAddAlumno, btnUpdateAlumno, btnDeleteAlumno, btnLimpiarRegistro;
    private AlumnoApi alumnoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAlumnoId = findViewById(R.id.etAlumnoId);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etCarrera = findViewById(R.id.etCarrera);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        tvAlumnoInfo = findViewById(R.id.tvAlumnoInfo);
        btnGetAlumno = findViewById(R.id.btnGetAlumno);
        btnAddAlumno = findViewById(R.id.btnAddAlumno);
        btnUpdateAlumno = findViewById(R.id.btnUpdateAlumno);
        btnDeleteAlumno = findViewById(R.id.btnDeleteAlumno);
        btnLimpiarRegistro = findViewById(R.id.btnLimpiarRegistro);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.145.200.162:8080/api/")  // Usa 10.0.2.2 para localhost en el emulador
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        alumnoApi = retrofit.create(AlumnoApi.class);

        btnGetAlumno.setOnClickListener(v -> {
            String id = etAlumnoId.getText().toString();
            if (!id.isEmpty()) {
                getAlumnoById(Long.parseLong(id));
            } else {
                Toast.makeText(MainActivity.this, "Por favor, ingresa un ID válido", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddAlumno.setOnClickListener(v -> addAlumno());
        btnUpdateAlumno.setOnClickListener(v -> updateAlumno());
        btnDeleteAlumno.setOnClickListener(v -> deleteAlumno());

        // Limpia los campos de texto
        btnLimpiarRegistro.setOnClickListener(v -> {
            etAlumnoId.setText("");
            etNombres.setText("");
            etApellidos.setText("");
            etCarrera.setText("");
            etCorreo.setText("");
            etTelefono.setText("");
            tvAlumnoInfo.setText(""); // Limpiar también el TextView de información
            Toast.makeText(MainActivity.this, "Campos limpiados", Toast.LENGTH_SHORT).show();
        });
    }

    private void getAlumnoById(Long id) {
        Call<Alumno> call = alumnoApi.getAlumno(id);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()) {
                    Alumno alumno = response.body();
                    if (alumno != null) {
                        etAlumnoId.setText(String.valueOf(alumno.getIdAlumno()));
                        etNombres.setText(alumno.getNombres());
                        etApellidos.setText(alumno.getApellidos());
                        etCarrera.setText(alumno.getCarrera());
                        etCorreo.setText(alumno.getCorreo());
                        etTelefono.setText(alumno.getTelefono());
                        tvAlumnoInfo.setText("Información del Alumno recuperada.");
                    } else {
                        tvAlumnoInfo.setText("Alumno no encontrado");
                    }
                } else {
                    tvAlumnoInfo.setText("Error al obtener el alumno");
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                tvAlumnoInfo.setText("Fallo en la conexión: " + t.getMessage());
            }
        });
    }

    private void addAlumno() {
        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setNombres(etNombres.getText().toString());
        nuevoAlumno.setApellidos(etApellidos.getText().toString());
        nuevoAlumno.setCarrera(etCarrera.getText().toString());
        nuevoAlumno.setCorreo(etCorreo.getText().toString());
        nuevoAlumno.setTelefono(etTelefono.getText().toString());

        Call<Alumno> call = alumnoApi.addAlumno(nuevoAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Alumno agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al agregar alumno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAlumno() {
        Long id = Long.parseLong(etAlumnoId.getText().toString());
        Alumno alumnoActualizado = new Alumno();
        alumnoActualizado.setIdAlumno(id);
        alumnoActualizado.setNombres(etNombres.getText().toString());
        alumnoActualizado.setApellidos(etApellidos.getText().toString());
        alumnoActualizado.setCarrera(etCarrera.getText().toString());
        alumnoActualizado.setCorreo(etCorreo.getText().toString());
        alumnoActualizado.setTelefono(etTelefono.getText().toString());

        Call<Alumno> call = alumnoApi.updateAlumno(id, alumnoActualizado);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Alumno actualizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al actualizar alumno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAlumno() {
        Long id = Long.parseLong(etAlumnoId.getText().toString());
        Call<Void> call = alumnoApi.deleteAlumno(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Alumno eliminado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al eliminar alumno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
