package hu.bme.aut.android.squashtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.squashtime.data.Post
import hu.bme.aut.android.squashtime.databinding.CardPostBinding

class PostsAdapter(private val context: Context) :
    ListAdapter<Post, PostsAdapter.PostViewHolder>(itemCallback) {

    private var postList: List<Post> = emptyList()
    private var lastPosition = -1

    class PostViewHolder(binding: CardPostBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvAuthor: TextView = binding.tvAuthor
        val tvTitle: TextView = binding.tvTitle
        val tvBody: TextView = binding.tvBody
        val imgPost: ImageView = binding.imgPost
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PostViewHolder(CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val tmpPost = postList[position]
        holder.tvAuthor.text = tmpPost.author
        holder.tvTitle.text = tmpPost.title
        holder.tvBody.text = tmpPost.body

        if (tmpPost.imageUrl.isNullOrBlank()) {
            holder.imgPost.visibility = View.GONE
        } else {
            Glide.with(context).load(tmpPost.imageUrl).into(holder.imgPost)
            holder.imgPost.visibility = View.VISIBLE
        }

        setAnimation(holder.itemView, position)
    }

    fun addPost(post: Post?) {
        post ?: return

        postList += (post)
        submitList((postList))
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem == newItem
            }
        }
    }
}
