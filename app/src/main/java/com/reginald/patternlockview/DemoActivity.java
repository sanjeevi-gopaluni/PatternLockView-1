package com.reginald.patternlockview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reginald.patternlockview.demo.R;

public class DemoActivity extends Activity {

    private static final String TAG = "DemoActivity";

    PatternLockView mCurLockView;

    PatternLockView mCircleLockView;

    PatternLockView mDotLockView;

    TextView mPasswordTextView;

    Button mSwitchButton;

    String mPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_layout);
        mCircleLockView = (PatternLockView) findViewById(R.id.lock_view_circle);
        mDotLockView = (PatternLockView) findViewById(R.id.lock_view_dot);
        mCurLockView = mDotLockView;
        mPasswordTextView = (TextView) findViewById(R.id.password_text);
        mSwitchButton = (Button) findViewById(R.id.switch_but);
        mSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchLockViews();
            }
        });
        mPasswordTextView.setText("please enter your password!");
        switchLockViews();
    }

    private void switchLockViews() {
        mCurLockView = mCurLockView == mCircleLockView ? mDotLockView : mCircleLockView;
        mCurLockView.setVisibility(View.VISIBLE);
        mCurLockView.reset();
        if (mCurLockView != mCircleLockView) {
            mCircleLockView.setVisibility(View.GONE);
            mCircleLockView.setCallBack(null);
            mCircleLockView.setOnNodeTouchListener(null);
            mSwitchButton.setText("switch to circle lock view");
        } else {
            mDotLockView.setVisibility(View.GONE);
            mDotLockView.setCallBack(null);
            mDotLockView.setOnNodeTouchListener(null);
            mSwitchButton.setText("switch to dot lock view");
        }

        mCurLockView.setCallBack(new PatternLockView.CallBack() {
            @Override
            public int onFinish(PatternLockView.Password password) {
                if (password.string.length() != 0) {
                    mPasswordTextView.setText("password is " + password.string);
                } else {
                    mPasswordTextView.setText("please enter your password!");
                }

                if (mPassword.equals(password.string)) {
                    return PatternLockView.CODE_PASSWORD_CORRECT;
                } else {
                    mPassword = password.string;
                    return PatternLockView.CODE_PASSWORD_ERROR;
                }
            }
        });

        mCurLockView.setOnNodeTouchListener(new PatternLockView.OnNodeTouchListener() {
            @Override
            public void onNodeTouched(int NodeId) {
                Log.d(TAG, "node " + NodeId + " has touched!");
            }
        });

    }
}
