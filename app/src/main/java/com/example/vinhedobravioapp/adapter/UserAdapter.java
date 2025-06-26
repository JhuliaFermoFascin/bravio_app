package com.example.vinhedobravioapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vinhedobravioapp.R;
import com.example.vinhedobravioapp.database.dao.UserDAO;
import com.example.vinhedobravioapp.database.model.UserModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userList = new ArrayList<>();
    private Context context;
    private UserDAO userDAO;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onEdit(UserModel user);
        void onDelete(UserModel user);
    }

    public UserAdapter(Context context, UserDAO userDAO, OnUserClickListener listener) {
        this.context = context;
        this.userDAO = userDAO;
        this.listener = listener;
    }

    public void setUserList(List<UserModel> users) {
        this.userList = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName, userPassword, userEmail, userCreatedAt, userLastLogin, userType;
        Spinner userStatus;
        ImageButton btnEditar, btnDeletar;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            userPassword = itemView.findViewById(R.id.userPassword);
            userStatus = itemView.findViewById(R.id.userStatus);
            userCreatedAt = itemView.findViewById(R.id.createdAt);
            userLastLogin = itemView.findViewById(R.id.lastLogin);
            userType = itemView.findViewById(R.id.userType);

            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item,
                    new String[]{"INATIVO", "ATIVO"});
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userStatus.setAdapter(adapter);
        }

        public void bind(UserModel user) {
            userName.setText(user.getName());
            userEmail.setText(user.getEmail());
            userPassword.setText(user.getPassword());
            userType.setText(user.getIsAdmin() == 1 ? "Administrador" : "Representante");
            userCreatedAt.setText("Criação: " + (user.getCreatedAt() != null ? formatarData(user.getCreatedAt()) : "não informada"));
            userLastLogin.setText("Último login: " + (user.getLastLogin() != null ? formatarData(user.getLastLogin()) : "nunca"));

            userStatus.setSelection(user.getStatus() == 1 ? 1 : 0);

            int bgColor = (user.getStatus() == 1)
                    ? ContextCompat.getColor(context, android.R.color.holo_green_light)
                    : ContextCompat.getColor(context, android.R.color.darker_gray);
            userStatus.setBackgroundColor(bgColor);

            userStatus.setOnItemSelectedListener(null);
            userStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int novoStatus = (position == 1) ? 1 : 0;
                    if (user.getStatus() != novoStatus) {
                        user.setStatus(novoStatus);

                        int newBgColor = (novoStatus == 1)
                                ? ContextCompat.getColor(context, android.R.color.holo_green_light)
                                : ContextCompat.getColor(context, android.R.color.darker_gray);
                        userStatus.setBackgroundColor(newBgColor);

                        new Thread(() -> {
                            userDAO.update(user);
                            ((android.app.Activity) context).runOnUiThread(() ->
                                    Toast.makeText(context, "Status atualizado com sucesso!", Toast.LENGTH_LONG).show()
                            );
                        }).start();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            btnEditar.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onEdit(userList.get(pos));
                }
            });

            btnDeletar.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onDelete(userList.get(pos));
                }
            });
        }
    }

    private String formatarData(String dataOriginal) {
        try {
            SimpleDateFormat formatoEntrada = dataOriginal.contains(":")
                    ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            Date date = formatoEntrada.parse(dataOriginal);

            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return formatoSaida.format(date);

        } catch (Exception e) {
            return dataOriginal; // fallback se der erro
        }
    }
}
