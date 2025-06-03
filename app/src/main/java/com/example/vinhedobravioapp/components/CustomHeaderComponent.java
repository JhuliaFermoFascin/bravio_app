package com.example.vinhedobravioapp.components;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.vinhedobravioapp.R;

public class CustomHeaderComponent {
    public static void configurarHeader(Activity activity, String titulo ) {
        TextView tituloView = activity.findViewById(R.id.header_title);
        ImageView voltarView = activity.findViewById(R.id.header_back);

        if (tituloView != null) {
            tituloView.setText(titulo);
        }

        if (voltarView != null) {
            voltarView.setOnClickListener(v -> activity.onBackPressed());
        }
    }

}
