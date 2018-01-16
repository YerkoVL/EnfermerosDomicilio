package pe.app.com.enfermerosdomicilio.vista.actividades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pe.app.com.enfermerosdomicilio.R;
import pe.app.com.enfermerosdomicilio.datos.conexion.Singleton;
import pe.app.com.enfermerosdomicilio.datos.herramientas.GenericAlerts;
import pe.app.com.enfermerosdomicilio.datos.herramientas.YourPreference;
import pe.app.com.enfermerosdomicilio.datos.modelos.Usuario;
import pe.app.com.enfermerosdomicilio.interactor.login.implemInteractLogin;
import pe.app.com.enfermerosdomicilio.presentador.login.implemPresentLogin;
import pe.app.com.enfermerosdomicilio.presentador.login.presentadorLogin;
import pe.app.com.enfermerosdomicilio.vista.vistaLogin;

public class LoginActivity extends Activity implements vistaLogin{

    CallbackManager callbackManager;
    private Usuario usuario;
    private YourPreference preferencias;
    Gson gson = new Gson();
    Context mCtx;
    ProgressDialog barraProgreso = null;
    GenericAlerts alertas = new GenericAlerts();
    String URL_LOG = "";

    private presentadorLogin presentador;

    @BindView(R.id.logEdtUsuario) EditText EdtUsuario;
    @BindView(R.id.logEdtPassword) EditText EdtPass;
    @BindView(R.id.logBtnIngresar) TextView TxtIngresar;
    @BindView(R.id.logBtnRegistrar) TextView TxtRegistrar;
    @BindView(R.id.logTxtOlvido) TextView TxtOlvido;
    @BindView(R.id.login_button) LoginButton loginButton;
    @BindView(R.id.imgFondo) KenBurnsView mImg;

    String usuarioValidado,contrasenaValidada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mCtx = this;

        inicializar();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult resultadoLogin) {
                presentador.validarCredenciales(EdtUsuario.getText().toString(), EdtPass.getText().toString(), resultadoLogin.getAccessToken());
            }

            @Override
            public void onCancel() {
                alertas.mensajeInfo("Omitido", "Se canceló el login", mCtx);
            }

            @Override
            public void onError(FacebookException error) {
                alertas.mensajeInfo("Error", error.getMessage().toString(), mCtx);
            }
        });
    }

    public void inicializar() {
        barraProgreso = new ProgressDialog(mCtx);
        preferencias = YourPreference.getInstance(mCtx);

        requerrimientosFacebook();

        presentador = new implemPresentLogin(this,new implemInteractLogin());
    }

    public void requerrimientosFacebook() {
        List<String> permissions = new ArrayList<>();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_birthday");
        loginButton.setReadPermissions(permissions);

        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void mostrarProgreso() {
        barraProgreso.show();
    }

    @Override
    public void ocultarProgreso() {
        barraProgreso.dismiss();
    }

    @Override
    public void setErrorConexion(String titulo,String respuesta) {
        alertas.mensajeInfo(titulo, respuesta, mCtx);
    }

    @Override
    public void setErrorUsuario() {
        EdtUsuario.setError(getString(R.string.falta_usuario));
    }

    @Override
    public void setErrorPassword() {
        EdtPass.setError(getString(R.string.falta_password));
    }

    @Override
    public void agregarColaConexion(Request<String> respuesta) {
        Singleton.getInstance(mCtx).addToRequestQueue(respuesta);
    }

    @Override
    public void setAlerta(String titulo, String respuesta) {
        alertas.mensajeInfo(titulo,respuesta,mCtx);
    }

    public void setDialogo(String titulo, String respuesta) {
        new LovelyStandardDialog(mCtx)
                .setTopColorRes(R.color.colorPrimary)
                .setButtonsColorRes(R.color.colorAccent)
                .setIcon(R.drawable.ic_enfermera)
                .setTitle("Completado")
                .setMessage(respuesta)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dirigirMenuPrincipal();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void navegarAPantallaPrincipal() {
        startActivity(new Intent(mCtx,MainActivity.class));
    }

    public void dirigirMenuPrincipal(){
        Intent intent = new Intent(mCtx,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
