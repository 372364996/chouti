package com.chewawa.baselibrary.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.chewawa.baselibrary.R;


/**
 * Created by baidu on 15/8/31.
 */
public class XProgressDialog extends ProgressDialog {

  protected TextView message;
  protected String messageText = "加载中...";

  public XProgressDialog(Context context) {
    super(context, R.style.CustomDialog);
  }

  public XProgressDialog(Context context, String message) {
    super(context,R.style.CustomDialog);
    messageText = message;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view_progress_lay);
    message = (TextView) findViewById(R.id.progress_text);
    if (message != null && !TextUtils.isEmpty(messageText)) {
      message.setText(messageText);
    }
    setCanceledOnTouchOutside(false);
//    setCancelable(false);
  }

  public void setMessage(String messageStr) {
    messageText = messageStr;
    if (message != null && !TextUtils.isEmpty(messageStr)) {
      this.message.setText(messageStr);
    }

  }


  /**
   * 获取显示文本控件
   */
  protected TextView getMessageView() {
    return message;
  }

  @Override public void show() {
    super.show();
  }
}
