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

/**
 * Created by gournandi on 4/06/16.
 */
public class FragmentTopics extends FragmentBase implements View.OnClickListener{


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
        list.add("Topic 1");
        list.add("Topic 2");
        list.add("Topic 3");
        return list;
    }


    @Override
    public void onClick(View v){

        switch(v.getId())
        {
            case R.id.ll_item:

                Bundle bundle=new Bundle();
                bundle.putString("TOPIC_NAME",(String)v.getTag());
                AddFragment.AddFragmentActivityStack(getActivity(),bundle,FragmentLessons.class);

                break;
        }

    }
}