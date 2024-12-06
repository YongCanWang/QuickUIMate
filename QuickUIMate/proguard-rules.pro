# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



    #
    # ----------------------------- 基本 -----------------------------
    #
    #----------------------------------------------------
    # 指定代码的压缩级别 0 - 7(指定代码进行迭代优化的次数，在Android里面默认是5，这条指令也只有在可以优化时起作用。)
    -optimizationpasses 5
    # 混淆时不会产生形形色色的类名(混淆时不使用大小写混合类名)
    -dontusemixedcaseclassnames
    # 指定不去忽略非公共的库类(不跳过library中的非public的类)
    -dontskipnonpubliclibraryclasses
    # 指定不去忽略包可见的库类的成员
    -dontskipnonpubliclibraryclassmembers
    #不进行优化，建议使用此选项，
    -dontoptimize
     # 不进行预校验,Android不需要,可加快混淆速度。
    -dontpreverify
    # 屏蔽警告
    -ignorewarnings
    # 指定混淆是采用的算法，后面的参数是一个过滤器
    # 这个过滤器是谷歌推荐的算法，一般不做更改
    -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
    # 保护代码中的Annotation不被混淆
    -keepattributes *Annotation*
    # 避免混淆泛型, 这在JSON实体映射时非常重要
    -keepattributes Signature
    # 抛出异常时保留代码行号
    -keepattributes SourceFile,LineNumberTable
     #优化时允许访问并修改有修饰符的类和类的成员，这可以提高优化步骤的结果。
    # 比如，当内联一个公共的getter方法时，这也可能需要外地公共访问。
    # 虽然java二进制规范不需要这个，要不然有的虚拟机处理这些代码会有问题。当有优化和使用-repackageclasses时才适用。
    #指示语：不能用这个指令处理库中的代码，因为有的类和类成员没有设计成public ,而在api中可能变成public
    -allowaccessmodification
    #当有优化和使用-repackageclasses时才适用。
    -repackageclasses ''
     # 混淆时记录日志(打印混淆的详细信息)
     # 这句话能够使我们的项目混淆后产生映射文件
     # 包含有类名->混淆后类名的映射关系
    -verbose

    #
    # ----------------------------- 默认保留 -----------------------------
    #
    #----------------------------------------------------
    # 保持哪些类不被混淆
    #继承activity,application,service,broadcastReceiver,contentprovider....不进行混淆
    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.support.multidex.MultiDexApplication
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class * extends android.view.View


    # 保留support下的所有类及其内部类
    -keep class android.support.** {*;}

    # 接入Google原生的一些服务时使用
    -keep public class com.google.vending.licensing.ILicensingService
    -keep public class com.android.vending.licensing.ILicensingService

    # 保留继承的
    -keep public class * extends android.support.v4.**
    -keep public class * extends android.support.v7.**
    -keep public class * extends android.support.annotation.**

    # 保留R下面的资源
    -keep class **.R$* {*;}

   #不混淆资源类
   -keepclassmembers class **.R$* {
       public static <fields>;
   }

   #表示不混淆任何包含native方法的类的类名以及native方法名，这个和我们刚才验证的结果是一致
   -keepclasseswithmembernames class * {
       native <methods>;
   }

   #这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
   #表示不混淆Activity中参数是View的方法，因为有这样一种用法，在XML中配置android:onClick=”buttonClick”属性，
   #当用户点击该按钮时就会调用Activity中的buttonClick(View view)方法，如果这个方法被混淆的话就找不到了
   -keepclassmembers class * extends android.app.Activity{
       public void *(android.view.View);
   }

   #表示不混淆枚举中的values()和valueOf()方法，枚举我用的非常少，这个就不评论了
   -keepclassmembers enum * {
       public static **[] values();
       public static ** valueOf(java.lang.String);
   }

   #表示不混淆任何一个View中的setXxx()和getXxx()方法，
   #因为属性动画需要有相应的setter和getter的方法实现，混淆了就无法工作了。
   -keep public class * extends android.view.View{
       *** get*();
       void set*(***);
       public <init>(android.content.Context);
       public <init>(android.content.Context, android.util.AttributeSet);
       public <init>(android.content.Context, android.util.AttributeSet, int);
   }
   -keepclasseswithmembers class * {
       public <init>(android.content.Context, android.util.AttributeSet);
       public <init>(android.content.Context, android.util.AttributeSet, int);
   }


   #表示不混淆Parcelable实现类中的CREATOR字段，
   #毫无疑问，CREATOR字段是绝对不能改变的，包括大小写都不能变，不然整个Parcelable工作机制都会失败。
   -keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
   }

   # 这指定了继承Serizalizable的类的如下成员不被移除混淆
   -keepclassmembers class * implements java.io.Serializable {
       static final long serialVersionUID;
       private static final java.io.ObjectStreamField[] serialPersistentFields;
       !static !transient <fields>;
       !private <fields>;
       !private <methods>;
       private void writeObject(java.io.ObjectOutputStream);
       private void readObject(java.io.ObjectInputStream);
       java.lang.Object writeReplace();
       java.lang.Object readResolve();
   }


   # 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
   -keepclassmembers class * {
       void *(**On*Event);
       void *(**On*Listener);
   }


   # 保留我们自定义控件（继承自View）不被混淆
   -keep public class * extends android.view.View{
       *** get*();
       void set*(***);
       public <init>(android.content.Context);
       public <init>(android.content.Context, android.util.AttributeSet);
       public <init>(android.content.Context, android.util.AttributeSet, int);
   }

   #
   #----------------------------- WebView(项目中没有可以忽略) -----------------------------
   #
   #webView需要进行特殊处理
   -keepclassmembers class fqcn.of.javascript.interface.for.Webview {
      public *;
   }
   -keepclassmembers class * extends android.webkit.WebViewClient {
       public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
       public boolean *(android.webkit.WebView, java.lang.String);
   }
   -keepclassmembers class * extends android.webkit.WebViewClient {
       public void *(android.webkit.WebView, jav.lang.String);
   }
   #在app中与HTML5的JavaScript的交互进行特殊处理
   #我们需要确保这些js要调用的原生方法不能够被混淆，于是我们需要做如下处理：
   -keepclassmembers class com.ljd.example.JSInterface {
       <methods>;
   }

   #
   #---------------------------------实体类---------------------------------
   #--------(实体Model不能混淆，否则找不到对应的属性获取不到值)-----
   #
   #修改成你对应的包名
   #-dontwarn com.test.mode.**
   #对含有反射类的处理
   #-keep class com.test.mode.** { *; }
   #修改成你对应的包名(view)，成对用
   #-dontwarn com.test.view.**
   #-keep class com.test.view.** { *; }



    -keep public class * extends android.app.Activity
    -keep public class * extends android.app.Application
    -keep public class * extends android.app.Service
    -keep public class * extends android.content.BroadcastReceiver
    -keep public class * extends android.content.ContentProvider
    -keep public class * extends android.app.backup.BackupAgentHelper
    -keep public class * extends android.preference.Preference
    -keep public class com.android.vending.licensing.ILicensingService
    -keep public class * extends android.app.Fragment
    -keep public class * extends android.support.v4.**
    -keep public class * extends android.support.annotation.**
    -keep public class * extends android.support.v7.**
    -keep public class android.app.Notification
    -keep public class android.webkit.**
    #保护WebView对HTML页面的API不被混淆
    -keep class **.Webview2JsInterface {*; }
    -keep public class * extends android.app.Dialog
    -keep public class * extends android.view


    -keep public class com.tomcan.frame.v.LoadingQuickFragment {
     public <init>(...);
    }

    -keepclassmembers class  com.tomcan.frame.v.LoadingQuickFragment {
                 public void create**(...);
                 public * get**();
                 public void set**(...);
                 public void  show**(...);
                 public void  dismiss**(...);
    }

    -keep public interface com.tomcan.frame.v.LoadingQuickFragment$OnBaseFragListener{
              *;
     }

    -keep public interface com.tomcan.quickui.mate.OnStackObservable{
                   *;
      }


    -keep public class * extends com.tomcan.frame.v.LoadingQuickFragment
