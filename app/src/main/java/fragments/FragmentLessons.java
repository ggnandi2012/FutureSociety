package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import adapter.AdapterBase;
import edu.fs.com.futuresociety.R;
import mediadecrypter.DecryptAndViewControl;
import mediadecrypter.Media;

/**
 * Created by gournandi on 4/06/16.
 */
public class FragmentLessons extends FragmentBase implements View.OnClickListener{

    private View mRootView;
    private RecyclerView recyclerView;

    private String courseName = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        mRootView=inflater.inflate(R.layout.recycler_view,container,false);


        init();
        return mRootView;
    }

    private void init()
    {
        recyclerView=(RecyclerView)mRootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterBase adapterBase=new AdapterBase(getCourseList(),this);
        recyclerView.setAdapter(adapterBase);
    }


    private LinkedList<String> getCourseList()
    {
        LinkedList<String>list=new LinkedList<>();
        list.add("Lesson 1");
        list.add("Lesson 2");
        list.add("Lesson 3");
        return list;
    }


    @Override
    public void onClick(View v){

        switch(v.getId())
        {
            case R.id.ll_item:


                String SDCARD_PATH = "/FT_Lesson/";

               String path = "/mnt/sdcard" + SDCARD_PATH
                        + "math_lesson1_encrypted.mp4";


                DecryptAndViewControl decryptView = new DecryptAndViewControl(getActivity());
                decryptView.setFileUrl(path , Media.TYPE_VIDEO,
                        "FT_lp");
                decryptView.setLaunchPlayer(true);
                //decryptView.setDecryptionListener(ge);
                decryptView.decryptAndView();
               //play video

                break;
        }

    }
}
