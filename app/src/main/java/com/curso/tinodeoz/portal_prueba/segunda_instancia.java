package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;


public class segunda_instancia extends Fragment {
    WebView webView;
    Button publicaion,ubicacion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public segunda_instancia() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static segunda_instancia newInstance(String param1, String param2) {
        segunda_instancia fragment = new segunda_instancia();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_segunda_instancia, container, false);
        cambiar_publicacion(v);
        cambiar_ubicacion(v);
       // ubicacion.setVisibility(View.GONE);
        String text = "<html><body style=\"text-align:center; font-size:18px; line-height:20px; background-color:#72ffffff;\"> %s </body></html>";
        String info=getString(R.string.descripcion3);
        webView = (WebView) v.findViewById(R.id.WebView3);
        webView.loadData(String.format(text, info), "text/html; charset=utf-8","UTF-8");
        webView.setBackgroundColor(Color.parseColor("#00FFFFFF"));

        return v;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void cambiar_ubicacion(View v){
        ubicacion=(Button) v.findViewById(R.id.btn_ubi_tocas);
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra= new Ubi_tocas();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();

            }
        });
    }


    public void cambiar_publicacion(View v){
        publicaion=(Button) v.findViewById(R.id.btn_pub_tocas);
        publicaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra= new Pub_toca();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();

            }
        });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
