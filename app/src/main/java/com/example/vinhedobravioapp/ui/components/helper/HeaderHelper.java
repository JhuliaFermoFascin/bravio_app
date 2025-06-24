package com.example.vinhedobravioapp.ui.components.helper;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.ui.components.vinhos.ConfigEstoqueActivity;
import com.example.vinhedobravioapp.ui.components.vinhos.EstoqueActivity;

public class HeaderHelper {
    public static void configurarHeader(Activity activity, String title, boolean isDashboard, boolean isEstoque, boolean isPageInitial) {
        View header = activity.findViewById(R.id.component_view_custom_header);
        if (header == null) return;

        ImageView menuIcon = header.findViewById(R.id.menu_suspenso);
        ImageView backIcon = header.findViewById(R.id.header_back);
        ImageView configIcon = header.findViewById(R.id.config);

        if (isPageInitial) {
            menuIcon.setVisibility(View.VISIBLE);
            backIcon.setVisibility(View.GONE);
            menuIcon.setOnClickListener(v -> {
                MenuSuspensoHelper.show(activity, isDashboard);
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
            Intent intent = new Intent(activity, ConfigEstoqueActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }
    public static void configurarHeader(Activity activity, String title, boolean isDashboard) {
        configurarHeader(activity, title, isDashboard, false, false);
    }
}
