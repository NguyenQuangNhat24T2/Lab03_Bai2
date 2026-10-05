package com.ute.lab03_bai2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab03_bai2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Biến lưu họ tên, giá trị khởi tạo mặc định theo đề bài
    private var currentName: String = "Chưa có thông tin"

    // 1. Đăng ký Activity Result Launcher để đón nhận dữ liệu phản hồi
    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newName = result.data?.getStringExtra("EXTRA_UPDATED_NAME")
            if (!newName.isNullOrBlank()) {
                currentName = newName
                binding.tvCurrentName.text = currentName
                Toast.makeText(this, "Đã cập nhật tên thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hiển thị tên ban đầu lên giao diện
        binding.tvCurrentName.text = currentName

        // 2. Bấm 'Chỉnh sửa thông tin' -> Gửi họ tên hiện tại sang EditProfileActivity
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("EXTRA_CURRENT_NAME", currentName)
            }
            editProfileLauncher.launch(intent)
        }
    }
}