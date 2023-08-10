package com.bitio.ui.profile

data class ProfileUiState(
    val loading: Boolean = false,
    val profile: Profile = Profile(),
    val error: String = ""
)


data class Profile(
    val id: Int = 0,
    val username: String = "Sajjad Abdel Aziz",
    val image: String = "https://th.bing.com/th/id/OIP.NqY3rNMnx2NXYo3KJfg43gAAAA?w=196&h=196&c=7&r=0&o=5&dpr=1.3&pid=1.7"
)