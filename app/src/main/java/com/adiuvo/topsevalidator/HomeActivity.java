package com.adiuvo.topsevalidator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adiuvo.topsevalidator.tabFrags.PendingFragment;
import com.adiuvo.topsevalidator.tabFrags.VerifiedFragment;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PendingFragment pendingFragment;
    private VerifiedFragment verifiedFragment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AlertDialog alert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences= getSharedPreferences("com.adiuvo.topsevalidator",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.getString("userName","").equals("")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
            alertDialog.setTitle("Select Validator");
            String[] items = {"Dr.Akhauri Yash Sinha","Dr.Shalu Verma Kumar"};
            int checkedItem = 1;
            alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            editor.putString("userName","Dr.Akhauri Yash Sinha");
                            editor.apply();
                            editor.commit();
                            Toast.makeText(HomeActivity.this, "Current User Dr.Akhauri Yash Sinha", Toast.LENGTH_LONG).show();
                            triggerSelected();
                            break;
                        case 1:
                            editor.putString("userName","Dr.Shalu Verma Kumar");
                            editor.apply();
                            editor.commit();
                            Toast.makeText(HomeActivity.this, "Current User Dr.Shalu Verma Kumar", Toast.LENGTH_LONG).show();

                            break;

                    }
                }
            });

            alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(true);
            alert.show();
        }
        setContentView(R.layout.activity_home);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.view_pager);
        pendingFragment= new PendingFragment();
        verifiedFragment=new VerifiedFragment();
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(pendingFragment,"");
        viewPagerAdapter.addFragment(verifiedFragment,"");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).setText("Pending");
        tabLayout.getTabAt(1).setText("Verified");
        tabLayout.getTabAt(0).setIcon(R.drawable.icons8_data_pending_96);
        tabLayout.getTabAt(1).setIcon(R.drawable.icons8_id_verified_96);
        FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getUid()).child("Verification").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if (snapshot.hasChildren()){
                        BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(Math.toIntExact(snapshot.getChildrenCount()));
                    }
                }else{
                    BadgeDrawable badgeDrawable = tabLayout.getTabAt(0).getOrCreateBadge();
                    badgeDrawable.setVisible(false );
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void triggerSelected() {
        if(alert!=null){
            if(alert.isShowing()){
                alert.dismiss();
            };
        }
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        //add fragment to the viewpager
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        //to setup title of the tab layout
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}