#    -keep public class  com.tomcan.quickuimate.v.NaviBaseFragment {
#
#    }
    -keep public class com.tomcan.quickui.mate.FragmentMate


    -keep public class com.tomcan.quickui.adapter.*
    -keepclassmembernames class com.tomcan.quickui.v.QuickBaseFragment {
       android.content.Context context;
      androidx.appcompat.app.AppCompatActivity activity;
      android.view.View v; android.view.View TagView;
      com.tomcan.quickui.v.QuickBaseFragment lastStckFragment;
      java.lang.String TAG; androidx.databinding.ViewDataBinding binding; com.tomcan.frame.vm.QuickViewModel vm;
    }

        -keepclassmembernames class com.tomcan.frame.vm.QuickViewModel {
            public java.lang.String TAG;
            public  android.app.Application application;
        }

        -keep public interface com.tomcan.quickui.v.QuickBaseFragment$CallBackLifecycle {
                    *;
        }

        -keep public interface com.tomcan.quickui.v.QuickBaseFragment$BackHandlerInterface {
                        *;
        }



    -keepclassmembers class  com.tomcan.quickui.v.QuickBaseFragment {

                public  int initViewID();

                public  com.tomcan.frame.vm.QuickViewModel initViewModel();

                public  void setView();

                public  void function();

                public  void resume();

                public  void loseTrack();

                public  void loseView();

                public  void finish();

                public  boolean back();

                public boolean isTop(...);

                 public void pop();
                 public void remove();
                 public void popRemove();

                 public * get**(...);
                 public void set**(...);
    }

        -keepclassmembers class com.tomcan.quickui.mate.FragmentMate {
            public ** getInstance();
            public void **Container(...);
            public void setAttachActivity(...);
            public void add(...);
            public void show(...);
            public void replaceStack(...);
            public void replaceAnimatorStack(...);
            public void pop**(...);
            public void remove**(...);
            public boolean isStacksEmpty();
            public void setStacksObservable(...);
            public android.widget.FrameLayout getContainerLayout();

        }



    -keep public class **.*abstract*.** {*;}


    # 所有枚举类型不要混淆
    -keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    }

    # 保持 native 方法不被混淆
    -keepclasseswithmembernames class * {
    native <methods>;
    }

    #保持R文件不被混淆，否则，你的反射是获取不到资源id的
    -keep class **.R*{*;}

    # parcelable 不被混淆
    -keep class * implements android.os.Parcelable {
    public static finalandroid.os.ParcelableCreator *;
    }

    #保持实现"Serializable"接口的类不被混淆
    -keepnames class * implements java.io.Serializable

    #保护实现接口Serializable的类中，指定规则的类成员不被混淆
    -keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <methods>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    }