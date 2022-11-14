package com.example.adminbookingapp.Owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminbookingapp.Adapter.RoomOwnerAdapter;
import com.example.adminbookingapp.Model.Khachsan;
import com.example.adminbookingapp.Model.Owner;
import com.example.adminbookingapp.Model.User;
import com.example.adminbookingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QLKS_OwnerFragment extends Fragment {
    TextView idxinchao, empty;
    Button empty2;
    DatabaseReference reference;
    RoomOwnerAdapter roomOwnerAdapter;
    List<Khachsan> room;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    String tenks = "";
    private String userID;
    private FirebaseUser user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_k_s__owner, container, false);
        initUI(view);

        //Toolbar welcome
        wellcome();

        room = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("TPHCM");
        recyclerView = view.findViewById(R.id.rcv_khachsan);
        //phan cach giua cac item
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        roomOwnerAdapter = new RoomOwnerAdapter(this, room);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(roomOwnerAdapter);
        //thay doi layout khi co item recyclerview
        roomOwnerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (roomOwnerAdapter != null && view != null) {
                    if (roomOwnerAdapter.getItemCount() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        empty2.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.GONE);
                        empty2.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        gettenks();
        getListBook();
        return view;
    }

    private void initUI(View view) {
        idxinchao = view.findViewById(R.id.xinchao_id);
        empty = view.findViewById(R.id.text1);
        empty2 = view.findViewById(R.id.btnthemks);
    }

    private void gettenks() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Owner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    Owner owner = child.getValue(Owner.class);
                    tenks = owner.getTenks().trim();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getListBook() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                room.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Khachsan diaDiem = child.getValue(Khachsan.class);
                    if (tenks.equals(diaDiem.getTenks())) {
                        room.add(diaDiem);
                    }
                }
                roomOwnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void wellcome() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference("Owner");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                String ten = userprofile.username;
                idxinchao.setText("Xin chào, " + ten + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                idxinchao.setText("");
            }
        });
    }

}