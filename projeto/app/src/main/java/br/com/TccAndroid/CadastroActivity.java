package br.com.TccAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import br.com.TccAndroid.R;
import br.com.TccAndroid.dao.DAO;
import br.com.TccAndroid.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    EditText mEditText_user, mEditText_senha, mEditText_email;
    Button mButtonCad;

    View mViewLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mEditText_user = findViewById(R.id.cad_edit_user);
        mEditText_senha = findViewById(R.id.cad_edit_pass);
        mEditText_email = findViewById(R.id.cad_edit_email);
        mButtonCad = findViewById(R.id.cad_btn_cadastro);
        mViewLogin = findViewById(R.id.cad_tv2);

        mViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        mButtonCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });

    }

    public void goToLogin (){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    private void cadastrarUsuario(){
        //cria um array " " com as strings para a função que faz o insert no db
        Usuario usuario = new Usuario(
                mEditText_user.getText().toString(),
                mEditText_senha.getText().toString(),
                mEditText_email.getText().toString()
        );

        String ed_user = mEditText_user.getText().toString();
        String ed_email = mEditText_email.getText().toString();
        String ed_senha = mEditText_senha.getText().toString();

        if(ed_user.equals("") || ed_senha.equals("") || ed_email.equals("") ){
            hideKeyBoard();
            Snackbar.make(findViewById(R.id.cad_constraint), "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
        } else if (validarUsuario(ed_user) != 1 && validarEmail(ed_email) != 1) {
            hideKeyBoard();
            Snackbar.make(findViewById(R.id.cad_constraint),"Nome e Email já estão em uso", Snackbar.LENGTH_SHORT).show();
        } else if (validarUsuario(ed_user) != 1) {
            hideKeyBoard();
            Snackbar.make(findViewById(R.id.cad_constraint),"Nome de usuário já está em uso",Snackbar.LENGTH_SHORT).show();
        } else if (validarEmail(ed_email) != 1){
            hideKeyBoard();
            Snackbar.make(findViewById(R.id.cad_constraint),"Email já está em uso", Snackbar.LENGTH_SHORT).show();
        } else if (ed_senha.length() < 8) {
            hideKeyBoard();
            Snackbar.make(findViewById(R.id.cad_constraint),"A senha deve conter pelo menos 8 Caracteres", Snackbar.LENGTH_SHORT).show();
        } else if (validarEmailComponences(ed_email) != 1) {
            hideKeyBoard();
            Snackbar.make(findViewById(R.id.cad_constraint),"Digite um email valido", Snackbar.LENGTH_SHORT).show();
        } else {

            int res = DAO.inserirUsuario(usuario);

            if(res <= 0){
                hideKeyBoard();
                Snackbar.make(findViewById(R.id.cad_constraint),"erro",Snackbar.LENGTH_SHORT).show();
            }else {
                hideKeyBoard();
                Snackbar.make(findViewById(R.id.cad_constraint),"Cadastro Realizado com sucesso",Snackbar.LENGTH_SHORT).show();
                goToLogin();
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }

            }
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

    //verifica se ja existe esse usuario no db
    private int validarUsuario(String valiUsuario){
        Usuario usu = new DAO().validarUsuarioCad(valiUsuario);
        //o 1 significa que o usuario nao existe no sql

        int validateNome = 1;

        if(usu != null) {
            validateNome = 0;
            return validateNome;
        }else {
         return 1;
        }
    }
    //verifica se ja existe esse email no db
    private int validarEmail(String valiEmail){
        Usuario email = new DAO().validarEmailCad(valiEmail);
        //o 1 significa que o usuario nao existe no sql

        int validateEmail = 1;

        if(email != null) {
            validateEmail = 0;
            return validateEmail;
        }else {
            return 1;
        }

    }

    //valida a se o email é valido (@gmail.com)
    private int validarEmailComponences(String email) {
        //o 1 significa que o email é valido
        int resvalidate = 1;

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return resvalidate;
        } else {
            resvalidate = 0;
            return resvalidate;
        }
    }

}