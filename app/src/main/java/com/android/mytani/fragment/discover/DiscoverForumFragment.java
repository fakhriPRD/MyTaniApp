package com.android.mytani.fragment.discover;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.mytani.R;
import com.android.mytani.adapter.PostAdapter;
import com.android.mytani.models.Post;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DiscoverForumFragment extends Fragment {

    // layout variables
    SearchView searchview_discoverForum;


    String postKey;

    // recylerview variables
    RecyclerView rc_discover_forum;
    PostAdapter postAdapter;


    // firebase variables
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databasePostReference;
    /*FirebaseRecyclerOptions<Post> options_post;
    FirebaseRecyclerAdapter<Post, PostViewHolder> post_adapter;*/

    // Post list
    List<Post> postList;
    List<Post> detailPostList;

    public DiscoverForumFragment() {
        // Required empty public constructor
        // ini coba commit
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_discover_forum, container, false);

        // initialize view
        searchview_discoverForum = view.findViewById(R.id.searchview_discoverForum);
        rc_discover_forum = view.findViewById(R.id.rc_discover_forum);
        searchview_discoverForum = view.findViewById(R.id.searchview_discoverForum);

        // initialize reycclerview
        rc_discover_forum.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc_discover_forum.hasFixedSize();

        searchview_discoverForum.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        // initialize detailPostList
        detailPostList = new ArrayList<Post>();

        // initialize firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databasePostReference = firebaseDatabase.getReference("posts");
        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        // get list posts from database
        databasePostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList = new ArrayList<>();
                for (DataSnapshot postnap : snapshot.getChildren()){
                    Post post = postnap.getValue(Post.class);
                    postList.add(post);
                }
                postAdapter = new PostAdapter(getActivity(), postList);
                rc_discover_forum.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showLog(String msg) {
        Log.d("FRAGMENT DISCOVER FORUM",msg);
    }

}