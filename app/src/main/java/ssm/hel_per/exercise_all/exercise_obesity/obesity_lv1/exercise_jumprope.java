package ssm.hel_per.exercise_all.exercise_obesity.obesity_lv1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ssm.hel_per.R;

public class exercise_jumprope extends AppCompatActivity {
    TextView mText;
    Button mButton;
    int value=120;
    String id;
    Double weight;

    ImageView swingImage;
    ImageView skyImage;

    Animation shakeAnimation;
    Animation dropAnimation;
    Animation flowAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_jumprope);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mText=(TextView)findViewById(R.id.text_jumprope);
        mButton=(Button)findViewById(R.id.nextbtn_jumprope);

        new CountDownTimer(120*1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) { // 총 시간과 주기
                value--;
                mText.setText("남은 시간  " + value + "초");
//                if(value==0){
//                    Intent intent = new Intent(exercise_buffet.this,exercise_jumpingrope.class);
//                    startActivity(intent);
//                    finish();
//                }
            }

            @Override
            public void onFinish() {

            }
        }.start();  // 타이머 시작
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(exercise_jumprope.this,exercise_step.class);
                id = getIntent().getStringExtra("id");
                intent.putExtra("id",id);
                weight=getIntent().getDoubleExtra("weight",0);
                intent.putExtra("weight",weight);
                startActivity(intent);
                finish();
            }
        });
        // swing 이미지에 애니메이션 객체 설정
        swingImage = (ImageView) findViewById(R.id.swingImage);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        swingImage.setAnimation(shakeAnimation);

        // water 이미지에 애니메이션 객체 설정
        // waterImage = (ImageView) findViewById(R.id.waterImage);
        dropAnimation = AnimationUtils.loadAnimation(this, R.anim.drop);
        //waterImage.setAnimation(dropAnimation);

        // sky 이미지에 애니메이션 객체 설정
        skyImage = (ImageView) findViewById(R.id.skyImage);
        flowAnimation = AnimationUtils.loadAnimation(this, R.anim.flow);
        skyImage.setAnimation(flowAnimation);
        flowAnimation.setAnimationListener(new AnimationAdapter());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


        if (hasFocus) {
            shakeAnimation.start();
            dropAnimation.start();
            flowAnimation.start();
        } else {
            shakeAnimation.reset();
            dropAnimation.reset();
            flowAnimation.reset();
        }

    }



    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }



    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }


    /**
     * 애니메이션의 시작과 종료 시점을 알기 위한 리스너
     */
    private final class AnimationAdapter implements Animation.AnimationListener {

        public void onAnimationStart(Animation animation) {

        }

        public void onAnimationEnd(Animation animation) {

        }

        public void onAnimationRepeat(Animation animation) {

        }

    }
}