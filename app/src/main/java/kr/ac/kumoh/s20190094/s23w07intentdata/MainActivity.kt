package kr.ac.kumoh.s20190094.s23w07intentdata

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.s20190094.s23w07intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    companion object{
        const val KEY_NAME = "isedol"
        const val LILPA = "lilpa"
        const val LLILPA = "llilpa"
    }

    private lateinit var main: ActivityMainBinding
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode != Activity.RESULT_OK)
            return@registerForActivityResult

        val result = it.data?.getIntExtra(
            ImageActivity.IMAGE_RESULT,
            ImageActivity.NONE) // 없을 때 반환값
        val str = when (result) {
            ImageActivity.LIKE -> "좋아요"
            ImageActivity.DISLIKE -> "싫어요"
            else -> ""
        }
        when (it.data?.getStringExtra(ImageActivity.IMAGE_NAME)) {
            LILPA -> main.img01.text = "릴파 ($str)"
            LLILPA -> main.img02.text = "릴ㄹ파 ($str)"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        main.img01.setOnClickListener{
            Toast.makeText(this,"릴파1 눌림",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ImageActivity::class.java)
            intent.putExtra(KEY_NAME,LILPA)
            startActivity(intent)
        }

        main.img02.setOnClickListener{
            Toast.makeText(this,"릴파2 눌림",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ImageActivity::class.java)
            intent.putExtra(KEY_NAME,LLILPA)
            startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, AnotherActivity::class.java)
        val value = when(p0?.id){
            main.img01.id -> {
                Toast.makeText(this, "릴파 이미지", Toast.LENGTH_SHORT).show()
                LILPA
            }
            main.img02.id -> {
                Toast.makeText(this, "릴ㄹ파 이미지",Toast.LENGTH_SHORT).show()
                LLILPA
            }
            else -> return
        }
        intent.putExtra(KEY_NAME, value)
        startForResult.launch(intent)
    }
}