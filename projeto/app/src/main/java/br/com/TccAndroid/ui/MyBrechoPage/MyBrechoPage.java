package br.com.TccAndroid.ui.MyBrechoPage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.santalu.maskara.widget.MaskEditText;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.TccAndroid.LoginActivity;
import br.com.TccAndroid.R;
import br.com.TccAndroid.dao.BrechoDAO;
import br.com.TccAndroid.dao.PerfilDAO;
import br.com.TccAndroid.model.Brecho;
import br.com.TccAndroid.ui.MeusBrechos.MeusBrechosFragment;

public class MyBrechoPage extends AppCompatActivity {

    MaskEditText maskEditTextTelefone, maskEditTextHoraDas, maskEditTextHoraAs;

    EditText mEditTextTitulo, mEditTextDescrição, mEditTextEndereco, mEditTextEnderecoNum;

    Button mButtonEditar, mButtonSave;

    ImageView mImageViewBrecho, mImageViewDeleteMyBrecho;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_brecho_page);

        mEditTextTitulo = findViewById(R.id.TextView_Title_MyBrechoPage);
        mEditTextDescrição = findViewById(R.id.TextView_Descricao_MyBrechoPage);
        mEditTextEndereco = findViewById(R.id.TextView_Endereco_MyBrechoPage);
        mEditTextEnderecoNum = findViewById(R.id.TextView_Endereco_Numero_MyBrechoPage);
        maskEditTextHoraDas = findViewById(R.id.TextView_Horario_Das_MyBrechoPage);
        maskEditTextHoraAs = findViewById(R.id.TextView_Horario_As_MyBrechoPage);
        maskEditTextTelefone = findViewById(R.id.TextView_Telefone_MyBrechoPage);
        mButtonEditar = findViewById(R.id.ButtonEditar_Mybrecho);
        mImageViewBrecho = findViewById(R.id.imageView_MyBrechoPage);
        mImageViewDeleteMyBrecho = findViewById(R.id.DeleteMybrecho);
        mButtonSave = findViewById(R.id.button_save_MyBrecho);

        String idBrecho = getIntent().getExtras().getString("mybrechoId");

        setDadosMyBrecho(idBrecho);

        mImageViewDeleteMyBrecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        mButtonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditTextTitulo.isEnabled()){
                    enableEdit();
                }else {
                    disableEdit();
                }
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarDadosBrecho();
            }
        });

        mImageViewBrecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

    }

    public void setDadosMyBrecho(String id){

        List<Brecho> ListaBrechos = BrechoDAO.infoBrecho(id);

        String idBrecho = getIntent().getExtras().getString("mybrechoId");

        for (Brecho brecho : ListaBrechos ) {

            mEditTextTitulo.setText(brecho.getTitulo());
            mEditTextDescrição.setText(brecho.getDescricao());
            maskEditTextHoraDas.setText(brecho.getHoraFuncionaentoDas());
            maskEditTextHoraAs.setText(brecho.getHoraFuncionaentoAs());
            mEditTextEndereco.setText(brecho.getEndereco());
            mEditTextEnderecoNum.setText(brecho.getNumero());
            maskEditTextTelefone.setText(brecho.getTelefone());
            mImageViewBrecho.setImageBitmap(converterByteToBipmap(BrechoDAO.getBytesFromDatabase(idBrecho)));
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Obtenha a URI da imagem selecionada
            Uri selectedImageUri = data.getData();

            // Defina a imagem selecionada no ImageView
            mImageViewBrecho.setImageURI(selectedImageUri);
        }
    }

    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 225, 225, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }

    public void enableEdit(){
        mEditTextTitulo.setEnabled(true);
        mEditTextDescrição.setEnabled(true);
        maskEditTextTelefone.setEnabled(true);
        maskEditTextHoraDas.setEnabled(true);
        maskEditTextHoraAs.setEnabled(true);
        mEditTextEndereco.setEnabled(true);
        mEditTextEnderecoNum.setEnabled(true);
        mButtonSave.setVisibility(View.VISIBLE);
    }

    public void disableEdit(){
        mEditTextTitulo.setEnabled(false);
        mEditTextDescrição.setEnabled(false);
        maskEditTextTelefone.setEnabled(false);
        maskEditTextHoraDas.setEnabled(false);
        maskEditTextHoraAs.setEnabled(false);
        mEditTextEndereco.setEnabled(false);
        mEditTextEnderecoNum.setEnabled(false);
        mButtonSave.setVisibility(View.GONE);
    }

    public void atualizarDadosBrecho(){

            String idBrecho = getIntent().getExtras().getString("mybrechoId");

            String ed_titulo = mEditTextTitulo.getText().toString();
            String ed_descricao = mEditTextDescrição.getText().toString();
            String ed_endereco = mEditTextEndereco.getText().toString();
            String ed_Num = mEditTextEnderecoNum.getText().toString();
            String ed_horaDas = maskEditTextHoraDas.getText().toString();
            String ed_horaAs = maskEditTextHoraAs.getText().toString();
            String ed_telefone = maskEditTextTelefone.getText().toString();

//            byte[] imgbytes = convertImageToByteArray(mImageViewBrecho);

            if(ed_titulo.equals("") || ed_descricao.equals("") || ed_endereco.equals("") || ed_telefone.equals("") || ed_horaAs.equals("") || ed_horaDas.equals("") || ed_Num.equals("")){
                Toast.makeText(MyBrechoPage.this, "Preencha todos os dados", Toast.LENGTH_SHORT).show();
            } else {
                int res = BrechoDAO.atualizarDadosBrecho(ed_titulo, ed_descricao, ed_telefone, ed_endereco, ed_Num, ed_horaDas, ed_horaAs, convertImageToByteArray(mImageViewBrecho), idBrecho);

                if ( res != 0){
                    Toast.makeText(MyBrechoPage.this, "Dados alterados com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyBrechoPage.this, "Erro", Toast.LENGTH_SHORT).show();
                }
            }
            }

    public byte[] convertImageToByteArray(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflar o layout personalizado para a caixa de diálogo
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_confirmation_mybrecho, null);

        String idBrecho = getIntent().getExtras().getString("mybrechoId");

        builder.setView(dialogView)
                .setTitle("Confirmação")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica para quando o botão "Sim" é clicado
                        BrechoDAO.deletarBrecho(Integer.parseInt(idBrecho));
                        Intent intent = new Intent(MyBrechoPage.this, MeusBrechosFragment.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(MyBrechoPage.this, "Brecho deletado com sucesso", Toast.LENGTH_SHORT).show();
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

}