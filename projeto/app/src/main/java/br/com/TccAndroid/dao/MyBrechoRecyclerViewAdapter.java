package br.com.TccAndroid.dao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.TccAndroid.R;
import br.com.TccAndroid.model.Brecho;

import br.com.TccAndroid.ui.BrechoPage.BrechoPageActivity;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Brecho}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBrechoRecyclerViewAdapter extends RecyclerView.Adapter<MyBrechoRecyclerViewAdapter.ViewHolder> {

    private final List<Brecho> mValues;

    public MyBrechoRecyclerViewAdapter(List<Brecho> brexos) {mValues = brexos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista_brecho,parent,false);

        return new ViewHolder(view);
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
//      Glide.with(holder.mImageByte).load(converterByteToBipmap(BrechoDAO.getBytesFromDatabase(Integer.toString(mValues.get(position).getId())))).into(holder.mImageByte);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BrechoPageActivity.class);
                intent.putExtra("brechoId", Integer.toString(holder.mItem.getId())); // Substitua com a lógica correta
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final TextView mHorarioFunDas;
        public final TextView mHorarioFunAs;
        public ImageView mImageByte;
        public Brecho mItem;

        private Context mContext;

        public ViewHolder(View view) {
            super(view);

            mContext = view.getContext();

            mView = view;
//            mIdView = (AppCompatTextView) view.findViewById(R.id.title_cardBrecho);
            mTitleView = (AppCompatTextView) view.findViewById(R.id.title_cardBrecho);
            mDescriptionView = (AppCompatTextView) view.findViewById(R.id.description_cardBrecho);
            mHorarioFunDas = (AppCompatTextView) view.findViewById(R.id.hora1_cardBrecho);
            mHorarioFunAs = (AppCompatTextView) view.findViewById(R.id.hora2_cardBrecho);
            mImageByte = (AppCompatImageView) view.findViewById(R.id.img_cardBrecho);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}