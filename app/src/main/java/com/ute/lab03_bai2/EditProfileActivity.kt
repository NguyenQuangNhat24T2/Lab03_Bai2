package com.ute.lab03_bai2 // <-- GIỮ ĐÚNG PACKAGE CỦA BẠN

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab03_bai2.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private var originalStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nhận dữ liệu sinh viên gửi từ MainActivity qua Explicit Intent (Bài 2)
        originalStudent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("STUDENT", Student::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("STUDENT") as? Student
        }

        // Đổ dữ liệu cũ lên Form
        originalStudent?.let {
            binding.edtName.setText(it.name)
            binding.edtClass.setText(it.className)
            binding.edtGpa.setText(it.gpa.toString())
        }

        // Bài 4: Nút Lưu -> Validate và gửi dữ liệu phản hồi về Activity cha
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString().trim()
            val className = binding.edtClass.text.toString().trim()
            val gpa = binding.edtGpa.text.toString().toDoubleOrNull()

            if (name.isEmpty() || gpa == null || gpa !in 0.0..4.0) {
                Toast.makeText(this, "Vui lòng nhập tên và GPA hợp lệ (0.0 - 4.0)!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updated = originalStudent?.copy(name = name, className = className, gpa = gpa)

            // Đóng gói kết quả gửi về qua Activity Result API
            val resIntent = Intent().apply {
                putExtra("UPDATED", updated)
            }
            setResult(Activity.RESULT_OK, resIntent) // Bắt buộc gọi trước finish()
            finish()
        }

        // Nút Hủy Bỏ -> Trả về RESULT_CANCELED mặc định
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}