package com.example.shethpath

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter(private val context: Context, private val posts: MutableList<Post>?):
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val postView = android.view.LayoutInflater.from(parent.context).inflate(R.layout.feed_view, parent, false)
        return ViewHolder(postView)
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        val post = posts?.get(position)
        if (post != null) {
            holder.bind(post)
        }
    }

    override fun getItemCount(): Int {
        return posts?.size ?:0
    }

    inner class ViewHolder(postView: View):RecyclerView.ViewHolder(postView) {
        private val usernameTitle = postView.findViewById<TextView>(R.id.tvUsername) as TextView
        private val usernameDescription = postView.findViewById<TextView>(R.id.tvUsernameDescription) as TextView
        private val postImage = postView.findViewById<ImageView>(R.id.ivPostPicture) as ImageView
        private val postDescription = postView.findViewById<TextView>(R.id.tvDescription) as TextView

        fun bind(post: Post) {
            usernameTitle.text = post.getUser()?.username
            usernameDescription.text = post.getUser()?.username
            postDescription.text = post.getDescription()
        }
    }


}