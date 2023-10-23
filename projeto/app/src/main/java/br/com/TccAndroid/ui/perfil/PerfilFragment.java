package br.com.TccAndroid.ui.perfil;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.com.TccAndroid.LoginActivity;
import br.com.TccAndroid.R;
import br.com.TccAndroid.dao.DAO;
import br.com.TccAndroid.dao.PerfilDAO;
import br.com.TccAndroid.model.Usuario;

public class PerfilFragment extends Fragment {

    TextView mTextViewNome, mTextViewEmail, mTextViewTitlePass;

    ImageView mImageViewDeleteUser;

    Button mButtonAlterDados, mButtonSaveDados;

    EditText mEditTextNome, mEditTextEmail, mEditTextSenhaAtual, mEditTextSenhaNova;

    String ed_Nome, ed_Email, ed_PassCurrent, ed_newPass;

    private PerfilViewModel mViewModel;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        mTextViewNome = view.findViewById(R.id.username);

//        mTextViewEmail = view.findViewById(R.id.email);
        mTextViewTitlePass = view.findViewById(R.id.changePasswordLabel);

        mButtonAlterDados = view.findViewById(R.id.changeDataBtn);
        mButtonSaveDados = view.findViewById(R.id.saveDataBtn);

        mEditTextNome = view.findViewById(R.id.usernameEdit);
        mEditTextEmail = view.findViewById(R.id.emailEdit);
        mEditTextSenhaAtual = view.findViewById(R.id.currentPassword);
        mEditTextSenhaNova = view.findViewById(R.id.newPassword);

        mImageViewDeleteUser = view.findViewById(R.id.DeletePerfil);

        mImageViewDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

//        String ed_Nome = mEditTextNome.getText().toString();
//        String ed_Email = mEditTextEmail.getText().toString();
//        String ed_PassCurrent = mEditTextSenhaAtual.getText().toString();
//        String ed_newPass = mEditTextSenhaNova.getText().toString();

        String nomeSeach = getActivity().getIntent().getExtras().getString("Nome");
        int idUser = getIdUser(nomeSeach);
        putInfo(idUser);
        mButtonSaveDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateUserData();

//                PerfilDAO.atualizarDadosUsuario(idUser, mEditTextNome.getText().toString(), mEditTextSenhaAtual.getText().toString(), mEditTextEmail.getText().toString());
//                getActivity().getIntent().putExtra("Nome",""+mEditTextNome+"");
//                String nomeUpdate = getActivity().getIntent().getExtras().getString("Nome");
//                int idUser = getIdUser(nomeSeach);
                putInfo(idUser);
                mTextViewNome.setText(mEditTextNome.getText().toString());
            }
        });

        mButtonAlterDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextNome.getVisibility() == View.VISIBLE){
                    hideInfo();
                } else {
                    apperInfo();
                }
            }
        });

        return view;
    }

    public int getIdUser(String nome){

//        String nomeSeach = getActivity().getIntent().getExtras().getString("Nome");
        int idUser = PerfilDAO.buscarIdUsuarioPorNome(nome);
//        String nomeUsuario = PerfilDAO.buscarNomeUsuarioPorId(idUser);
//        mTextViewNome.setText(nomeUsuario);
//        putInfo(nomeUsuario);
        return idUser;
    }

    public void putInfo(int id){

        List<Usuario> usuarios = PerfilDAO.getUserInfo(id);

        for (Usuario usuario : usuarios){
            mTextViewNome.setText(usuario.getUsername());
            mEditTextNome.setText(usuario.getUsername());
            mEditTextEmail.setText(usuario.getEmail());
            mEditTextSenhaAtual.setText(usuario.getPassword());
        }
    }
    public void hideInfo(){
        mEditTextNome.setVisibility(View.INVISIBLE);
        mEditTextEmail.setVisibility(View.INVISIBLE);
        mEditTextSenhaAtual.setVisibility(View.INVISIBLE);
//        mEditTextSenhaNova.setVisibility(View.GONE);
        mButtonSaveDados.setVisibility(View.INVISIBLE);
        mTextViewTitlePass.setVisibility(View.INVISIBLE);
    }
    public void apperInfo(){
        mEditTextNome.setVisibility(View.VISIBLE);
        mEditTextEmail.setVisibility(View.VISIBLE);
        mEditTextSenhaAtual.setVisibility(View.VISIBLE);
//        mEditTextSenhaNova.setVisibility(View.VISIBLE);
        mButtonSaveDados.setVisibility(View.VISIBLE);
        mTextViewTitlePass.setVisibility(View.VISIBLE);
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflar o layout personalizado para a caixa de diálogo
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation, null);

        builder.setView(dialogView)
                .setTitle("Confirmação")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para quando o botão "Sim" é clicado
                        PerfilDAO.deletarBrechoByUser(getIdUser(getActivity().getIntent().getExtras().getString("Nome")));
                        PerfilDAO.deletarUser(getIdUser(getActivity().getIntent().getExtras().getString("Nome")));
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para quando o botão "Não" é clicado
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    public void updateUserData(){

        String nomeSeach = getActivity().getIntent().getExtras().getString("Nome");
        int idUser = getIdUser(nomeSeach);

        String ed_nome = mEditTextNome.getText().toString();
        String ed_email = mEditTextEmail.getText().toString();
        String ed_senha = mEditTextSenhaAtual.getText().toString();

        if(ed_nome.equals("") || ed_email.equals("") || ed_senha.equals("")) {
            Toast.makeText(getContext(), "Preencha todos os dados", Toast.LENGTH_SHORT).show();
        } else if (validarUsuario(ed_nome, idUser) != 0) {
            Toast.makeText(getContext(), "Nome de usuário já está em uso", Toast.LENGTH_SHORT).show();
        } else if (validarEmail(ed_email, idUser) != 0) {
            Toast.makeText(getContext(), "Email já está em uso", Toast.LENGTH_SHORT).show();
        } else if (validarEmailComponences(ed_email) != 1) {
            Toast.makeText(getContext(), "Digite um email valido", Toast.LENGTH_SHORT).show();
        } else if (ed_senha.length() < 8) {
            Toast.makeText(getContext(), "Senha deve conter no minimo 8 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();
            PerfilDAO.atualizarDadosUsuario(idUser, ed_nome, ed_senha, ed_email);
        }
    }

    //verifica se ja existe esse usuario no db
    private int validarUsuario(String nome, int id){
        Usuario usu = new PerfilDAO().validarUsuarioCad(nome, id);

        if(usu != null) {
            return 1;
        }else {
            return 0;
        }
    }
    //verifica se ja existe esse email no db
    private int validarEmail(String email, int id){
        Usuario emailUser = new PerfilDAO().validarEmailCad(email, id);

        if(emailUser != null) {
            return 1;
        }else {
            return 0;
        }

    }

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