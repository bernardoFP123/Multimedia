package misiont.mision6.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import misiont.mision6.Activities.MainActivity;
import misiont.mision6.R;

import static android.R.attr.button;
import static misiont.mision6.Activities.MainActivity.REQUEST_CODE;
import static misiont.mision6.Activities.MainActivity.REQUEST_CODE_BRING_PHOTO;


public class FragmentImagen extends Fragment {

    ImageView imageView;
    Button buttonAbrir;
    Button buttonGenerar;
    TextView textViewRuta;
    String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};


    public FragmentImagen() {
        // Required empty public constructor
    }


    public static FragmentImagen newInstance() {
        FragmentImagen fragment = new FragmentImagen();

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
        View view = inflater.inflate(R.layout.fragment_fragment_imagen, container, false);
        imageView = view.findViewById(R.id.imageViewImageFragment);
        buttonAbrir = view.findViewById(R.id.buttonImagenAbrir);
        buttonGenerar = view.findViewById(R.id.buttonImagenCapturar);
        textViewRuta = view.findViewById(R.id.textViewRuta);
        buttonAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent,REQUEST_CODE_BRING_PHOTO);
            }
        });

        buttonGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagenFolder = new File(Environment.getExternalStorageDirectory(),"CameraFolder");
                imagenFolder.mkdir();
                File imagen = new File(imagenFolder,"foto.jpg");
                Uri uriImagen = Uri.fromFile(imagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uriImagen);
                getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_IMAGE_CAPTURE);
            }
        });

        return view;
    }

    public void setImage(Bitmap bitmap,String path){
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(bitmap);
        textViewRuta.setText("Ruta:"+path);
    }


}
