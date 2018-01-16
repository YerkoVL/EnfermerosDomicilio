package pe.app.com.enfermerosdomicilio.interactor.login;

import com.facebook.login.LoginResult;

public interface interactorLogin {
    interface OnFinalLoginListener {
        void onErrorUsuario();

        void onErrorPassword();

        void onSuccess();
    }

    void login(String usuario, String password, OnFinalLoginListener listener, LoginResult resultadoLogin);
}
