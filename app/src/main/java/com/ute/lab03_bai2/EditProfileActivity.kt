package com.ute.lab03_bai2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab03_bai2.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Nhận tên hiện tại từ Intent gửi sang và điền sẵn vào EditText
        val currentName = intent.getStringExtra("EXTRA_CURRENT_NAME") ?: ""
        if (currentName != "Chưa có thông tin") {
            binding.edtName.setText(currentName)
        }

        // 2. Xử lý nút 'Lưu & Quay lại'
        binding.btnSaveAndReturn.setOnClickListener {
            val updatedName = binding.edtName.text.toString().trim()

            if (updatedName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập họ và tên!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Đóng gói tên mới vào Intent phản hồi
            val resultIntent = Intent().apply {
                putExtra("EXTRA_UPDATED_NAME", updatedName)
            }

            // Gán mã thành công RESULT_OK và đóng màn hình
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}