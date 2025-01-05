# Add any ProGuard configurations specific to this
# extension here.
-keep public class com.sumit1334.listview** {
    public *;
 }

-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/sumit/listview/repack'
-flattenpackagehierarchy
-dontpreverify
#-keep class androidx.recyclerview.widget.RecyclerView { *; }
#-dontwarn androidx.recyclerview**
#
#-keepclassmembers class androidx.recyclerview.widget.RecyclerView {
#    public void suppressLayout(boolean);
#    public boolean isLayoutSuppressed();
#}

-dontwarn androidx.**
-dontwarn com.google.**
-dontwarn com.sumit1334.**