package hu.bme.aut.android.squashtime.data

import com.google.firebase.firestore.auth.User
import java.util.*

data class Appointment(
    val uid: String = "",
    val date: Date? = null,
    val available: Boolean = true,
    val booked_by: User? = null,
    val court_number: String? = null,
    val comment: String? = null
)