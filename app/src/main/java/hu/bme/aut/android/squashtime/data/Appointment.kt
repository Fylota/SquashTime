package hu.bme.aut.android.squashtime.data

data class Appointment(
    val uid: String = "",
    val date: String? = null,
    val available: Boolean = true,
    val booked_by: String? = null,
    val court_number: String? = null,
    val comment: String? = null
)