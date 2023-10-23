package br.com.TccAndroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;

import br.com.TccAndroid.dao.DAO;
import br.com.TccAndroid.model.Usuario;
import br.com.TccAndroid.ui.perfil.PerfilFragment;

public class LoginActivity extends AppCompatActivity {

    EditText mEditText_user, mEditText_pass;
    TextView mTextView_cad,texto;
    Button mButton_login;

    ProgressBar mProgressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditText_user = findViewById(R.id.ed_user);
        mEditText_pass = findViewById(R.id.ed_pass);
        mButton_login = findViewById(R.id.btn_login);
        mTextView_cad = findViewById(R.id.tv_2);
        mProgressBar = findViewById(R.id.login_progressBar);
//        texto = findViewById(R.id.tvTexto);

        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                login();
            }
        });

        mTextView_cad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotocad();
            }
        });

        //verifica se esta conectado com o banco de dados
//        Connection conn = Conexao.conectar(getApplicaionContext());
//        try {
//            if (conn != null) {
//                if (!conn.isClosed())
//                    texto.setText("CONEXÃO REALIZADA COM SUCESSO!!!");
//                else
//                    texto.setText("CONEXÃO FECHADA!!!");
//            }else{
//                texto.setText("CONEXÃO NULA, NÃO REALIZADA!!!");
//            }
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            texto.setText("CONEXÃO FALHOU!!! " +
//                    e.getMessage());
//        }

    }

    private Context getApplicaionContext() {
        return null;
    }

    public void login(){
//        mProgressBar.setVisibility(View.VISIBLE);

        View view = this.getCurrentFocus();

        String usuario = mEditText_user.getText().toString();
        String password = mEditText_pass.getText().toString();

        Usuario usu = new DAO().verificaLogin(usuario, password);

        if(usu != null){
//            snackBarLoginSuccess();
            hideKeyBoard();
            mProgressBar.setVisibility(View.INVISIBLE);
            gotohome();
        }else{
            hideKeyBoard();
            mProgressBar.setVisibility(View.INVISIBLE);
            Snackbar.make(findViewById(R.id.constraint), R.string.login_erro, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) { // Verifica se o teclado está ativo/aberto
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void gotohome(){
        //vai para a tela de cadastro

//        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//        intent.putExtra("Nome" , mEditText_user.getText().toString());
//        startActivity(intent);
        Bundle bundleNome = new Bundle();
        bundleNome.putString("Nome", mEditText_user.getText().toString());
        Intent cad_tela = new Intent(getApplicationContext(), HomeActivity.class);
        cad_tela.putExtras(bundleNome);
        startActivity(cad_tela);
    }

    public void gotocad(){
        //vai para a tela de cadastro
        Intent cad_tela = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(cad_tela);
    }

//    private void snackBarLoginSuccess(){
//        Snackbar.make(findViewById(R.id.constraint), R.string.login_susses, Snackbar.LENGTH_SHORT).show();
//    }

//    private void snackBarLoginErro(){
//        Snackbar.make(findViewById(R.id.constraint), R.string.login_erro, Snackbar.LENGTH_SHORT).show();
//    }



    public void eyepass(){
        //visualizar senha;
    }
}
