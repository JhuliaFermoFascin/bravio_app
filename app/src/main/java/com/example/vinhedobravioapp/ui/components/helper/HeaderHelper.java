package com.example.vinhedobravioapp.ui.components.helper;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vinhedobravioapp.R;

public class HeaderHelper {
    public static void configurarHeader(Activity activity, String title, Integer tipoUsuario, boolean isEstoque, boolean isPageInitial, boolean isVisitante) {
        View header = activity.findViewById(R.id.component_view_custom_header);
        if (header == null) return;

        TextView titleView = header.findViewById(R.id.header_title);
        if (titleView != null) {
            titleView.setText(title);
        }

        ImageView menuIcon = header.findViewById(R.id.menu_suspenso);
        ImageView backIcon = header.findViewById(R.id.header_back);
        ImageView configIcon = header.findViewById(R.id.config);

        if (isPageInitial) {
            menuIcon.setVisibility(View.VISIBLE);
            backIcon.setVisibility(View.GONE);
            menuIcon.setOnClickListener(v -> {
                if (isVisitante) {
                    MenuVisitanteHelper.show(activity, tipoUsuario);
                } else {
                    MenuSuspensoHelper.show(activity, tipoUsuario);
                }
            });
        } else {
            backIcon.setVisibility(View.VISIBLE);
            menuIcon.setVisibility(View.GONE);
            backIcon.setOnClickListener(v -> activity.onBackPressed());
        }

        if (!isEstoque){
            configIcon.setVisibility(View.GONE);
        }

        configIcon.setOnClickListener(view -> {
            ConfigHelper.show(activity);
        });
    }
    public static void configurarHeader(Activity activity, String title, Integer tipoUsuario) {
        configurarHeader(activity, title, tipoUsuario, false, false, false);
    }
}
