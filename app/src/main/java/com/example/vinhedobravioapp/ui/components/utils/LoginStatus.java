package com.example.vinhedobravioapp.ui.components.utils;
public class LoginStatus {
    private long idUsuario;
    private String nome;
    private String dataLogin;
    private boolean isAdmin;
    private boolean manterLogado;

    public LoginStatus(long idUsuario, String nome, String dataLogin, boolean isAdmin, boolean manterLogado) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.dataLogin = dataLogin;
        this.isAdmin = isAdmin;
        this.manterLogado = manterLogado;
    }

    // Getters e Setters
    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDataLogin() { return dataLogin; }
    public void setDataLogin(String dataLogin) { this.dataLogin = dataLogin; }

    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public boolean isManterLogado() { return manterLogado; }
    public void setManterLogado(boolean manterLogado) { this.manterLogado = manterLogado; }
    @Override
    public String toString() {
        return "LoginStatus{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", dataLogin='" + dataLogin + '\'' +
                ", isAdmin=" + isAdmin +
                ", manterLogado=" + manterLogado +
                '}';
    }

}
