package com.example.adminbookingapp.Adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminbookingapp.Admin.DetailHotelActivity;
import com.example.adminbookingapp.Model.Khachsan;
import com.example.adminbookingapp.Model.User;
import com.example.adminbookingapp.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    List<User> list;
    public UserAdapter(List<User> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder (v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);

        holder.tenuser.setText(user.getUsername());
        holder.emailuser.setText(user.getEmail());
        holder.sdtuser.setText(user.getPhone());

        if (user.getImage().equals("")) {
            Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.ic_outline_account_circle_24);
            holder.img.setImageDrawable(drawable);
        } else {
            Picasso.get().load(user.getImage())
                    .placeholder(R.drawable.ic_outline_account_circle_24)
                    .fit()
                    .noFade()
                    .centerCrop()
                    .into(holder.img);
        }

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbk = new Intent(view.getContext(), DetailHotelActivity.class);
                intentbk.putExtra("clickdetail", user);
                view.getContext().startActivity(intentbk);
            }
        });*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tenuser, emailuser, sdtuser;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            tenuser = itemView.findViewById(R.id.tenUser);
            emailuser = itemView.findViewById(R.id.emailUser);
            sdtuser = itemView.findViewById(R.id.sdtUser);
            relativeLayout = itemView.findViewById(R.id.ln_linear);
        }
    }
}
