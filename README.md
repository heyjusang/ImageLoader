ImageLoader
===========
[![](https://jitpack.io/v/heyjusang/ImageLoader.svg)](https://jitpack.io/#heyjusang/ImageLoader)

Simple Android Url Image Loader

Download
--------
### Gradle:
Step 1. Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ..
    maven { url 'https://jitpack.io' }
  }
}
```
Step 2. Add the dependency:
```gradle	
dependencies {
  implementation 'com.github.heyjusang:ImageLoader:1.0.1-alpha'
}
```

### or Maven:
Step 1. Add the JitPack repository to your build file
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Step 2. Add the dependency:
```xml
<dependency>
  <groupId>com.github.heyjusang</groupId>
  <artifactId>ImageLoader</artifactId>
  <version>1.0.1-alpha</version>
</dependency>
```

Usage
-----
```java
// Basic Usage
ImageLoader.with(context)
  .load(url)
  .into(imageView)

// Add placeholder image
ImageLoader.with(context)
  .load(url)
  .thumbnail(R.drawable.thumbnail)
  .into(imageView)
  
```

Features
--------
* supports memory cache and disk cache
* prevents loading duplicate images 
