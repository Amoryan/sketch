/*
 * Copyright 2013 Peng fei Pan
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

package me.panpf.sketch.sample

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import me.panpf.sketch.sample.util.DataTransferStation

abstract class BaseActivity : AppCompatActivity() {
    val toolbar: Toolbar? by bindOptionalView(R.id.toolbar)
    val dataTransferHelper = DataTransferStation.PageHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataTransferHelper.onCreate(savedInstanceState)

        val bindContentView = javaClass.getAnnotation(BindContentView::class.java)
        if (bindContentView != null && bindContentView.value > 0) {
            setContentView(bindContentView.value)
        }

        if (toolbar != null) {
            onPreSetSupportActionBar()
            setSupportActionBar(toolbar)
            onPostSetSupportActionBar()
        }
    }

    override fun setContentView(layoutResID: Int) {
        setTransparentStatusBar()
        super.setContentView(layoutResID)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        setTransparentStatusBar()
        super.setContentView(view, params)
    }

    override fun setContentView(view: View) {
        setTransparentStatusBar()
        super.setContentView(view)
    }

    protected fun onPreSetSupportActionBar() {

    }

    protected fun onPostSetSupportActionBar() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onContentChanged() {
        super.onContentChanged()

        if (!isDisableSetFitsSystemWindows()) {
            setFitsSystemWindows()
        }
    }

    /**
     * 让状态栏完全透明
     */
    private fun setTransparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setFitsSystemWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val contentViewGroup = findViewById(android.R.id.content) as ViewGroup
            if (contentViewGroup != null && contentViewGroup.childCount > 0) {
                contentViewGroup.getChildAt(0).fitsSystemWindows = true
            }
        }
    }

    open fun isDisableSetFitsSystemWindows(): Boolean{
        return false
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        dataTransferHelper.onSaveInstanceState(outState)
    }

    public override fun onDestroy() {
        super.onDestroy()
        dataTransferHelper.onDestroy()
    }
}
