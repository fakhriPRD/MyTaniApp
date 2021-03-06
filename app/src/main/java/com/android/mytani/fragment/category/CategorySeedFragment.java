package com.android.mytani.fragment.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.mytani.R;
import com.android.mytani.adapter.PostAdapter;
import com.android.mytani.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategorySeedFragment extends Fragment {

    // firebase variables
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databasePostRef;

    PostAdapter postAdapter;
    RecyclerView rv_seedCat;
    SearchView searchview_catSeed;
    RelativeLayout relative_illustration_notfound;

    // list
    List<Post> filteredPostList;
    List<Post> postList;

    boolean dataExist = false;

    public CategorySeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // get list posts from database
        filteredPostList = new ArrayList<>();
        Query query = databasePostRef.orderByChild("category").equalTo("Biji");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    postList = new ArrayList<>();
                    if (snapshot.exists()){
                        dataExist = true;
                        for (DataSnapshot postnap : snapshot.getChildren()){
                            Post post = postnap.getValue(Post.class);
                            postList.add(post);
                        }
                        postAdapter = new PostAdapter(getActivity(), postList);
                        rv_seedCat.setAdapter(postAdapter);
                    } else {
                        searchview_catSeed.setVisibility(View.INVISIBLE);
                        relative_illustration_notfound.setVisibility(View.VISIBLE);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_seed, container, false);

        // initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databasePostRef = firebaseDatabase.getReference("posts");

        // initialize recyclerview
        rv_seedCat = view.findViewById(R.id.rv_seedCat);
        relative_illustration_notfound = view.findViewById(R.id.relative_illustration_notfound);
        rv_seedCat.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_seedCat.hasFixedSize();

        searchview_catSeed = view.findViewById(R.id.searchview_catSeed);
        if (dataExist){

            searchview_catSeed.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    postAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }


        return view;
    }
}