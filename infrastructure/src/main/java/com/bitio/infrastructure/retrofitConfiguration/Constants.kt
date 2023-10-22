package com.bitio.infrastructure.retrofitConfiguration

const val USER_BASE_URL = "http://192.168.100.101:8080/api/"
const val ACCESS_TOKEN_INVALIDATION_DURATION_MIN = 20L
const val DURATION_TO_UPDATE_ACCESS_TOKEN_MIN = ACCESS_TOKEN_INVALIDATION_DURATION_MIN - 3