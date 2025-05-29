package com.example.vinhedobravioapp.ui.components.login;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.components.CustomButtonComponent;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CadastroVinhoActivity extends AppCompatActivity {

    private MaterialButton winePhotoButton;
    private Drawable selectedImageDrawable = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_vinho);

        CustomButtonComponent cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(v -> finish());

        // Setup do Spinner de tipo de vinho
        Spinner wineType = findViewById(R.id.wineType);
        List<String> tiposVinho = Arrays.asList("Tinto", "Branco", "Rosé");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposVinho
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wineType.setAdapter(adapter);

        // Setup do campo de notas de degustação (multiseleção)
        EditText tastingNotes = findViewById(R.id.tastingNotes);

        String[] notas = {"Frutado", "Amadeirado", "Herbal", "Especiarias", "Floral"};
        boolean[] checkedItems = new boolean[notas.length];
        List<String> selectedNotas = new ArrayList<>();

        tastingNotes.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroVinhoActivity.this);
            builder.setTitle("Selecione as notas de degustação");

            builder.setMultiChoiceItems(notas, checkedItems, (dialog, which, isChecked) -> {
                if (isChecked) {
                    selectedNotas.add(notas[which]);
                } else {
                    selectedNotas.remove(notas[which]);
                }
            });

            builder.setPositiveButton("OK", (dialog, which) -> {
                tastingNotes.setText(TextUtils.join(", ", selectedNotas));
            });

            builder.setNegativeButton("Cancelar", null);
            builder.show();
        });

        // Botão para selecionar imagem (foto)
        winePhotoButton = findViewById(R.id.winePhoto);

        ActivityResultLauncher<Intent> photoPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        try {
                            Drawable imageDrawable = Drawable.createFromStream(
                                    getContentResolver().openInputStream(selectedImage),
                                    selectedImage.toString()
                            );
                            selectedImageDrawable = imageDrawable;
                            winePhotoButton.setIcon(selectedImageDrawable);
                            winePhotoButton.setText(""); // remover texto quando mostrar imagem
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        winePhotoButton.setOnClickListener(v -> {
            if (selectedImageDrawable == null) {
                // Se não tem imagem selecionada, abre seletor
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                photoPickerLauncher.launch(intent);
            } else {
                // Se já tem imagem, pergunta se quer remover
                new AlertDialog.Builder(CadastroVinhoActivity.this)
                        .setTitle("Remover foto")
                        .setMessage("Deseja remover a foto selecionada?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            selectedImageDrawable = null;
                            winePhotoButton.setIcon(getResources().getDrawable(R.drawable.icon_photo, null));
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });
    }
}
