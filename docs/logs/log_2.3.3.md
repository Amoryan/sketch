这是一个bug修复版本，如果你使用了lowQualityImage属性，那么强烈建议升级

bugs：
* 修复在kitkat以上版本使用lowQualityImage属性时对bitmap执行reconfigure会抛出"IllegalArgumentException: Bitmap not large enough to support new configuration"异常导致图片显示失败的bug

其它：
* 在kitkat以上版本Options的bitmapConfig属性和ImageFormat的lowQualityConfig属性无法使用ARGB_4444
