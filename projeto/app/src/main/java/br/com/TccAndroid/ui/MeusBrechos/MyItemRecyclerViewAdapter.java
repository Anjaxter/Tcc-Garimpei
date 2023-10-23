package br.com.TccAndroid.ui.MeusBrechos;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.TccAndroid.LoginActivity;
import br.com.TccAndroid.dao.BrechoDAO;
import br.com.TccAndroid.databinding.FragmentMeusBrechos2Binding;
import br.com.TccAndroid.model.Brecho;
import br.com.TccAndroid.ui.BrechoPage.BrechoPageActivity;
import br.com.TccAndroid.ui.MeusBrechos.placeholder.PlaceholderContent.PlaceholderItem;
import br.com.TccAndroid.ui.MyBrechoPage.MyBrechoPage;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Brecho}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Brecho> mValues;

    public MyItemRecyclerViewAdapter(List<Brecho> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMeusBrechos2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public static Bitmap converterByteToBipmap(byte[] foto) {
        Bitmap bmp = null;
        Bitmap bitmapReduzido = null;
        byte[] x = foto;

        try {
            bmp = BitmapFactory.decodeByteArray(x, 0, x.length);

            bitmapReduzido = Bitmap.createScaledBitmap(bmp, 65, 65, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmapReduzido;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitulo());
        holder.mDescriptionView.setText(mValues.get(position).getEndereco() + " " + mValues.get(position).getNumero());
        holder.mHorarioFunDas.setText(mValues.get(position).getHoraFuncionaentoDas());
        holder.mHorarioFunAs.setText(mValues.get(position).getHoraFuncionaentoAs());
        holder.mImageByte.setImageBitmap(converterByteToBipmap(mValues.get(position).getFotos()));
//        Glide.with(holder.mImageByte).load(converterByteToBipmap(BrechoDAO.getBytesFromDatabase(Integer.toString(mValues.get(position).getId())))).into(holder.mImageByte);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyBrechoPage.class);
                intent.putExtra("mybrechoId", Integer.toString(holder.mItem.getId())); // Substitua com a lógica correta
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        public final TextViewIdView;
        public final View mView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final TextView mHorarioFunDas;
        public final TextView mHorarioFunAs;
        public ImageView mImageByte;
        public Brecho mItem;

        public ViewHolder(FragmentMeusBrechos2Binding binding) {
            super(binding.getRoot());

            mView = binding.getRoot();

            mTitleView = binding.titleCardBrecho;
            mDescriptionView = binding.descriptionCardBrecho;
            mHorarioFunDas = binding.hora1CardBrecho;
            mHorarioFunAs = binding.hora2CardBrecho;
            mImageByte = binding.imgCardBrecho;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}