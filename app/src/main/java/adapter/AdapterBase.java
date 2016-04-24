package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import edu.fs.com.futuresociety.R;

/**
 * Created by gournandi on 4/06/16.
 */
public  class AdapterBase  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LinkedList<String> list = new LinkedList<>();
    private View.OnClickListener onClickListener = null;

    public AdapterBase(LinkedList<String> list , View.OnClickListener onClickListener)
    {
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_courses, parent, false);
        return new BaseViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseHolder = (BaseViewHolder)holder;
        baseHolder.tvName.setText(list.get(position));
        baseHolder.container.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        if(list !=null && list.size()>0)
        return list.size();
        else
            return 0;
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private View container;

        public BaseViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView)itemView.findViewById(R.id.tv_adapter_item_course_name);
            container = itemView.findViewById(R.id.ll_item);
        }
    }

}
