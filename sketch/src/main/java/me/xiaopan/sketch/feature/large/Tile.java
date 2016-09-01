/*
 * Copyright (C) 2016 Peng fei Pan <sky@xiaopan.me>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.sketch.feature.large;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Tile implements ObjectPool.CacheStatus {
    public Rect drawRect = new Rect();
    public Rect srcRect = new Rect();
    public int inSampleSize;
    public float scale = -1;

    public Bitmap bitmap;
    public Rect bitmapDrawSrcRect = new Rect();

    private boolean inCachePool;
    private KeyNumber keyNumber = new KeyNumber();

    public Tile() {
    }

    public boolean isEmpty() {
        return bitmap == null || bitmap.isRecycled() || isDecodeParamEmpty();
    }

    public boolean isDecodeParamEmpty() {
        return drawRect.isEmpty() || drawRect.isEmpty()
                || srcRect.isEmpty() || srcRect.isEmpty()
                || inSampleSize == 0
                || scale == -1;
    }

    @SuppressWarnings("unused")
    public void clean() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        bitmapDrawSrcRect.setEmpty();

        srcRect.setEmpty();
        drawRect.setEmpty();

        inSampleSize = 0;
        scale = -1;
    }

    public int getKey() {
        return keyNumber.getKey();
    }

    public void refreshKey() {
        keyNumber.refresh();
    }

    public String getInfo(){
        //noinspection StringBufferReplaceableByString
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append("drawRect:").append(drawRect.toShortString());
        builder.append(",");
        builder.append("srcRect:").append(srcRect.toShortString());
        builder.append(",");
        builder.append("inSampleSize:").append(inSampleSize);
        builder.append(",");
        builder.append("scale:").append(scale);
        builder.append(",");
        builder.append("key:").append(keyNumber.getKey());
        builder.append(",");
        builder.append("hashCode:").append(Integer.toHexString(hashCode()));
        builder.append(")");
        return builder.toString();
    }

    public boolean isExpire(int key) {
        return keyNumber.getKey() != key;
    }

    @Override
    public boolean isInCachePool() {
        return inCachePool;
    }

    @Override
    public void setInCachePool(boolean inCachePool) {
        this.inCachePool = inCachePool;
    }
}
