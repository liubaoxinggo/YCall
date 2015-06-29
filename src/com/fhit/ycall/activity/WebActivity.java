package com.fhit.ycall.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.ToastUtil;
import com.fhit.ycall.util.web.DownLoadFileThreadTask;
import com.fhit.ycall.util.web.DownloadInterface;
import com.fhit.ycall.util.web.ResponseBean;

public class WebActivity extends BaseActivity implements View.OnClickListener,
		DownloadInterface {

	public final static String LOAD_URL = "load_url";
	public final static String LOAD_TITLE = "load_title";
	public final static String LOAD_FROM = "load_from";
	/**
	 * Ĭ�ϼ���
	 */
	public final static int FROM_DEFAULT = 0x100;
	/**
	 * ������ҵ��ҵ
	 */
	public final static int FROM_ENTERPRISE = 0x101;
	/**
	 * ������Ϸ
	 */
	public final static int FROM_GAME = 0x102;
	private WebView mWebView; // webView
	private ProgressBar title_bar; // ������������
	private TextView loadingTv; // �м������
	private ImageButton back; // ����
	private ImageButton forward; // ǰ��
	private ImageButton refresh; // ˢ��
	private ProgressDialog dialog = null; //���ؽ�����
	private final String defaultUrl = "http://www.baidu.com";
	private final String FILE_PATH = "/sdcard/download";
	private TextView tvTitle;
	private RelativeLayout bottomLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		initView(); // ��ʼ���ؼ�
		setListener(); // ���ü���
		initWeb();// ִ��WebView��ʼ������
		initData();
	}
	
	/**
	 * ��ʼ��webView
	 */
	@SuppressLint("NewApi") 
	private void initWeb() {
		mWebView.setScrollBarStyle(0);// ���������Ϊ0���ǲ������������ռ䣬��������������ҳ��
		WebSettings ws = mWebView.getSettings();
		ws.setJavaScriptEnabled(true); // ����֧��javascript�ű�
		ws.setAllowFileAccess(true); // ��������ļ�
		ws.setBuiltInZoomControls(true); // ���ò���ʾ���Ű�ť
		ws.setSupportZoom(true); // ֧������
		ws.setDisplayZoomControls(false);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				LogUtil.e("ycall��", getLocalClassName()+",shouldOverrideUrlLoading() ����url = "+url);
				loadurl(view, url);// ������ҳ
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				LogUtil.e("ycall��", getLocalClassName()+",onPageStarted() ��ʼ");
				loadingTv.setVisibility(View.VISIBLE);
				title_bar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				LogUtil.e("ycall��", getLocalClassName()+",onPageFinished() ����");
				loadingTv.setVisibility(View.GONE);
				title_bar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				ToastUtil.showLongToast(description + " ������룺" + errorCode);
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// ������ȸı������
				//LogUtil.e("ycall��", getLocalClassName()+",onProgressChanged() ���ȣ�"+progress);
				title_bar.setProgress(progress);
				loadingTv.setText(progress + "%");
				super.onProgressChanged(view, progress);
			}
		});
		/**
		 * �������ؼ�������urlΪ�ļ�ʱ��ʼ�����ļ�
		 */
		mWebView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent,
					String contentDisposition, String mimetype,
					long contentLength) {
				download(url);
			}
		});
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		
		((ImageView)findViewById(R.id.left_back_ic)).setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tvTitle = (TextView)findViewById(R.id.left_title_tv);
		mWebView = (WebView) findViewById(R.id.webview);
		title_bar = (ProgressBar) findViewById(R.id.loadingbar);
		loadingTv = (TextView) findViewById(R.id.loadingTv);
		bottomLayout = (RelativeLayout) findViewById(R.id.bottomlayout);
		back = (ImageButton) findViewById(R.id.btn_back);
		forward = (ImageButton) findViewById(R.id.btn_go);
		refresh = (ImageButton) findViewById(R.id.btn_refresh);
		bottomLayout.setVisibility(View.GONE);
	}

	/**
	 * ���ü�����
	 */
	private void setListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		refresh.setOnClickListener(this);
	}
	private void initData(){
		((EditText)findViewById(R.id.center_input_et)).setVisibility(View.INVISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setVisibility(View.VISIBLE);
		int from = this.getIntent().getIntExtra(LOAD_FROM, FROM_DEFAULT);
		switch (from) {
		case FROM_DEFAULT:
			break;
		case FROM_ENTERPRISE:
			((ImageView)findViewById(R.id.top_right_ic)).setVisibility(View.VISIBLE);
			((ImageView)findViewById(R.id.top_right_ic)).setOnClickListener(enterpriseFavListener);
			((ImageView)findViewById(R.id.top_right_ic)).setImageResource(R.drawable.nav_fav_selector);
			break;
		case FROM_GAME:
			break;

		default:
			break;
		}
		String loadTitle = this.getIntent().getStringExtra(LOAD_TITLE);
		if(!TextUtils.isEmpty(loadTitle)){
			tvTitle.setText(loadTitle);
		}else{
			tvTitle.setText(getResourceString(R.string.app_name));
		}
		String loadUrl = this.getIntent().getStringExtra(LOAD_URL);
		if(!TextUtils.isEmpty(loadUrl)){
			mWebView.loadUrl(loadUrl);
		}else{
			mWebView.loadUrl(defaultUrl);
		}
	}
	private View.OnClickListener enterpriseFavListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ToastUtil.showLongToast("�ղ���ҵ");
		}
	};
	/**
	 * ��������
	 * @param view
	 * @param url
	 */
	public void loadurl(final WebView view, final String url) {
		view.loadUrl(url);// ������ҳ
	}

	/**
	 * �����ļ�
	 */
	private void download(String url) {
		// TODO Auto-generated method stub
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			showDownloadDialog(); //��ʾ���ش���
			String expand_name = url.substring(url.lastIndexOf("."), url.length()); //��ȡ�����ļ�����չ��
		    String save_file_name = System.currentTimeMillis()+expand_name; //���ļ���
			final DownLoadFileThreadTask task = new DownLoadFileThreadTask(url,
					file.getAbsolutePath() + "/"+save_file_name, this);
			Thread thread = new Thread(task);
			thread.start();
			dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					task.stop();
				}
			});
		} else {
			Toast.makeText(this, "SD�������ã�", Toast.LENGTH_SHORT).show();
		}
	}

	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView != null
				&& mWebView.canGoBack()) {
			goBack();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			WebActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mWebView.removeAllViews();
		mWebView.destroy();
		Log.e("�����˳�:", "------- �Ѿ��˳�-----");
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back: // ����
			goBack();
			break;
		case R.id.btn_go: // ǰ��
			goForward();
			break;
		case R.id.btn_refresh: // ˢ��
			refresh();
			break;
		default:
			break;
		}
	}

	/**
	 * ����
	 */
	private void goBack() {
		// TODO Auto-generated method stub
		if (mWebView != null && mWebView.canGoBack()) {
			mWebView.goBack();
		}
	}

	/**
	 * ǰ��
	 */
	private void goForward() {
		// TODO Auto-generated method stub
		if (mWebView != null && mWebView.canGoForward()) {
			mWebView.goForward();
		}
	}

	/**
	 * ˢ��
	 */
	private void refresh() {
		// TODO Auto-generated method stub
		if (mWebView != null) {
			mWebView.reload();
		}
	}

	/**
	 * ��ʾ���ؽ�����
	 */
	private void showDownloadDialog() {
		if (dialog == null) {
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setTitle("������...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ȡ��", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
		};
		dialog.show();
	}

	@Override
	public void sendMsg(ResponseBean response) {
		// TODO Auto-generated method stub
		if (response == null) {
			return;
		}
		switch (response.getStatus()) {
		case DownLoadFileThreadTask.DOWNLOAD_ING:
			dialog.setMax(response.getTotal());
			dialog.setProgress(response.getCurrent());
			break;
		case DownLoadFileThreadTask.DOWNLOAD_SUCCESS:
			dialog.dismiss();
			 Looper.prepare(); //ע����δ���
			Toast.makeText(WebActivity.this, "���سɹ����Ѿ����ص�"+FILE_PATH+"Ŀ¼��", Toast.LENGTH_LONG)
					.show();
			Looper.loop();
			break;
		case DownLoadFileThreadTask.DOWNLOAD_FAILURE:
			dialog.dismiss();
			Looper.prepare();
			Toast.makeText(WebActivity.this, response.getMsg(),
					Toast.LENGTH_SHORT).show();
			Looper.loop();
			break;
		default:
			break;
		}
	}

}
