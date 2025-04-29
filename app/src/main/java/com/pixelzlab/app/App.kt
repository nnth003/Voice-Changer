package com.pixelzlab.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by pixelzlab on 07/09/2022.
 */
@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        configTimber()
    }

    private fun configTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * Since we're displaying SVGs in the app, Coil needs an ImageLoader which supports this
     * format. During Coil's initialization it will call `applicationContext.newImageLoader()` to
     * obtain an ImageLoader.
     *
     * @see https://github.com/coil-kt/coil/blob/main/coil-singleton/src/main/java/coil/Coil.kt#L63
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
}