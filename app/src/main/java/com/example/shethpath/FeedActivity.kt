package com.example.shethpath

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class FeedActivity : AppCompatActivity() {

    private lateinit var rvPosts: RecyclerView
    lateinit var adapter: FeedAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_view)
//        rvPosts = findViewById(R.id.rvFeed)

        queryPosts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOut) {
            ParseUser.logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        if (item.itemId == R.id.compostPost) {
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)

        }

        return super.onOptionsItemSelected(item)
    }


    // Query for all posts in our server
    fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {

                if (e != null) {
                    Log.e(MainActivity.TAG, "Error fetching posts")
                } else {
                    if (posts != null) {

                        for (post in posts) {
                            Log.i(MainActivity.TAG, "Post: " + post.getDescription() + " , username: " + post.getUser()?.username)
                        }
                    }
                }
            }
        })
    }

}