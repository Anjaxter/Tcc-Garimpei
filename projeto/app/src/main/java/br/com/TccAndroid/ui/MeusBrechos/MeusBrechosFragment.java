package br.com.TccAndroid.ui.MeusBrechos;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.TccAndroid.R;
import br.com.TccAndroid.dao.BrechoDAO;
import br.com.TccAndroid.dao.DAO;
import br.com.TccAndroid.dao.PerfilDAO;
import br.com.TccAndroid.ui.MeusBrechos.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class MeusBrechosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MeusBrechosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MeusBrechosFragment newInstance(int columnCount) {
        MeusBrechosFragment fragment = new MeusBrechosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String nomeSeach = getActivity().getIntent().getExtras().getString("Nome");
        int idUser = PerfilDAO.buscarIdUsuarioPorNome(nomeSeach);
        View view = inflater.inflate(R.layout.fragment_meus_brechos_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(BrechoDAO.meusBrecos(Integer.toString(idUser))));
        }
        return view;
    }
}