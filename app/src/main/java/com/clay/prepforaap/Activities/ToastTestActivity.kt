package com.clay.prepforaap.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.clay.prepforaap.R

class ToastTestActivity : AppCompatActivity() {

    val toast_gen: Button by lazy {
        findViewById(R.id.btn_toast_gen) as Button
    }
    val checkbox_gravity: CheckBox by lazy {
        findViewById(R.id.checkBox) as CheckBox
    }
    val checkbox_lyt: CheckBox by lazy {
        findViewById(R.id.checkBox2) as CheckBox
    }
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast_test)
        toast_gen.setOnClickListener(View.OnClickListener { view ->
            var gravity: Int = Gravity.BOTTOM
            if (checkbox_gravity.isChecked()) {
                gravity = Gravity.BOTTOM or Gravity.END
            }
            if (checkbox_lyt.isChecked()) {
                customToastGen(gravity)
            }else{
                Toast.makeText(context,"This toast is default.",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun customToastGen(gravity: Int) {
        val toast: Toast = Toast(context)
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_toast, findViewById(R.id.container_toast))
        toast.view = view
        toast.setGravity(gravity, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.setMargin(4.0f,4.0f)
        toast.show()
    }
}
