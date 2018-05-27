package org.pindad.jemuran.util;

import java.util.List;

public interface Interactor<T> {
    public void onSyncData(T data);
    public void onFailed(T error);
}
