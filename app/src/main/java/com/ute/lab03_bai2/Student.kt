package com.ute.lab03_bai2

import java.io.Serializable

data class Student(
    val id: String,
    val name: String,
    val gpa: Double
) : Serializable