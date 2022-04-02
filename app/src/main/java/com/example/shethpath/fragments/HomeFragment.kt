package com.example.shethpath.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shethpath.*
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class HomeFragment : Fragment() {

    lateinit var postsRV: RecyclerView
    lateinit var adapter: PostAdapter
    lateinit var adapterProf: ProfilePostAdapter
    var allPosts: ArrayList<Post> = ArrayList()
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

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
        adapter = PostAdapter(requireContext(), allPosts)
        postsRV.adapter = adapter
        // 5. Set layout manager on RecyclerView
        postsRV.layoutManager = LinearLayoutManager(requireContext())

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

    open fun queryPosts( ) {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.limit = 20
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
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        })
    }



    companion object {
        const val TAG = "HomeFragment"
    }

}