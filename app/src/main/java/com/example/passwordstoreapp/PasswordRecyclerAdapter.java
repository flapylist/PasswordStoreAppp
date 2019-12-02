package com.example.passwordstoreapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PasswordRecyclerAdapter extends RecyclerView.Adapter<PasswordRecyclerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<UserPasswordDB> userPasswordDBList;
    private Context mcontext;

    PasswordRecyclerAdapter(Context context, List<UserPasswordDB> userPasswordDBList) {
        this.userPasswordDBList = userPasswordDBList;
        inflater = LayoutInflater.from(context);
        mcontext = context;
    }

    public PasswordRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_view,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(PasswordRecyclerAdapter.ViewHolder holder, final int position){
        final UserPasswordDB userPasswordDB=userPasswordDBList.get(position);

        holder.tvName.setText(userPasswordDB.getName());
        holder.tvLogin.setText(userPasswordDB.getLogin());
        holder.tvPassword.setText(userPasswordDB.getPassword());
        holder.tvID.setText("ID: "+userPasswordDB.getId());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mcontext instanceof ListActivity){
                    ((ListActivity) mcontext).presentAlert(userPasswordDB.getName(),userPasswordDB.getLogin(), userPasswordDB.getPassword(),
                            userPasswordDB.getId());
                    notifyDataSetChanged();
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentAlertDelete(position, userPasswordDB.getId());
            }
        });
    }

    public void presentAlertDelete(final int position, final Long id){
        AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
        builder.setTitle("Delete")
                .setMessage("Are you really want delete record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.getInstance(mcontext).deleteItem(id);
                        deleteUser(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert=builder.create();
        alert.show();
    }

    public void updateUserList(List<UserPasswordDB> list){
        userPasswordDBList.clear();
        userPasswordDBList.addAll(list);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return userPasswordDBList.size();
    }

    public void deleteUser(int position){
        userPasswordDBList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,userPasswordDBList.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvName, tvLogin, tvPassword;
        Button btnEdit, btnDelete;

        public ViewHolder(View view) {
            super(view);
            tvID=view.findViewById(R.id.tvID);
            tvName=view.findViewById(R.id.tvName);
            tvLogin=view.findViewById(R.id.tvLogin);
            tvPassword=view.findViewById(R.id.tvPassword);
            btnEdit=view.findViewById(R.id.btnEdit);
            btnDelete=view.findViewById(R.id.btnDelete);
        }
    }
}
