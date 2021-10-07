package com.adiuvo.topsevalidator;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiuvo.topsevalidator.tabFrags.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class myadapterimages extends FirebaseRecyclerAdapter<modelImages,myadapterimages.myviewholder> {
    Context mContext;
    String TAG="myadapterimages.java";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userName;
    String uid;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public myadapterimages(@NonNull FirebaseRecyclerOptions<modelImages> options, Context context,String userName) {
        super(options);
        Log.d(TAG, "myadapterimages: ");
        mContext=context;
        sharedPreferences= mContext.getSharedPreferences("com.adiuvo.topsevalidator",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        this.userName=userName;
        uid=FirebaseAuth.getInstance().getUid();
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull modelImages model) {
        String date = "";
        Log.d(TAG, "myadapterimages: "+model.getSrfid());
        holder.oprName.setText(model.getOperator().split(":")[0].replaceAll(" ","")+"   "+model.getOperator().split(":")[1].replaceAll(" ",""));
        holder.reportStatus.setText(model.getVerification());
        try {
            Date date1=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).parse(model.getDatetime());
            SimpleDateFormat sdf= new SimpleDateFormat("dd.MM.yyyy-HH:mm", Locale.getDefault());
            date=sdf.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.reportDate.setText(date);
        holder.Srfid.setText(model.getSrfid());
        holder.materialCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                holder.materialCardView.setBackgroundColor(mContext.getResources().getColor(R.color.accent));
                return false;
            }
        });
        holder.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle(model.getSrfid())
                        .setMessage("If The Report is accurate Consent Accept, if not press reject.")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                verifiedImages ver=new verifiedImages(model.getUrl(), "Verified", model.getOperator(), model.getDatetime(), model.getSrfid(), model.getTreename(),userName );
                                String mapChildLoc=(model.getTreename()).replace(model.srfid,"");
                                mapChildLoc=mapChildLoc.replace(".pdf","");
                                Log.d(TAG, "onClick: Reduced mapChildLoc"+mapChildLoc);
                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verified").child(model.getSrfid()).setValue(ver);
                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verification").child(model.getTreename().replace(".pdf","")).setValue(null);
//                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verified").child()
                                if(sharedPreferences.getInt(Constants.NumberOfReportsVerified,0)==0){
                                    FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsVerified).setValue(1);
                                    editor.putInt(Constants.NumberOfReportsVerified,1);
                                    editor.apply();
                                    editor.commit();
                                }else{
                                    editor.putInt(Constants.NumberOfReportsVerified,sharedPreferences.getInt(Constants.NumberOfReportsVerified,0)+1);
                                    FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsVerified).setValue(sharedPreferences.getInt(Constants.NumberOfReportsVerified,0)+1);
                                    editor.apply();
                                    editor.commit();
                                }
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                verifiedImages ver=new verifiedImages(model.getUrl(), "Verified", model.getOperator(), model.getDatetime(), model.getSrfid(), model.getTreename(),userName );
                                String mapChildLoc=(model.getTreename()).replace(model.srfid,"");
                                mapChildLoc=mapChildLoc.replace(".pdf","");

                                Log.d(TAG, "onClick: Reduced mapChildLoc"+mapChildLoc);
                                FirebaseDatabase.getInstance().getReference().child(uid).child("SrfMap").child(model.srfid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        int Count=snapshot.child("sessionCount").getValue(int.class);
                                        FirebaseDatabase.getInstance().getReference().child(uid).child("SrfMap").child(model.getSrfid()).child("sessionCount").setValue(Count);
                                        FirebaseDatabase.getInstance().getReference().child(uid).child("Rejected").child(model.getSrfid()).setValue(ver);
                                        FirebaseDatabase.getInstance().getReference().child(uid).child("Verification").child(model.getTreename().replace(".pdf","")).setValue(null);
                                        if(sharedPreferences.getInt(Constants.NumberOfReportsRejected,0)==0){
                                            FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsRejected).setValue(1);
                                            editor.putInt(Constants.NumberOfReportsRejected,1);
                                            editor.apply();
                                            editor.commit();
                                        }else{
                                            editor.putInt(Constants.NumberOfReportsVerified,sharedPreferences.getInt(Constants.NumberOfReportsRejected,0)+1);
                                            FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsRejected).setValue(sharedPreferences.getInt(Constants.NumberOfReportsRejected,0)+1);
                                            editor.apply();
                                            editor.commit();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });

