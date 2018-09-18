package misiont.mision6.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import misiont.mision6.Fragments.AnimationFragment;
import misiont.mision6.Fragments.FragmentAudio;
import misiont.mision6.Fragments.FragmentGrafico;
import misiont.mision6.Fragments.FragmentImagen;
import misiont.mision6.Fragments.FragmentVideo;
import misiont.mision6.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Fragment fragment;
    public final static int REQUEST_CODE = 1;
    public final static int REQUEST_CODE_BRING_PHOTO = 2;
    public final static int REQUEST_CODE_IMAGE_CAPTURE = 3;
    public final static int REQUEST_CODE_BRING_AUDIO = 4;
    public final static int REQUEST_CODE_BRING_VIDEO = 5;
    public final static int REQUEST_CODE_RECORD_VIDEO = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout  = (DrawerLayout)findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawerAbierto,R.string.drawerCerrado);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragment = AnimationFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,fragment)
                .commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.drawerAnim:
                fragment = AnimationFragment.newInstance();
                break;
            case R.id.drawerGraficos:
                fragment = FragmentGrafico.newInstance();
                break;
            case R.id.drawerImaget:
                fragment = FragmentImagen.newInstance();
                break;
            case R.id.drawerAudio:
                fragment = FragmentAudio.newInstance();
                break;
            case R.id.drawerVideo:
                fragment = FragmentVideo.newInstance();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(null)
                .commit();
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int i = 0;
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_BRING_PHOTO:
                    Uri uri = data.getData();
                    String path[] = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,path,null,null,null);
                    cursor.moveToFirst();
                    int columna = cursor.getColumnIndex(path[0]);
                    String pathImagen = cursor.getString(columna);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(pathImagen);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    if(bitmap != null) {
                        int height = bitmap.getHeight();
                        int width = bitmap.getWidth();
                        float scaleX = 0.5f;
                        float scaleY = 0.5f;
                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleX, scaleY);
                        Bitmap nuevaImagen = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                        ((FragmentImagen) fragment).setImage(nuevaImagen,pathImagen);
                    }

                    break;
                case REQUEST_CODE_IMAGE_CAPTURE:
                    Bitmap bitmap1 = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/CameraFolder/foto.jpg");
                    if(bitmap1 != null) {
                        int height = bitmap1.getHeight();
                        int width = bitmap1.getWidth();
                        float scaleX = 0.5f;
                        float scaleY = 0.5f;
                        Matrix matrix = new Matrix();
                        matrix.postScale(scaleX, scaleY);
                        Bitmap nuevaImagen = Bitmap.createBitmap(bitmap1, 0, 0, width, height, matrix, true);
                        ((FragmentImagen) fragment).setImage(nuevaImagen,Environment.getExternalStorageDirectory()+"/CameraFolder/foto.jpg");
                    }


                    break;
                case REQUEST_CODE_BRING_AUDIO:
                    ((FragmentAudio)fragment).setAudioResource(data.getDataString());
                    break;
                case REQUEST_CODE_BRING_VIDEO:
                    ((FragmentVideo)fragment).setVideoResources(data.getDataString());
                case REQUEST_CODE_RECORD_VIDEO:
                    ((FragmentVideo)fragment).setVideoResources(data.getDataString());
                    break;
            }
        }


    }


}
