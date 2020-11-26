package com.hanzhi.chouti.ui.teachers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.hanzhi.chouti.R;
import com.hanzhi.chouti.ui.appointment.fragment.AppointmentTimeFragment;

public class TeacherInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);
        //必需继承FragmentActivity,嵌套fragment只需要这行代码
        getSupportFragmentManager().beginTransaction().replace(R.id.teacher_info_selectedtime, new AppointmentTimeFragment()).commitAllowingStateLoss();
    }
}