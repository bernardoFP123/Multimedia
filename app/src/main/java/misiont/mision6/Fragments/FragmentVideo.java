package misiont.mision6.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;

import misiont.mision6.Activities.MainActivity;
import misiont.mision6.R;

import static misiont.mision6.Activities.MainActivity.REQUEST_CODE;
import static misiont.mision6.Activities.MainActivity.REQUEST_CODE_BRING_VIDEO;


public class FragmentVideo extends Fragment {

    VideoView videoView;
    Button buttonAbrir;
    Button buttonGrabar;
    TextView textViewRuta;
    MediaController mediaController;

    String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public FragmentVideo() {
        // Required empty public constructor
    }


    public static FragmentVideo newInstance() {
        FragmentVideo fragment = new FragmentVideo();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Pedir permisos
        int leer = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int leer1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(leer == PackageManager.PERMISSION_DENIED || leer1 == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),permisos,REQUEST_CODE);
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_video, container, false);

        buttonAbrir = view.findViewById(R.id.buttonAbrir);
        buttonGrabar = view.findViewById(R.id.buttonGrabar);
        textViewRuta = view.findViewById(R.id.textViewRuta);
        videoView = view.findViewById(R.id.videoViewImageFragment);

        buttonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent,REQUEST_CODE_BRING_VIDEO);
            }
        });
        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                File videoFolder = new File(Environment.getExternalStorageDirectory(),"VideoFolder");
                videoFolder.mkdir();
                File video = new File(videoFolder,"video.jpg");
                Uri uriVideo = Uri.fromFile(video);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uriVideo);
                getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_RECORD_VIDEO);
            }
        });


        return view;
    }

    public void setVideoResources(String videoPath){
        mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(videoPath));
        textViewRuta.setText("Ruta: "+videoPath);
        videoView.start();


    }

}

