package com.curso.tinodeoz.portal_prueba;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link primera_all.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link primera_all#newInstance} factory method to
 * create an instance of this fragment.
 */
public class primera_all extends Fragment {
    WebView webView;
    Button txt_btn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public primera_all() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment primera_all.
     */
    // TODO: Rename and change types and number of parameters
    public static primera_all newInstance(String param1, String param2) {
        primera_all fragment = new primera_all();
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
        View v=inflater.inflate(R.layout.fragment_primera_all, container, false);

        cambiar_notificaciones(v);

        String text = "<html><body style=\"text-align:center; font-size:18px; line-height:20px; background-color:#72ffffff;\"> %s </body></html>";
        String info=getString(R.string.descripcion2);
        webView = (WebView) v.findViewById(R.id.WebView2);
        webView.loadData(String.format(text, info), "text/html; charset=utf-8","UTF-8");
        //webView.setBackgroundColor(Color.parseColor("#42ffffff"));
        webView.setBackgroundColor(Color.parseColor("#00FFFFFF"));

                return v;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void cambiar_notificaciones(View v){
        txt_btn=(Button) v.findViewById(R.id.txt_not);
        txt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra= new Notificaciones();
                FragmentTransaction trans= getFragmentManager().beginTransaction();
                trans.replace(R.id.content_frame, fra);
                trans.addToBackStack(null);
                trans.commit();

            }
        });

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