//                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verified").child()
                            }
                        })
                        .setIcon(mContext.getDrawable(R.drawable.icons8_id_verified_96))
                        .show();
            }
        });
        holder.viewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    mContext.startActivity(intent);
                }
                new AlertDialog.Builder(mContext)
                        .setTitle(model.getSrfid())
                        .setMessage("If The Report is accurate Consent Accept, if not press reject.")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                verifiedImages ver=new verifiedImages(model.getUrl(), "Verified", model.getOperator(), model.getDatetime(), model.getSrfid(), model.getTreename(),userName );
                                String mapChildLoc=(model.getTreename()).replace(model.srfid,"");
                                mapChildLoc=mapChildLoc.replace(".pdf","");
                                Log.d(TAG, "onClick: Reduced mapChildLoc"+mapChildLoc);
                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verified").child(model.getSrfid()).setValue(ver);

                                if(sharedPreferences.getInt(Constants.NumberOfReportsVerified,0)==0){
                                    FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsVerified).setValue(1);
                                    editor.putInt(Constants.NumberOfReportsVerified,1);
                                    editor.apply();
                                    editor.commit();
                                }else{
                                    editor.putInt(Constants.NumberOfReportsVerified,sharedPreferences.getInt(Constants.NumberOfReportsVerified,0)+1);
                                    FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsVerified).setValue(sharedPreferences.getInt(Constants.NumberOfReportsVerified,0)+1);
                                    editor.apply();
                                    editor.commit();
                                }

                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verification").child(model.getTreename().replace(".pdf","")).setValue(null);
//                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verified").child()
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                verifiedImages ver=new verifiedImages(model.getUrl(), "Verified", model.getOperator(), model.getDatetime(), model.getSrfid(), model.getTreename(),userName );
                                String mapChildLoc=(model.getTreename()).replace(model.srfid,"");
                                mapChildLoc=mapChildLoc.replace(".pdf","");

                                Log.d(TAG, "onClick: Reduced mapChildLoc"+mapChildLoc);
                                FirebaseDatabase.getInstance().getReference().child(uid).child("SrfMap").child(model.getSrfid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        int Count=snapshot.child("sessionCount").getValue(int.class);
                                        FirebaseDatabase.getInstance().getReference().child(uid).child("SrfMap").child(model.getSrfid()).child("sessionCount").setValue(Count);
                                        FirebaseDatabase.getInstance().getReference().child(uid).child("Rejected").child(model.getSrfid()).setValue(ver);
                                        FirebaseDatabase.getInstance().getReference().child(uid).child("Verification").child(model.getTreename().replace(".pdf","")).setValue(null);
                                        if(sharedPreferences.getInt(Constants.NumberOfReportsRejected,0)==0){
                                            FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsRejected).setValue(1);
                                            editor.putInt(Constants.NumberOfReportsRejected,1);
                                            editor.apply();
                                            editor.commit();
                                        }else{
                                            editor.putInt(Constants.NumberOfReportsVerified,sharedPreferences.getInt(Constants.NumberOfReportsRejected,0)+1);
                                            FirebaseDatabase.getInstance().getReference().child(uid).child(Constants.GeneralUserAnalytics).child(Constants.NumberOfReportsRejected).setValue(sharedPreferences.getInt(Constants.NumberOfReportsRejected,0)+1);
                                            editor.apply();
                                            editor.commit();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                });

//                                FirebaseDatabase.getInstance().getReference().child(uid).child("Verified").child()
                            }
                        })
                        .setIcon(mContext.getDrawable(R.drawable.icons8_id_verified_96))
                        .show();

            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientreportrow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView Srfid,reportDate,reportStatus,oprName;
        MaterialButton viewReport,verify;
        MaterialCardView materialCardView;
        View v;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            Srfid= (TextView)itemView.findViewById(R.id.Srfid);
            materialCardView=itemView.findViewById(R.id.recyclerParent);
            reportDate=(TextView)itemView.findViewById(R.id.report_date);
            reportStatus=(TextView)itemView.findViewById(R.id.report_Status_view);
            oprName=(TextView)itemView.findViewById(R.id.oprname);
            viewReport=itemView.findViewById(R.id.viewReport);
            verify=itemView.findViewById(R.id.verify);
            v=itemView;
        }
    }
}
