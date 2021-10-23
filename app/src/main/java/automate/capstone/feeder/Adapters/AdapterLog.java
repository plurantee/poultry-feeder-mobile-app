package automate.capstone.feeder.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import automate.capstone.feeder.DataRecycler.DataLog;
import automate.capstone.feeder.R;

/**
 * Created by Donnald on 2/3/2018.
 */

public class AdapterLog extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<DataLog> data = Collections.emptyList();
    DataLog current;
    //int currentPos = 0;

    // create constructor to initilize context and data sent from MainActivity
    public AdapterLog(Context context, List<DataLog> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.log_row_layout, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        myHolder.tvLogInfo.setText(current.loginfo);
        myHolder.tvLogType.setText(current.logtype);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvLogType;
        TextView tvLogInfo;
        Button btnViewInfo;
        public MyHolder(View itemView) {
            super(itemView);
            tvLogType = (TextView) itemView.findViewById(R.id.tv_log_type);
            tvLogInfo = (TextView) itemView.findViewById(R.id.tv_log_info);
            btnViewInfo = (Button) itemView.findViewById(R.id.btn_log);
            btnViewInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tvMessage = "";
                    if (tvLogInfo.getText().toString().equals("Feeding Activity Was Done (Manual)")){
                        tvMessage= tvLogType.getText().toString();
                    } else {
                        tvMessage= tvLogType.getText().toString();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(tvMessage)
                            .setCancelable(true)
                            .setTitle(tvLogInfo.getText().toString())
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                }
            });
        }
    }

}
