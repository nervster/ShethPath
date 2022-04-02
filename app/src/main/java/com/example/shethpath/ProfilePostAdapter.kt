package com.example.shethpath

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProfilePostAdapter(val context: Context, val posts: ArrayList<Post>) : RecyclerView.Adapter<ProfilePostAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
//        val tvUsername: TextView
//        val tvUsernameDescription: TextView
        val ivPostImage: ImageView
//        val tvDescription: TextView

        init {
//            tvUsername = itemView.findViewById(R.id.tvUsername)
//            tvUsernameDescription = itemView.findViewById(R.id.tvUsernameDescription)
            ivPostImage = itemView.findViewById(R.id.ivPostsProfile)
//            tvDescription = itemView.findViewById(R.id.tvDescription)
        }

        fun bind(post: Post) {
//            tvDescription.text = post.getDescription()
//            tvUsername.text = post.getUser()?.username
//            tvUsernameDescription.text = post.getUser()?.username
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivPostImage)
            Log.i("ProfileAdapter", "Reaches bind step")

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // which layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.item_post_grid, parent,false)
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