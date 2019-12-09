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

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PasswordRecyclerAdapter extends RecyclerView.Adapter<PasswordRecyclerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<UserPasswordDB> userPasswordDBList;
    private Context mcontext;
    private boolean isChecked=true;

    PasswordRecyclerAdapter(Context context, List<UserPasswordDB> userPasswordDBList) {
        this.userPasswordDBList = userPasswordDBList;
        inflater = LayoutInflater.from(context);
        mcontext = context;
    }

    public PasswordRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_view,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final PasswordRecyclerAdapter.ViewHolder holder, final int position){
        final UserPasswordDB userPasswordDB=userPasswordDBList.get(position);

        holder.tvName.setText(userPasswordDB.getName());
        holder.tvLogin.setText(userPasswordDB.getLogin());
        holder.tvPassword.setText(userPasswordDB.getPassword());
        holder.tvID.setText("ID: "+userPasswordDB.getId().toString());
        holder.bind(userPasswordDB);

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



    public void addItem(UserPasswordDB userPasswordDB){
        userPasswordDBList.add(userPasswordDBList.size(),userPasswordDB);
        notifyItemInserted(userPasswordDBList.size());

    }

    public void addItem(UserPasswordDB userPasswordDB,int position){
        userPasswordDBList.add(position,userPasswordDB);
        notifyItemInserted(position);

    }

    @Override
    public int getItemCount(){
        return userPasswordDBList.size();
    }

    public void deleteItem(int position){
        userPasswordDBList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,userPasswordDBList.size());
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

        public void bind (final UserPasswordDB item){
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    EventBus.getDefault().post(new AddEvent(item));
                    return false;
                }
            });
        }
    }

    public Context getContext(){
        return mcontext;
    }



}
