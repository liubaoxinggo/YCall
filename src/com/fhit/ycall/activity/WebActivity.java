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
	 * 默认加载
	 */
	public final static int FROM_DEFAULT = 0x100;
	/**
	 * 加载企业主业
	 */
	public final static int FROM_ENTERPRISE = 0x101;
	/**
	 * 加载游戏
	 */
	public final static int FROM_GAME = 0x102;
	private WebView mWebView; // webView
	private ProgressBar title_bar; // 标题栏进度条
	private TextView loadingTv; // 中间进度条
	private ImageButton back; // 后退
	private ImageButton forward; // 前进
	private ImageButton refresh; // 刷新
	private ProgressDialog dialog = null; //下载进度条
	private final String defaultUrl = "http://www.baidu.com";
	private final String FILE_PATH = "/sdcard/download";
	private TextView tvTitle;
	private RelativeLayout bottomLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		initView(); // 初始化控件
		setListener(); // 设置监听
		initWeb();// 执行WebView初始化函数
		initData();
	}
	
	/**
	 * 初始化webView
	 */
	@SuppressLint("NewApi") 
	private void initWeb() {
		mWebView.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
		WebSettings ws = mWebView.getSettings();
		ws.setJavaScriptEnabled(true); // 设置支持javascript脚本
		ws.setAllowFileAccess(true); // 允许访问文件
		ws.setBuiltInZoomControls(true); // 设置不显示缩放按钮
		ws.setSupportZoom(true); // 支持缩放
		ws.setDisplayZoomControls(false);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				LogUtil.e("ycall：", getLocalClassName()+",shouldOverrideUrlLoading() 加载url = "+url);
				loadurl(view, url);// 载入网页
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				LogUtil.e("ycall：", getLocalClassName()+",onPageStarted() 开始");
				loadingTv.setVisibility(View.VISIBLE);
				title_bar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				LogUtil.e("ycall：", getLocalClassName()+",onPageFinished() 结束");
				loadingTv.setVisibility(View.GONE);
				title_bar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				ToastUtil.showLongToast(description + " 错误代码：" + errorCode);
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
				//LogUtil.e("ycall：", getLocalClassName()+",onProgressChanged() 进度："+progress);
				title_bar.setProgress(progress);
				loadingTv.setText(progress + "%");
				super.onProgressChanged(view, progress);
			}
		});
		/**
		 * 设置下载监听，当url为文件时候开始下载文件
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
	 * 初始化控件
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
	 * 设置监听器
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
			ToastUtil.showLongToast("收藏企业");
		}
	};
	/**
	 * 载入链接
	 * @param view
	 * @param url
	 */
	public void loadurl(final WebView view, final String url) {
		view.loadUrl(url);// 载入网页
	}

	/**
	 * 下载文件
	 */
	private void download(String url) {
		// TODO Auto-generated method stub
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(FILE_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			showDownloadDialog(); //显示下载窗口
			String expand_name = url.substring(url.lastIndexOf("."), url.length()); //获取下载文件的扩展名
		    String save_file_name = System.currentTimeMillis()+expand_name; //新文件名
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
			Toast.makeText(this, "SD卡不可用！", Toast.LENGTH_SHORT).show();
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
		Log.e("程序退出:", "------- 已经退出-----");
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back: // 返回
			goBack();
			break;
		case R.id.btn_go: // 前进
			goForward();
			break;
		case R.id.btn_refresh: // 刷新
			refresh();
			break;
		default:
			break;
		}
	}

	/**
	 * 返回
	 */
	private void goBack() {
		// TODO Auto-generated method stub
		if (mWebView != null && mWebView.canGoBack()) {
			mWebView.goBack();
		}
	}

	/**
	 * 前进
	 */
	private void goForward() {
		// TODO Auto-generated method stub
		if (mWebView != null && mWebView.canGoForward()) {
			mWebView.goForward();
		}
	}

	/**
	 * 刷新
	 */
	private void refresh() {
		// TODO Auto-generated method stub
		if (mWebView != null) {
			mWebView.reload();
		}
	}

	/**
	 * 显示下载进度条
	 */
	private void showDownloadDialog() {
		if (dialog == null) {
			dialog = new ProgressDialog(this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setTitle("下载中...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener() {
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
			 Looper.prepare(); //注意这段代码
			Toast.makeText(WebActivity.this, "下载成功，已经下载到"+FILE_PATH+"目录！", Toast.LENGTH_LONG)
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
