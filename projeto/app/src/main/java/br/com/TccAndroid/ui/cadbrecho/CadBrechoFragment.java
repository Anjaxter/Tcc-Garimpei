package br.com.TccAndroid.ui.cadbrecho;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.santalu.maskara.widget.MaskEditText;

import java.io.ByteArrayOutputStream;

import br.com.TccAndroid.R;
import br.com.TccAndroid.dao.BrechoDAO;
import br.com.TccAndroid.dao.PerfilDAO;
import br.com.TccAndroid.databinding.FragmentCadBrechoBinding;
import br.com.TccAndroid.model.Brecho;

public class CadBrechoFragment extends Fragment {

    private FragmentCadBrechoBinding binding;

    MaskEditText mEditTextTelefone, mEditTextHoraFunDas,mEditTextHoraFunAs;
    EditText mEditTextTitulo, mEditTextDescricao, mEditTextEndereco, mEditTextNum;
    ImageView mImageViewUpload;
    ImageView mImageViewUp;

    byte[] mImageViewImageSelected;
    Button mButtonCadastrarBrecho;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CadBrechoViewModel slideshowViewModel =
                new ViewModelProvider(this).get(CadBrechoViewModel.class);

        View view = inflater.inflate(R.layout.fragment_cad_brecho, container, false);

        mEditTextTitulo = view.findViewById(R.id.cadBrecho_editText_title);
        mEditTextDescricao = view.findViewById(R.id.cadBrecho_editText_descricao);
        mEditTextTelefone = view.findViewById(R.id.cadBrecho_editText_telefone);
        mEditTextHoraFunDas = view.findViewById(R.id.cadBrecho_editText_HorarioFun_das);
        mEditTextHoraFunAs = view.findViewById(R.id.cadBrecho_editText_HoraioFun_as);
        mEditTextEndereco = view.findViewById(R.id.cadBrecho_editText_endereco);
        mButtonCadastrarBrecho = view.findViewById(R.id.cadBrecho_buttonCad);
        mImageViewUpload = view.findViewById(R.id.cadBrecho_cardBrecho);
        mImageViewUp = view.findViewById(R.id.cadBrecho_img_upload);
        mEditTextNum = view.findViewById(R.id.cadBrecho_editText_endereco_numero);
//        mImageViewImageSelected = convertImageToByteArray(mImageViewUpload);

        String ed_Titulo = mEditTextTitulo.getText().toString();
        String ed_Descricao = mEditTextDescricao.getText().toString();
        String ed_Telefone = mEditTextTelefone.getText().toString();
        String ed_HorarioFun_das = mEditTextHoraFunDas.getText().toString();
        String ed_horarioFun_as = mEditTextHoraFunAs.getText().toString();
        String ed_Endereco = mEditTextEndereco.getText().toString();

        mImageViewUpload.setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });

        mButtonCadastrarBrecho.setOnClickListener(view1 -> {
            if (ed_Descricao.equals(null) || ed_Titulo.equals(null) || ed_Endereco.equals(null) || ed_horarioFun_as.equals(null) || ed_HorarioFun_das.equals(null) || ed_Telefone.equals(null)) {
                Snackbar.make(view1, "Preencha todos os campos", Snackbar.LENGTH_SHORT).show();
            }else {
                cadastrarBrecho();
                clearInput();
            }
        });
        return view;
    }

    public void cadastrarBrecho(){

        Brecho brecho = new Brecho(
                mEditTextTitulo.getText().toString(),
                mEditTextDescricao.getText().toString(),
                mEditTextTelefone.getText().toString(),
                mEditTextHoraFunDas.getText().toString(),
                mEditTextHoraFunAs.getText().toString(),
                mEditTextEndereco.getText().toString() + ",",
                mEditTextNum.getText().toString(),
                convertImageToByteArray(mImageViewUpload),
                PerfilDAO.buscarIdUsuarioPorNome(getActivity().getIntent().getExtras().getString("Nome"))
        );

        int res = BrechoDAO.cadastrarBrecho(brecho);

        if(res != 0){
            Toast.makeText(getContext(), "Brecho cadastrado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Obtenha a URI da imagem selecionada
            Uri selectedImageUri = data.getData();

            // Defina a imagem selecionada no ImageView
            mImageViewUpload.setImageURI(selectedImageUri);
            mImageViewUp.setImageBitmap(null);
        }
    }

    public byte[] convertImageToByteArray(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, stream);
        return stream.toByteArray();
    }

    public void clearInput(){
        mEditTextHoraFunAs.setText("");
        mEditTextHoraFunDas.setText("");
        mEditTextEndereco.setText("");
        mEditTextDescricao.setText("");
        mEditTextTitulo.setText("");
        mEditTextTelefone.setText("");
        mEditTextNum.setText("");
        mImageViewUpload.setImageDrawable(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}