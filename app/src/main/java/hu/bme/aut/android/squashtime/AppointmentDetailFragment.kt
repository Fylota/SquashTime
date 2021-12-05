package hu.bme.aut.android.squashtime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.squashtime.adapter.AppointmentsAdapter
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.FragmentAppointmentDetailBinding
import java.util.*

class AppointmentDetailFragment() : Fragment() {

    private var selectedAppointment: Appointment? = null
    private val db = Firebase.firestore

    private var _binding: FragmentAppointmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAppointmentDetailBinding.inflate(inflater, container, false)
        arguments?.let { args ->
            selectedAppointment = Appointment(
                uid = args.getString("appID") ?: "",
                date = args.getString("date") ?: "",
                court_number = args.getString("courtNumber") ?: "",
                available = args.getBoolean("available")
            )
        }

        binding.buttonBookAppointment.setOnClickListener {
            bookAppointment()
            binding.buttonBookAppointment.isEnabled = false
            binding.buttonCancelAppointment.isVisible = true
            binding.buttonCancelAppointment.isClickable = true
        }

        binding.buttonCancelAppointment.setOnClickListener {
            cancelAppointment()
            binding.buttonBookAppointment.isEnabled = true
            binding.buttonCancelAppointment.isVisible = false
            binding.buttonCancelAppointment.isClickable = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appointmentDate.text = selectedAppointment?.date
        binding.appointmentCourtNumber.text = selectedAppointment?.court_number

        if(selectedAppointment?.available == true) {
            binding.buttonBookAppointment.isEnabled = true
            binding.buttonCancelAppointment.isVisible = false
            binding.buttonCancelAppointment.isClickable = false
        } else {
            binding.buttonBookAppointment.isEnabled = false
            binding.buttonCancelAppointment.isVisible = true
            binding.buttonCancelAppointment.isClickable = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bookAppointment() {
        db.collection("appointments")
            .whereEqualTo("uid", arguments?.getString("appID")).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (doc in snapshot.documents) {
                        db.collection("appointments").document(doc.id).update(
                            "available", false, "booked_by",
                            FirebaseAuth.getInstance().currentUser?.email
                        )
                        Toast.makeText(this.context, "Item updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun cancelAppointment() {
        db.collection("appointments")
            .whereEqualTo("uid", arguments?.getString("appID")).get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (doc in snapshot.documents) {
                        db.collection("appointments").document(doc.id).update(
                            "available", true, "booked_by", null)
                        Toast.makeText(this.context, "Item updated", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


}
