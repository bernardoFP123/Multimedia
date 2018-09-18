package misiont.mision6.Fragments;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import misiont.mision6.R;
import misiont.mision6.Renderers.CuboRenderer;


public class FragmentGrafico extends Fragment {

    private GLSurfaceView lienzo;

    public FragmentGrafico() {
        // Required empty public constructor
    }

    public static FragmentGrafico newInstance() {
        FragmentGrafico fragment = new FragmentGrafico();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lienzo = new GLSurfaceView(getContext());
        lienzo.setRenderer(new CuboRenderer(getContext()));
        return lienzo;
    }



}
