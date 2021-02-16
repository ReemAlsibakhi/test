package com.Test.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Test.R;
import com.Test.model.Message;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static  final int MSG_TYPE_RIGHT = 1;
    public static  final int MSG_TYPE_LEFT = 0;
    private Activity mContext;
    private ArrayList<Message> msgsList;


    public interface OnItemClickListener {
        void onLongClicked(Integer position);
    }

    public MessageAdapter(Activity mContext, ArrayList<Message> msgsList) {
        this.mContext = mContext;
        this.msgsList = msgsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_sent_message, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_recieved_message, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMsg.setText(msgsList.get(position).getContent());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showAletDialog(position);
                return false;
            }
        });
    }

    private void showAletDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("The selected row is: "+position+"The database id id:")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        msgsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, msgsList.size());
                        notifyDataSetChanged();
                      //  mContext.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Do you want to delete this?");
        alert.show();
    }

    @Override
    public int getItemCount() {
        return msgsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     TextView tvMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
       tvMsg=itemView.findViewById(R.id.tvMsg);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (msgsList.get(position).getIsSend()) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }

    }
}
