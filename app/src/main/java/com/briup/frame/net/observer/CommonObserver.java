package com.briup.frame.net.observer;

import android.app.Dialog;
import android.text.TextUtils;
import com.briup.frame.net.base.BaseObserver;
import com.briup.frame.net.utils.ToastUtils;
import io.reactivex.disposables.Disposable;

/**
 *
 * 通用的Observer
 * 用户可以根据自己需求自定义自己的类继承BaseObserver<T>即可
 */

public abstract class CommonObserver<T> extends BaseObserver<T> {


    private Dialog mProgressDialog;

    public CommonObserver() {
    }

    public CommonObserver(Dialog progressDialog) {
        mProgressDialog = progressDialog;
    }

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(String errorMsg);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);


    @Override
    public void doOnSubscribe(Disposable d) {
        //自行管理取消请求  重写doOnSubscribe方法调用如下方法即可加入，在onDestroy中调用RxHttpUtils.clearAllCompositeDisposable()
        //RxHttpUtils.addToCompositeDisposable(d);
    }

    @Override
    public void doOnError(String errorMsg) {
        dismissLoading();
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {
        dismissLoading();
    }

    /**
     * 隐藏loading对话框
     */
    private void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
