package hu.bme.aut.android.squashtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.CardAppointmentBinding

class AppointmentsAdapter(private val context: Context) :
    ListAdapter<Appointment, AppointmentsAdapter.AppointmentViewHolder>(itemCallback) {

    private var postList: List<Appointment> = emptyList()
    private var lastPosition = -1
    var itemClickListener: AppointmentItemClickListener? = null

    class AppointmentViewHolder(binding: CardAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvDate: TextView = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppointmentViewHolder(CardAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val tmpPost = postList[position]
        holder.tvDate.text = tmpPost.date.toString()
    }

    fun addAppointment(post: Appointment?) {
        post ?: return

        postList += (post)
        submitList((postList))
    }

    companion object {
        object itemCallback : DiffUtil.ItemCallback<Appointment>() {
            override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val binding: CardAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {
        var appointment: Appointment? = null

        init {
            itemView.setOnClickListener {
                appointment?.let { appointment -> itemClickListener?.onItemClick(appointment) }
            }
        }
    }

    interface AppointmentItemClickListener {
        fun onItemClick(appointment: Appointment)
    }
}
