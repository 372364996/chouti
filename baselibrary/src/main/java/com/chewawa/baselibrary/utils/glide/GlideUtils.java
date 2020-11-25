package com.chewawa.baselibrary.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chewawa.baselibrary.BaseApplication;
import com.chewawa.baselibrary.oss.OSSUtils;
import com.chewawa.baselibrary.utils.DensityUtil;

import java.io.File;
import java.math.BigDecimal;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 图片加载框架封装
 *
 * @author nanfeifei
 * @since 2016年10月9日上午11:49:21
 * @version 1.0
 */
public class GlideUtils {
	public Context context;
	public GlideUtils(Context context){
		this.context = context;
	}
	private String filterPath(String path){

		if(path == null){
			return "";
		}
		if(!path.contains("http")){
			return  OSSUtils.getInstance().getPublicObjectURL(path);
		}
		return path.trim();
	}
	/**
	 * 加载图片到ImageView
	 * @param imageUrl  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadImage(String imageUrl, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder);
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(imageView);

		}

	}
	/**
	 * 加载图片到ImageView
	 * @param imageUrl  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadRoundedImage(String imageUrl, ImageView imageView, int placeholder, int roundingRadius){
		if(isDestroy(context)){
			return;
		}
		//设置图片圆角角度
		RoundedCorners roundedCorners= new RoundedCorners(DensityUtil.dip2px(context,roundingRadius));
		MultiTransformation multiTransformation =  new MultiTransformation<>(new CenterCrop()
				,roundedCorners);
		RequestOptions options= RequestOptions
				.bitmapTransform(multiTransformation)
				.placeholder(placeholder)
				.error(placeholder);
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(imageView);

		}

	}
	/**
	 * 加载图片到ImageView
	 * @param imageUrl  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadImageCenterCrop(String imageUrl, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.centerCrop();
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(imageView);

		}

	}
	/**
	 * 加载图片到ImageView
	 * @param file  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadImage(File file, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder);
		Glide.with(context)
				.load(file)
				.apply(options)
				.into(imageView);

	}
	/**
	 * 加载图片到ImageView
	 * @param model  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadImage(byte[] model, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder);
		Glide.with(context)
				.load(model)
				.apply(options)
				.into(imageView);

	}
	/**
	 * 加载图片到ImageView
	 * @param file  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadImageCenterCrop(File file, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.centerCrop();
		Glide.with(context)
				.load(file)
				.apply(options)
				.into(imageView);

	}
	public void loadBackground(String imageUrl, final View view, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder);
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(new SimpleTarget<Drawable>(){
						@Override
						public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
								view.setBackground(resource);
							}else {
								view.setBackgroundDrawable(resource);
							}
						}

					});

		}
	}

	/** 通过平铺方式加载背景图(首页Tab栏背景使用)
	 * @param imageUrl
	 * @param view
	 * @param targetHeight  平铺单元高度（适配使用）
	 * @param placeholder
	 */
	public void loadBackgroundByTiled(String imageUrl, final View view, final int targetHeight, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder);
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(new SimpleTarget<Drawable>() {
						@Override
						public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
							BitmapDrawable drawable = bitmapRepeatToDrawable(resource, targetHeight);
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
								view.setBackground(drawable);
							}else {
								view.setBackgroundDrawable(drawable);
							}
						}

					});

		}
	}
	/** 通过平铺方式加载背景图(首页Tab栏背景使用)
	 * @param resource
	 * @param targetHeight  平铺单元高度（适配使用）
	 */
	private BitmapDrawable bitmapRepeatToDrawable(Drawable resource, int targetHeight){
		BitmapDrawable drawable = null;
		if(resource == null){
			return drawable;
		}
		int width = resource.getIntrinsicWidth();// 取drawable的长宽
		int height = resource.getIntrinsicHeight();
		if(width == 0 || height ==0){
			return drawable;
		}
		Bitmap.Config config = resource.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
		Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
		resource.setBounds(0, 0, width, height);
		resource.draw(canvas);// 把drawable内容画到画布中

		int newHeight = DensityUtil.dip2px(BaseApplication.getInstance(), targetHeight);
		float scale = 1; //默认保持原比例
		if(targetHeight != 0){
			scale = (float) newHeight/ (float) height;
		}
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		// 得到新的图片
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		drawable = new BitmapDrawable(bitmap);
		drawable.setTileModeXY(Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
		//索尼，小米等部分手机此时像素密集度异常，需要手动设置同dp转化px时一致
		drawable.setTargetDensity(BaseApplication.getInstance().getResources().getDisplayMetrics());
		drawable.setDither(true);
		//newBitmap.recycle();
		return drawable;
	}
	/**
	 * 加载图片到ImageView
	 * @param imageResource  图片地址
	 * @param imageView  View
	 * @param placeholder  占位图
	 */
	public void loadImage(int imageResource, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder);
		if(imageResource!=0){
			Glide.with(context)
					.load(imageResource)
					.apply(options)
					.into(imageView);

		}

	}
	/**
	 * 加载图片到ImageView，显示圆角
	 * @param imageUrl
	 * @param imageView
	 * @param placeholder
	 */
	public void loadCircleImage(String imageUrl, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.circleCrop();
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(imageView);
		}

	}
	/**
	 * 加载图片到ImageView，显示圆角
	 * @param file
	 * @param imageView
	 * @param placeholder
	 */
	public void loadCircleImage(File file, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.circleCrop();
		if(file!=null){
			Glide.with(context)
					.load(file)
					.apply(options)
					.into(imageView);
		}

	}
	/**
	 * 加载图片到ImageView，显示圆角
	 * @param res
	 * @param imageView
	 * @param placeholder
	 */
	public void loadCircleImage(int res, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.circleCrop();
		if(res!=0){
			Glide.with(context)
					.load(res)
					.apply(options)
					.into(imageView);
		}

	}

	/**
	 * 加载图片到ImageView，图片虚化
	 * @param imageUrl
	 * @param imageView
	 * @param placeholder
	 */
	public void loadBlurImage(String imageUrl, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.transform(new BlurTransformation(25, 10));
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(imageView);
		}

	}
	/**
	 * 加载图片到ImageView，图片虚化
	 * @param file
	 * @param imageView
	 * @param placeholder
	 */
	public void loadBlurImage(File file, ImageView imageView, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.transform(new BlurTransformation(25, 10));
		if(file!=null){
			Glide.with(context)
					.load(file)
					.apply(options)
					.into(imageView);
		}

	}
	/**
	 * 加载图片到ImageView，图片虚化
	 * @param res
	 * @param imageView
	 */
	public void loadBlurImage(int res, ImageView imageView){
		if(isDestroy(context)){
			return;
		}
		if(res == 0){
			return;
		}
		RequestOptions options = new RequestOptions()
				.transform(new BlurTransformation());
		Glide.with(context)
				.load(res)
				.apply(options)
				.into(imageView);

	}
	//默认加载
	public void loadBlurImage(Uri uri, ImageView imageView, int placeholder) {
		if(isDestroy(context)){
			return;
		}
		if(TextUtils.isEmpty(uri.getPath())){
			return;
		}
		RequestOptions options = new RequestOptions()
				.transform(new BlurTransformation(25, 10));
		Glide.with(context)
				.load(uri)
				.apply(options)
				.into(imageView);
	}
	public void loadBlurBackground(String imageUrl, final View view, int placeholder){
		if(isDestroy(context)){
			return;
		}
		RequestOptions options = new RequestOptions()
				.placeholder(placeholder)
				.error(placeholder)
				.transform(new BlurTransformation(25, 10));;
		if(imageUrl!=null){
			imageUrl = filterPath(imageUrl);
			Glide.with(context)
					.load(imageUrl)
					.apply(options)
					.into(new SimpleTarget<Drawable>(){
						@Override
						public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
								view.setBackground(resource);
							}else {
								view.setBackgroundDrawable(resource);
							}
						}

					});

		}
	}
	public void loadVideoCover(String videoUrl, final ImageView imageView){
		if(videoUrl != null){
//			videoUrl = filterPath(videoUrl);
			Observable.just(videoUrl)
					.map(new Function<String, Bitmap>() {
						@Override
						public Bitmap apply(String videoUrl) throws Exception {
							Bitmap bitmap = getNetVideoBitmap(videoUrl);
							return bitmap;
						}
					})
					.subscribeOn(Schedulers.newThread()) // s1
					.observeOn(AndroidSchedulers.mainThread()) // o2
					.subscribe(new Consumer<Bitmap>() {
						@Override
						public void accept(Bitmap bitmap) throws Exception {
							imageView.setImageBitmap(bitmap);
						}
					});
		}

	}
	// 清除图片磁盘缓存，调用Glide自带方法
	public boolean clearCacheDiskSelf() {
		try {
			if (Looper.myLooper() == Looper.getMainLooper()) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Glide.get(BaseApplication.getInstance()).clearDiskCache();
					}
				}).start();
			} else {
				Glide.get(BaseApplication.getInstance()).clearDiskCache();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 清除Glide内存缓存
	public boolean clearCacheMemory() {
		try {
			if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
				Glide.get(BaseApplication.getInstance()).clearMemory();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 获取Glide磁盘缓存大小
	public String getCacheSize() {
		try {
			File file = Glide.getPhotoCacheDir(BaseApplication.getInstance());
			return getFormatSize(getFolderSize(file));
		} catch (Exception e) {
			e.printStackTrace();
			return "获取失败";
		}
	}
	// 获取指定文件夹内所有文件大小的和
	private long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (File aFileList : fileList) {
				if (aFileList.isDirectory()) {
					size = size + getFolderSize(aFileList);
				} else {
					size = size + aFileList.length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	// 格式化单位
	private String getFormatSize(double size) {
		double kiloByte = size / 1024;
		//if (kiloByte < 1) {
		//	return size + "Byte";
		//}
		double megaByte = kiloByte / 1024;
		//if (megaByte < 1) {
		//	BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
		//	return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		//}
		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}
		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
	public static Bitmap drawableToBitmap(Drawable drawable) {
		// 取 drawable 的长宽
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// 取 drawable 的颜色格式
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// 建立对应 bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// 建立对应 bitmap 的画布
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// 把 drawable 内容画到画布中
		drawable.draw(canvas);
		return bitmap;
	}
	public Bitmap getNetVideoBitmap(String videoUrl) {
		Bitmap bitmap = null;

		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			//根据url获取缩略图
			retriever.setDataSource(videoUrl);
			//获得第一帧图片
			bitmap = retriever.getFrameAtTime();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			retriever.release();
		}
		return bitmap;
	}
	/**
	 * 判断Activity是否Destroy
	 * @param context
	 * @return
	 */
	public static boolean isDestroy(Context context) {
		if(context instanceof Activity){
			Activity mActivity = (Activity) context;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
				if(mActivity== null ||mActivity.isDestroyed()){
					return true;
				}else {
					return false;
				}
			}else {
				if (mActivity== null || mActivity.isFinishing()){
					return true;
				}else {
					return false;
				}
			}
		}
		return false;

	}
}
