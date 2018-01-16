package pe.app.com.enfermerosdomicilio.presentador.login;

import com.facebook.login.LoginResult;

import pe.app.com.enfermerosdomicilio.interactor.login.implemInteractLogin;
import pe.app.com.enfermerosdomicilio.interactor.login.interactorLogin;
import pe.app.com.enfermerosdomicilio.vista.vistaLogin;

public class implemPresentLogin implements presentadorLogin, interactorLogin.OnFinalLoginListener {

    private vistaLogin loginVista;
    private interactorLogin loginInteractor;

    public implemPresentLogin(vistaLogin loginVista, implemInteractLogin loginInteractor){
        this.loginVista = loginVista;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void validarCredenciales(String usuario, String password, LoginResult resultadoLogin) {
        if(loginVista != null){
            loginVista.mostrarProgreso();
        }
        loginInteractor.login(usuario,password,this,resultadoLogin);
    }

    @Override
    public void onDestroy() {
        loginVista = null;
    }

    @Override
    public void onErrorUsuario() {
        if(loginVista != null){
            loginVista.setErrorUsuario();
            loginVista.ocultarProgreso();
        }
    }

    @Override
    public void onErrorPassword() {
        if(loginVista != null){
            loginVista.setErrorPassword();
            loginVista.ocultarProgreso();
        }
    }

    @Override
    public void onSuccess() {
        if(loginVista != null){
            loginVista.navegarAPantallaPrincipal();
        }
    }
}
