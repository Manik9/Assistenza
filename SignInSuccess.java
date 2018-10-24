package binarykeys.com.attendance;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignInSuccess extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;
    private FloatingActionButton mFloatingActionButton;
    private Button mLogout;
    private String recievedRollNumber;
    private DatabaseReference rollNumberData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setContentView(R.layout.recyclerview);

        mLogout=(Button)findViewById(R.id.logout);
        mFloatingActionButton=(FloatingActionButton) findViewById(R.id.AddFloatingButton);
        mAuth = FirebaseAuth.getInstance();
        getRollNumber();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("16071A0563");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInSuccess.this,NewCard.class));
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SignInSuccess.this,LoginScreen.class));
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });

        mDatabase.keepSynced(true);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<Card,CardViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Card, CardViewHolder>(Card.class,
                R.layout.cardlayout,
                CardViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(CardViewHolder viewHolder, Card model, final int position) {

                viewHolder.setAttbyTot(model.getAttbyTot());
                viewHolder.setPer(model.getPer());
                viewHolder.setSub(model.getSub());
          }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    public static class CardViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public CardViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setSub(String sub) {
            TextView subject=(TextView)mView.findViewById(R.id.fac_sub);
            subject.setText(sub);
        }

        public void setPer(String percent){
            TextView perce=(TextView)mView.findViewById(R.id.att_percent);
            if(percent.length()>=5)
                perce.setText(percent.substring(0,5)+"%");
            else
                perce.setText(percent);
        }
        public void setAttbyTot(String aat){
            TextView aatbytot=(TextView)mView.findViewById(R.id.attbytot);
            aatbytot.setText(aat);
        }

    }


    private void getRollNumber(){
        rollNumberData=FirebaseDatabase.getInstance().getReference().child("UsersUID").child(mAuth.getCurrentUser().getUid()).child("RollNumber");
        rollNumberData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recievedRollNumber= dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }






}
