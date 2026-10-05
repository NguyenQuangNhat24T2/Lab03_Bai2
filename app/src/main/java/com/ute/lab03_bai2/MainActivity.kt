package com.ute.lab03_bai2 // <-- GIỮ ĐÚNG PACKAGE CỦA BẠN

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ute.lab03_bai2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "TAG_LIFECYCLE"
    }

    private lateinit var binding: ActivityMainBinding

    // Dữ liệu mẫu sinh viên
    private var student = Student(
        id = "22505120005",
        name = "Nguyễn Quang Nhật",
        className = "22CT1",
        email = "nhatnq@ute.udn.vn",
        gpa = 3.8
    )

    // ==========================================
    // 3 LAUNCHERS CỦA ACTIVITY RESULT API (BÀI 4, 5, 6)
    // ==========================================
    // 1. Launcher Bài 4: Nhận kết quả cập nhật từ EditProfileActivity
    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val updated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                res.data?.getSerializableExtra("UPDATED", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                res.data?.getSerializableExtra("UPDATED") as? Student
            }

            updated?.let {
                student = it
                bindData(student)
                Toast.makeText(this, "Đã lưu thông tin mới của ${it.name}!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Đã hủy thao tác chỉnh sửa!", Toast.LENGTH_SHORT).show()
        }
    }

    // 2. Launcher Bài 5: Chọn ảnh đại diện từ Gallery bằng Photo Picker
    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            binding.imgAvatar.setImageURI(it)
            Toast.makeText(this, "Đã cập nhật ảnh đại diện!", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Launcher Bài 6: Xin cấp quyền Camera lúc chạy (Runtime Permission)
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Đã cấp quyền Camera! Có thể chụp ảnh ngay.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Bạn đã từ chối quyền Camera!", Toast.LENGTH_SHORT).show()
        }
    }

    // ==========================================
    // CÁC HÀM VÒNG ĐỜI ACTIVITY (BÀI 1)
    // ==========================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity đang được khởi tạo và nạp layout")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindData(student)

        // Nút 1: Chuyển màn hình và chờ kết quả trả về (Bài 2 + Bài 4)
        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("STUDENT", student)
            }
            editLauncher.launch(intent)
        }

        // Nút 2: Mở bộ sưu tập ảnh (Bài 5)
        binding.btnChangeAvatar.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        // Nút 3: Gọi Cố vấn học tập bằng Implicit Intent (Bài 3)
        binding.btnCallHotline.setOnClickListener {
            val phone = "0905123456"
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(dialIntent)
        }

        // Nút 4: Xin quyền Camera (Bài 6)
        binding.btnRequestCamera.setOnClickListener {
            cameraLauncher.launch(Manifest.permission.CAMERA)
        }

        // Nút 5: Mở bản đồ Google Maps đến ĐH Sư Phạm Kỹ Thuật (Bài tập mở rộng 1)
        binding.btnOpenMap.setOnClickListener {
            val mapUri = Uri.parse("geo:16.0768,108.2141?q=Đại+học+Sư+phạm+Kỹ+thuật+Đà+Nẵng")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            try {
                startActivity(mapIntent)
            } catch (e: ActivityNotFoundException) {
                val fallbackIntent = Intent(Intent.ACTION_VIEW, mapUri)
                startActivity(Intent.createChooser(fallbackIntent, "Chọn ứng dụng Bản đồ"))
            }
        }
    }

    private fun bindData(s: Student) {
        binding.tvName.text = s.name
        binding.tvDetails.text = "Mã SV: ${s.id} | Lớp: ${s.className}"
        val rank = if (s.gpa >= 3.6) "Xuất sắc" else if (s.gpa >= 3.2) "Giỏi" else "Khá"
        binding.tvGpaBadge.text = "GPA: ${s.gpa} - $rank"
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity đã hiển thị")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity sẵn sàng nhận tương tác")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity tạm dừng tương tác")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity đã bị ẩn hoàn toàn")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Mở lại từ Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity bị hủy khỏi RAM")
    }
}