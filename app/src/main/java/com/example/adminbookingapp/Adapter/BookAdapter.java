package com.example.adminbookingapp.Adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminbookingapp.Model.Booked;
import com.example.adminbookingapp.Model.User;
import com.example.adminbookingapp.Owner.DetailBookedActivity;
import com.example.adminbookingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    List<Booked> list;

    public BookAdapter(List<Booked> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booked booked = list.get(position);

        holder.tenks.setText(booked.getTenks());
        holder.ngayden.setText(booked.getNgayden());
        holder.ngaydi.setText(booked.getNgaydi());
        holder.tongtien.setText(booked.getTongtien());
        holder.tenkh.setText(booked.getTenkhachhang());

        holder.ln_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbk = new Intent(view.getContext(), DetailBookedActivity.class);
                intentbk.putExtra("detailbooked", booked);
                view.getContext().startActivity(intentbk);
            }
        });

        if (!booked.getStatus()) {
            Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_circle_24);
            holder.statusks.setImageDrawable(drawable);
        } else {
            Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_circle_24_green);
            holder.statusks.setImageDrawable(drawable);
        }

        holder.onTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("phongdadat");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Booked booked1 = child.getValue(Booked.class);
                            if (booked1.getTenks().equals(booked.getTenks())) {
                                reference.child(child.getKey()).child("status").setValue(true);
                                Toast.makeText(v.getContext(), "Đã duyệt đơn hàng!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(v.getContext(), "Warning!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenks, ngayden, ngaydi, tongtien, tenkh, onTrangThai;
        RelativeLayout ln_linear;
        ImageView statusks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            onTrangThai = itemView.findViewById(R.id.duyetdon);
            statusks = itemView.findViewById(R.id.statusbooked);
            tenks = itemView.findViewById(R.id.tenkstext2);
            tenkh = itemView.findViewById(R.id.tenkhachhang);
            ln_linear = itemView.findViewById(R.id.deltaRelative);
            tongtien = itemView.findViewById(R.id.itemtongtien);
            ngayden = itemView.findViewById(R.id.txt_ngayden);
            ngaydi = itemView.findViewById(R.id.txt_ngaydi);
        }
    }
}
