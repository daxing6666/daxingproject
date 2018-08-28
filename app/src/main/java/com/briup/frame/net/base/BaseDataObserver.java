package com.briup.frame.net.base;

import com.briup.frame.net.bean.BaseData;
import com.briup.frame.net.exception.ApiException;
import com.briup.frame.net.interfaces.IDataSubscriber;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 基类BaseObserver使用BaseData
 */

public abstract class BaseDataObserver<T> implements Observer<BaseData<T>>, IDataSubscriber<T> {

    /**
     * 是否隐藏toast
     *
     * @return
     */
    protected boolean isHideToast() {
        return false;
    }

    @Override
    public void onSubscribe(Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(BaseData<T> baseData) {
        doOnNext(baseData);
    }

    @Override
    public void onError(Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }


    private void setError(String errorMsg) {
        doOnError(errorMsg);
    }

}
