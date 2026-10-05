package com.ute.lab03_bai2

import java.io.Serializable

data class Student(
    val id: String,
    var name: String,
    var className: String, // Đảm bảo đúng tên biến className
    val email: String,
    var gpa: Double
) : Serializable