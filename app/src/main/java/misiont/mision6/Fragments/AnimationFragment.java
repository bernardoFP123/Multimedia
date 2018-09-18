package misiont.mision6.Fragments;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import misiont.mision6.R;


public class AnimationFragment extends Fragment {


    ImageView imageView;
    FloatingActionButton fab;
    public AnimationFragment() {
        // Required empty public constructsor
    }


    public static AnimationFragment newInstance() {
        AnimationFragment fragment = new AnimationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_animation, container, false);
        imageView = view.findViewById(R.id.imageViewAnimation);
        fab = view.findViewById(R.id.fabAnimation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimatorSet ast = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),R.animator.star_animator);
                ast.setTarget(imageView);
                ast.start();
            }
        });

        return view;
    }


}
