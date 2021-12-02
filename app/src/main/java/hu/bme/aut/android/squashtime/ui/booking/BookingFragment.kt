package hu.bme.aut.android.squashtime.ui.booking

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.squashtime.adapter.AppointmentsAdapter
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.FragmentBookingBinding


class BookingFragment : Fragment() {

    private lateinit var bookingViewModel: BookingViewModel
    private var _binding: FragmentBookingBinding? = null
    private lateinit var appointmentsAdapter: AppointmentsAdapter


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookingViewModel =
            ViewModelProvider(this).get(BookingViewModel::class.java)

        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        appointmentsAdapter = activity?.let { AppointmentsAdapter(it.applicationContext) }!!
        binding.rvDates.layoutManager = LinearLayoutManager(this.context).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        binding.rvDates.adapter = appointmentsAdapter

        initAppointmentsListener()

        return root
    }

    private fun initAppointmentsListener() {
        val db = Firebase.firestore
        db.collection("appointments")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Toast.makeText(this.context, e.toString(), Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> appointmentsAdapter.addAppointment(dc.document.toObject<Appointment>())
                        DocumentChange.Type.MODIFIED -> Toast.makeText(this.context, dc.document.data.toString(), Toast.LENGTH_SHORT).show()
                        DocumentChange.Type.REMOVED -> Toast.makeText(this.context, dc.document.data.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}