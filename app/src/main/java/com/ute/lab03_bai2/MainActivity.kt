package com.ute.lab03_bai2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab03_bai2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
        const val EXTRA_STUDENT_NAME = "EXTRA_STUDENT_NAME"
        const val EXTRA_STUDENT_GPA = "EXTRA_STUDENT_GPA"
        const val EXTRA_STUDENT_OBJECT = "EXTRA_STUDENT_OBJECT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sampleStudent = Student("2415053122226", "Nguyễn Quang Nhật", 3.8)

        binding.btnNavigate.setOnClickListener {
            navigateToEditProfile(sampleStudent)
        }
    }

    private fun navigateToEditProfile(student: Student) {
        // Khởi tạo Explicit Intent chỉ định rõ EditProfileActivity
        val intent = Intent(this, EditProfileActivity::class.java).apply {
            // 1. Truyền các kiểu dữ liệu nguyên thủy
            putExtra(EXTRA_STUDENT_ID, student.id)
            putExtra(EXTRA_STUDENT_NAME, student.name)
            putExtra(EXTRA_STUDENT_GPA, student.gpa)

            // 2. Truyền đối tượng Serializable
            putExtra(EXTRA_STUDENT_OBJECT, student)
        }
        startActivity(intent)
    }
}