package hu.bme.aut.android.squashtime.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.squashtime.R
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.CardAppointmentBinding

class AppointmentsAdapter(private val context: Context?) :
    ListAdapter<Appointment, AppointmentsAdapter.AppointmentViewHolder>(itemCallback) {

    private var appointmentList: List<Appointment> = emptyList()

    class AppointmentViewHolder(val binding: CardAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvDate: TextView = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppointmentViewHolder(CardAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val tmpApp = appointmentList[position]
        holder.binding.let { binding ->
            binding.tvDate.text = tmpApp.date.toString()
        }
        holder.binding.cardView.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("appID",tmpApp.uid)
            view.findNavController().navigate(R.id.action_nav_booking_to_appointmentDetailFragment, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAppointment(app: Appointment?) {
        app ?: return

        appointmentList += app
        submitList((appointmentList))
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
}
