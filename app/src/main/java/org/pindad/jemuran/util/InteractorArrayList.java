package org.pindad.jemuran.util;

public interface InteractorArrayList<T> {
    public void onSyncData(T data);
    public void onFailed(T error);
}
