package hu.bme.aut.android.squashtime

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.squashtime.adapter.AppointmentsAdapter
import hu.bme.aut.android.squashtime.data.Appointment
import hu.bme.aut.android.squashtime.databinding.FragmentAppointmentDetailBinding
import java.util.*

class AppointmentDetailFragment : Fragment() {

    private var selectedAppointment: Appointment? = null
    private lateinit var appointmentsAdapter: AppointmentsAdapter

    private var _binding: FragmentAppointmentDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Firebase.firestore
        db.collection("appointments")
            .whereEqualTo("uid",arguments?.getString("appID")).get()
            .addOnSuccessListener { results ->
                for (document in results) {
                    if (document != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        val tmpAppointment = document.toObject<Appointment>()
                        selectedAppointment = Appointment(tmpAppointment.uid,tmpAppointment.date, tmpAppointment.available)
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAppointmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "The selected app in onViewCreated: $selectedAppointment")
        binding.appointmentDetail.text = selectedAppointment?.date.toString()
        binding.textDetails.text = selectedAppointment?.date.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
