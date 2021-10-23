package automate.capstone.feeder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import automate.capstone.feeder.DataRecycler.DataEditScheduleRecycler;
import automate.capstone.feeder.R;
import automate.capstone.feeder.Store;

/**
 * Created by Donnald on 2/7/2018.
 */

public class AdapterEditSchedule extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<String> timeArray = Collections.emptyList();
    String current;
    AdapterEditSchedule.MyHolder myHolder;
    Button removeTime;
    JSONObject obj = null;
    public AdapterEditSchedule(Context context, List<String> timeArray) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.timeArray = timeArray;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.edit_schedule_recycler_layout, parent,false);
        MyHolder holder = new AdapterEditSchedule.MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        myHolder = (AdapterEditSchedule.MyHolder) holder;
        current = timeArray.get(position);


        //JSONArray jsonSchedule= obj.getJSONArray("schedule");
        myHolder.tvTime.setText(current);
    }

    @Override
    public int getItemCount() {return timeArray.size();}


    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        public MyHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_edit_sched_recycler);
            removeTime = (Button) itemView.findViewById(R.id.btn_editmode_remove_time);
            removeTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeAt(getAdapterPosition());
                }
            });

        }
    }

    public void removeAt(int position) {
        timeArray.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}
