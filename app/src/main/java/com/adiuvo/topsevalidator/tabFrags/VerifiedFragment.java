package com.adiuvo.topsevalidator.tabFrags;

import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.adiuvo.topsevalidator.R;
import com.adiuvo.topsevalidator.modelImages;
import com.adiuvo.topsevalidator.myVerifiedAdapterimages;
import com.adiuvo.topsevalidator.myadapterimages;
import com.adiuvo.topsevalidator.verifiedImages;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;

public class VerifiedFragment extends Fragment {
    RecyclerView recview;
    myVerifiedAdapterimages adapter;

    private SharedPreferences sharedPreferences;
    ImageView bird;
    LinearLayout birdlinear;
    private VerifiedViewModel mViewModel;

    public static VerifiedFragment newInstance() {
        return new VerifiedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.verified_fragment, container, false);
        birdlinear=root.findViewById(R.id.birdlinear);
        sharedPreferences= getContext().getSharedPreferences("com.adiuvo.topsevalidator",MODE_PRIVATE);
        bird=root.findViewById(R.id.birdnotfound);
        Glide.with(this).asGif().load(R.raw.bird).into(bird);
        recview=(RecyclerView) root.findViewById(R.id.recview2);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));


        FirebaseRecyclerOptions<verifiedImages> options
                = new FirebaseRecyclerOptions.Builder<verifiedImages>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("Verified"), verifiedImages.class)
                .build();

        adapter=new myVerifiedAdapterimages(options, getContext(),sharedPreferences.getString("userName",""));
        recview.setAdapter(adapter);
        adapter.startListening();
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("Verified").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    birdlinear.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }else{
                    birdlinear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerifiedViewModel.class);
        // TODO: Use the ViewModel
    }

}