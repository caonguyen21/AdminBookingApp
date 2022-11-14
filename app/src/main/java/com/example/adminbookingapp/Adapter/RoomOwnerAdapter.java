package com.example.adminbookingapp.Adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminbookingapp.Admin.DetailHotelActivity;
import com.example.adminbookingapp.Model.Khachsan;
import com.example.adminbookingapp.Owner.QLKS_OwnerFragment;
import com.example.adminbookingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RoomOwnerAdapter extends RecyclerView.Adapter<RoomOwnerAdapter.ViewHolder> {
    List<Khachsan> list;
    QLKS_OwnerFragment context;

    public RoomOwnerAdapter(QLKS_OwnerFragment context, List<Khachsan> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_owner, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Khachsan ks = list.get(position);

        holder.tenks.setText(ks.getTenks());
        holder.diachi.setText(ks.getDiachi());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String get_gia = currencyVN.format(Integer.parseInt(ks.getGia()));
        holder.gia.setText(get_gia);

        if (ks.getHinh().equals("")) {
            Drawable drawable = holder.itemView.getContext().getDrawable(R.drawable.ic_baseline_image_24);
            holder.img.setImageDrawable(drawable);
        } else {
            Picasso.get().load(ks.getHinh())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .fit()
                    .noFade()
                    .centerCrop()
                    .into(holder.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentbk = new Intent(view.getContext(), DetailHotelActivity.class);
                intentbk.putExtra("clickdetail", ks);
                view.getContext().startActivity(intentbk);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tenks, diachi, gia;
        RelativeLayout ln_linear;
        ImageView statusks;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusks = itemView.findViewById(R.id.statusks);
            img = itemView.findViewById(R.id.img1);
            tenks = itemView.findViewById(R.id.tenkstext);
            diachi = itemView.findViewById(R.id.diachitext);
            gia = itemView.findViewById(R.id.giatext);
            ln_linear = itemView.findViewById(R.id.ln_linear);
        }
    }
}
