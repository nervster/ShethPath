package com.example.shethpath

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shethpath.PostAdapter.*

class PostAdapter(val context: Context, val posts: ArrayList<Post>) : RecyclerView.Adapter<ViewHolder>() {
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvUsername: TextView
    val tvUsernameDescription: TextView
    val ivPostImage: ImageView
    val tvDescription: TextView
    val tvLikeCount: TextView

    init {
        tvUsername = itemView.findViewById(R.id.tvUsername)
        tvUsernameDescription = itemView.findViewById(R.id.tvUsernameDescription)
        ivPostImage = itemView.findViewById(R.id.ivPostPicture)
        tvDescription = itemView.findViewById(R.id.tvDescription)
        tvLikeCount = itemView.findViewById(R.id.tvLikeCount)
    }

        fun bind(post: Post) {
            tvDescription.text = post.getDescription()
            tvUsername.text = post.getUser()?.username
            tvUsernameDescription.text = post.getUser()?.username
            tvLikeCount.text = post.getLike().toString() + " Likes"
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivPostImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // which layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    // Clean all elements of the recycler
    fun clear() {
        posts.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(postList: List<Post>) {
        posts.addAll(postList)
        notifyDataSetChanged()
    }


}


