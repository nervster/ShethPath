package com.example.shethpath.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shethpath.Post
import com.example.shethpath.PostAdapter
import com.example.shethpath.ProfilePostAdapter
import com.example.shethpath.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment: HomeFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This is where we set up our views and click listeners
        postsRV = view.findViewById(R.id.rvHome)
        swipeContainer = view.findViewById(R.id.swipeContainer)

        // Steps to populate RV
        // 1. Create layout for each row in list - Done
        // 2. Create data source for each row (this is the Post class)
        // 3. Create adapter that will bridge data and row layout -- Done
        // 4. Set adapter on RecyclerView
        adapterProf = ProfilePostAdapter(requireContext(), allPosts)
        postsRV.adapter = adapterProf
        // 5. Set layout manager on RecyclerView
        postsRV.layoutManager = GridLayoutManager(requireContext(), 3)

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
//            fetchTimelineAsync(0)
            allPosts.clear()
            queryPosts()
            swipeContainer.isRefreshing = false

        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        queryPosts()
    }

    override fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")
//        query.limit = 20
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {

                if (e != null) {
                    Log.e(TAG, "Error fetching posts")
                } else {
                    if (posts != null) {
                        for (post in posts) {
                            Log.i(TAG, "Post: " + post.getDescription() + " , username: " + post.getUser()?.username)
                        }

                        allPosts.addAll(posts)
                        adapterProf.notifyDataSetChanged()
                    }

                }
            }
        })

    }

     companion object {
        const val TAG = "ProfileFragment"
    }
}