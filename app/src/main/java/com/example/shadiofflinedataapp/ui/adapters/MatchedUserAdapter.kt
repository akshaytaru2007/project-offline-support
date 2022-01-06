package com.example.shadiofflinedataapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shadiofflinedataapp.R
import com.example.shadiofflinedataapp.data.local.model.MatchAcceptedRejectUser
import com.example.shadiofflinedataapp.data.local.model.User
import com.example.shadiofflinedataapp.data.model.UserDecision
import com.example.shadiofflinedataapp.data.model.value
import com.example.shadiofflinedataapp.databinding.MatchedUserCardItemBinding
import com.example.shadiofflinedataapp.ui.MainViewModel

class MatchedUserAdapter(private val viewModel: MainViewModel) :
    ListAdapter<User, MatchedUserAdapter.MatchUserViewHolder>(UserComparator()) {
    private val acceptedRejectUsers: MutableList<MatchAcceptedRejectUser> = mutableListOf()
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MatchedUserAdapter.MatchUserViewHolder {
        val binding =
            MatchedUserCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MatchUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchedUserAdapter.MatchUserViewHolder, position: Int) {
        val item = getItem(position)
        item.let {
            holder.bind(it, position)
        }
    }

    fun setAcceptRejectUserList(acceptedRejectUser: List<MatchAcceptedRejectUser>) {
        this.acceptedRejectUsers.clear()
        this.acceptedRejectUsers.addAll(acceptedRejectUser)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    inner class MatchUserViewHolder(private val binding: MatchedUserCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, position: Int) {

            binding.apply {
                userName.text =
                    "${user.name.title} ${user.name.firstName} ${user.name.lastName}"

                Glide.with(itemView)
                    .load(user.picture)
                    .into(binding.userImage)

                accept.setOnClickListener {
                    viewModel.setAcceptRejectUser(
                        user.uuid,
                        UserDecision.Accepted()
                    )
                }
                reject.setOnClickListener {
                    viewModel.setAcceptRejectUser(
                        user.uuid,
                        UserDecision.Reject()
                    )
                }

                val acceptedRejectedUser =
                    acceptedRejectUsers.filter { it.userUuid == user.uuid }.singleOrNull()

                if (acceptedRejectedUser == null) {
                    btnLayout.visibility = View.VISIBLE
                } else {
                    btnLayout.visibility = View.INVISIBLE
                }

                acceptedRejectedUser?.let {
                    when (it.decision) {
                        UserDecision.Accepted().value() -> {
                            statusMessage.text = context.getString(R.string.member_accepted)
                        }
                        UserDecision.Reject().value() -> {
                            statusMessage.text = context.getString(R.string.member_declined)
                        }
                        else -> {
                            statusMessage.text = ""
                        }
                    }
                }
            }
        }
    }

    class UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}