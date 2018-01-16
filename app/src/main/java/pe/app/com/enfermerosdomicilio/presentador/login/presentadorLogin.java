package pe.app.com.enfermerosdomicilio.presentador.login;

import com.facebook.AccessToken;

public interface presentadorLogin {
    void validarCredenciales(String usuario, String password, AccessToken acceso);
    void onDestroy();
}
