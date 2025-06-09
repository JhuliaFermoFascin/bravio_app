package com.example.vinhedobravioapp;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vinhedobravioapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // FAB (botão flutuante)
        binding.fab.setOnClickListener(view -> {
            Snackbar.make(view, "FAB clicado", Snackbar.LENGTH_LONG).show();
        });

        // AQUI É ONDE VOCÊ LIGA O BOTÃO CUSTOMIZADO
        TextView botaoCustomizado = findViewById(R.id.botao_customizado);
        botaoCustomizado.setOnClickListener(view -> {
            Snackbar.make(view, "Botão customizado clicado!", Snackbar.LENGTH_SHORT).show();
            // ou qualquer outra lógica
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

}