package misiont.mision6.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import misiont.mision6.Activities.MainActivity;
import misiont.mision6.R;

import static misiont.mision6.Activities.MainActivity.REQUEST_CODE;


public class FragmentAudio extends Fragment {
    Button buttonAbrir;
    Button buttonReproducir;
    Button buttonGrabar;
    TextView textViewRuta;
    MediaPlayer audio;
    boolean reproduciendo;
    boolean grabando = false;
    MediaRecorder mediaRecorder;
    private static String nombreAudio = Environment.getExternalStorageDirectory() + "/audio.3gp";

    String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    public FragmentAudio() {
        // Required empty public constructor
    }


    public static FragmentAudio newInstance() {
        FragmentAudio fragment = new FragmentAudio();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //Pedir permisos
        int leer = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int leer1 = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
        if(leer == PackageManager.PERMISSION_DENIED || leer1 == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(),permisos,REQUEST_CODE);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_audio, container, false);

        buttonAbrir = view.findViewById(R.id.buttonAbrir);
        buttonGrabar = view.findViewById(R.id.buttonGrabar);
        buttonReproducir = view.findViewById(R.id.buttonReproducir);
        textViewRuta = view.findViewById(R.id.textViewRuta);

        buttonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent,"Selecciona un audio"), MainActivity.REQUEST_CODE_BRING_AUDIO);
            }
        });
        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grabando(grabando);

                if (!grabando){
                    buttonGrabar.setText("Detener Grabación");
                }else{
                    buttonGrabar.setText("Iniciar Grabación");
                }

                grabando = !grabando;
            }
        });
        buttonReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audio!=null){
                    if(reproduciendo){
                        audio.pause();
                        reproduciendo = false;
                        buttonReproducir.setText("Reproducir");
                    }else{
                        audio.start();
                        reproduciendo = true;
                        buttonReproducir.setText("Detener");
                    }
                }
            }
        });

        return view;
    }


    public void setAudioResource(String path){
        audio = new MediaPlayer();
        try{
            audio.setDataSource(getContext(),Uri.parse(path));
            audio.prepare();
            textViewRuta.setText("Ruta: "+ path);
        }
        catch (Exception e){

        }

    }
    private void detenerGrabacion(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(getContext(), "Se ha guardado el audio en:\n" + Environment.getExternalStorageDirectory() + "/audio.3gp", Toast.LENGTH_LONG).show();
        setAudioResource(Environment.getExternalStorageDirectory() + "/audio.3gp");
    }

    private void grabando(boolean grabando){
        if (!grabando){
            comenzarGrabacion();
        }else{
            detenerGrabacion();
        }
    }

    private void comenzarGrabacion(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(nombreAudio);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try{
            mediaRecorder.prepare();
        }catch(IOException e){
            Toast.makeText(getContext(), "No se grabará correctamente", Toast.LENGTH_SHORT).show();
        }

        mediaRecorder.start();
    }


    @Override
    public void onStop() {
        if (mediaRecorder != null){
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if(audio != null){
            audio.release();
            audio = null;
        }
        super.onStop();
    }
}


