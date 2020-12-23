package com.srfairyox.Corvus.DarkOrbit.Data;

public class AccountData {
    private String Username;
    private String Password;
    private String SessionId;
    private String UserId;
    private String Server;
    private Boolean UsernamePasswordLogin;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public Boolean getUsernamePasswordLogin() {
        return UsernamePasswordLogin;
    }

    public void setUsernamePasswordLogin(Boolean usernamePasswordLogin) {
        UsernamePasswordLogin = usernamePasswordLogin;
    }

    @Override
    public String toString() {
        return "AccountData{" +
                "Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", SessionId='" + SessionId + '\'' +
                ", UserId='" + UserId + '\'' +
                ", Server='" + Server + '\'' +
                ", UsernamePasswordLogin=" + UsernamePasswordLogin +
                '}';
    }
}
