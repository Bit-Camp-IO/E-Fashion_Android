package com.bitio.ui.profile

data class ProfileUiState(
    val loading: Boolean = false,
    val profile: Profile = Profile(),
    val error: String = ""
)


data class Profile(
    val id: Int = 0,
    val username: String = "Sajjad Abdel Aziz",
    val image: String = "https://th.bing.com/th/id/OIP.GlXqxcR9EmviN5kuwaUsMQHaIB?w=197&h=213&c=7&r=0&o=5&dpr=1.3&pid=1.7"
)