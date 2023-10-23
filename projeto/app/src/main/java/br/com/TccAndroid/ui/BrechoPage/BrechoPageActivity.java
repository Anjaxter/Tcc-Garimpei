package br.com.TccAndroid.ui.BrechoPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import br.com.TccAndroid.R;
import br.com.TccAndroid.dao.BrechoDAO;
import br.com.TccAndroid.dao.ListaBrechoFragment;
import br.com.TccAndroid.dao.MyBrechoRecyclerViewAdapter;
import br.com.TccAndroid.model.Brecho;

public class BrechoPageActivity extends AppCompatActivity{

    TextView mTextViewTelefone, mTextViewTitle, mTextViewDescricao, mTextViewHorarioDas, mTextViewHorarioAs, mTextViewEndereco, mTextViewNumero;
    ImageView mImageViewFotoBrecho, mImageViewGoBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brecho_page);

        mTextViewTitle = findViewById(R.id.TextView_Title_BrechoPage);
        mTextViewDescricao = findViewById(R.id.TextView_Descricao_BrechoPage);
        mTextViewHorarioDas = findViewById(R.id.TextView_Horario_Das_BrechoPage);
        mTextViewHorarioAs = findViewById(R.id.TextView_Horario_As_BrechoPage);
        mTextViewEndereco = findViewById(R.id.TextView_Endereco_BrechoPage);
        mTextViewNumero = findViewById(R.id.TextView_Endereco_Numero_BrechoPage);
        mImageViewFotoBrecho = findViewById(R.id.imageView_BrechoPage);
        mTextViewTelefone = findViewById(R.id.TextView_Telefone_BrechoPage);
//        mImageViewGoBack = findViewById(R.id.button_goBack);
        String idBrecho = getIntent().getExtras().getString("brechoId");
        insertDataBrecho(idBrecho);

//        mImageViewGoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

    }

    public void insertDataBrecho(String id){

        List<Brecho> ListaBrechos = BrechoDAO.infoBrecho(id);

        String idBrecho = getIntent().getExtras().getString("brechoId");

        for (Brecho brecho : ListaBrechos ) {

            mTextViewTitle.setText(brecho.getTitulo());
            mTextViewDescricao.setText(brecho.getDescricao());
            mTextViewHorarioDas.setText(brecho.getHoraFuncionaentoDas());
            mTextViewHorarioAs.setText(brecho.getHoraFuncionaentoAs());
            mTextViewEndereco.setText(brecho.getEndereco());
            mTextViewNumero.setText(brecho.getNumero());
            mTextViewTelefone.setText(brecho.getTelefone());
            mImageViewFotoBrecho.setImageBitmap(converterByteToBipmap(BrechoDAO.getBytesFromDatabase(idBrecho)));
        }

    }

    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 220, 220, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }

}