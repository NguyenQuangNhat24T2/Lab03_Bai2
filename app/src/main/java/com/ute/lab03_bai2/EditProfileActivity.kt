package com.ute.lab03_bai2

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab03_bai2.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Nhận dữ liệu chuỗi và số thực (có giá trị mặc định phòng ngừa lỗi)
        val id = intent.getStringExtra(MainActivity.EXTRA_STUDENT_ID) ?: "Chưa có"
        val name = intent.getStringExtra(MainActivity.EXTRA_STUDENT_NAME) ?: ""
        val gpa = intent.getDoubleExtra(MainActivity.EXTRA_STUDENT_GPA, 0.0)

        // 2. Nhận đối tượng Serializable với ép kiểu an toàn 'as?'
        val student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(MainActivity.EXTRA_STUDENT_OBJECT, Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(MainActivity.EXTRA_STUDENT_OBJECT) as? Student
        }

        // Hiển thị dữ liệu nhận được lên giao diện
        binding.tvReceivedId.text = "Mã SV nhận được: $id"
        binding.edtName.setText(student?.name ?: name)
        binding.edtGpa.setText((student?.gpa ?: gpa).toString())

        // Nút quay lại màn hình trước
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}