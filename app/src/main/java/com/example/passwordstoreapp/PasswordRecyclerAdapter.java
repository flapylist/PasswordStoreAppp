package com.example.passwordstoreapp;

import android.content.Context;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordstoreapp.Database.Password;
import com.example.passwordstoreapp.EventBusStaff.EditEvent;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PasswordRecyclerAdapter extends RecyclerView.Adapter<PasswordRecyclerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Password> passwordList;
    private Context mcontext;

    PasswordRecyclerAdapter(Context context, List<Password> passwordList) {
        this.passwordList=passwordList;
        inflater = LayoutInflater.from(context);
        mcontext = context;
    }

    public PasswordRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_view,parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final PasswordRecyclerAdapter.ViewHolder holder, final int position){
        final Password password=passwordList.get(position);

        holder.tvName.setText(password.getName());
        holder.tvLogin.setText(password.getLogin());
        holder.tvPassword.setText(password.getPassword());
        holder.tvID.setText("ID: "+password.getPassword());

        holder.setViewOnClick(password,position);

        holder.chkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(holder.chkPassword.isChecked())
                holder.tvPassword.setTransformationMethod(null);
                if(!holder.chkPassword.isChecked())
                    holder.tvPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
        }
        );
    }


    @Override
    public int getItemCount(){
        return passwordList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLogin, tvPassword,tvID;
        CheckBox chkPassword;

        public ViewHolder(View view) {
            super(view);
            tvID=view.findViewById(R.id.ID);
            tvName=view.findViewById(R.id.tvName);
            tvLogin=view.findViewById(R.id.tvLogin);
            tvPassword=view.findViewById(R.id.tvPassword);
            chkPassword=view.findViewById(R.id.chkPassword);
        }

        public void setViewOnClick(final Password item, final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EditEvent(item,position));
                }
            });
        }
    }

    public Context getContext(){
        return mcontext;
    }



}
