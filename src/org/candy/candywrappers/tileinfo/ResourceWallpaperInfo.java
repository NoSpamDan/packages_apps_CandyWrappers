package org.candy.candywrappers.tileinfo;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import org.candy.photos.BitmapRegionTileSource;
import org.candy.photos.BitmapRegionTileSource.BitmapSource;
import org.candy.candywrappers.WallpaperCropActivity.CropViewScaleAndOffsetProvider;
import org.candy.candywrappers.WallpaperPickerActivity;

public class ResourceWallpaperInfo extends DrawableThumbWallpaperInfo {

    private final Resources mResources;
    private final int mResId;

    public ResourceWallpaperInfo(Resources res, int resId, Drawable thumb) {
        super(thumb);
        mResources = res;
        mResId = resId;
    }

    @Override
    public void onClick(final WallpaperPickerActivity a) {
        a.setWallpaperButtonEnabled(false);
        final BitmapRegionTileSource.InputStreamSource bitmapSource =
                new BitmapRegionTileSource.InputStreamSource(mResources, mResId, a);
        a.setCropViewTileSource(bitmapSource, false, false, new CropViewScaleAndOffsetProvider() {

            @Override
            public float getScale(Point wallpaperSize, RectF crop) {
                return wallpaperSize.x /crop.width();
            }

            @Override
            public float getParallaxOffset() {
                return a.getWallpaperParallaxOffset();
            }
        }, new Runnable() {

            @Override
            public void run() {
                if (bitmapSource.getLoadingState() == BitmapSource.State.LOADED) {
                    a.setWallpaperButtonEnabled(true);
                }
            }
        });
    }

    @Override
    public void onSave(WallpaperPickerActivity a) {
        a.cropImageAndSetWallpaper(mResources, mResId, true /* shouldFadeOutOnFinish */);
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public boolean isNamelessWallpaper() {
        return true;
    }
}
