** Add this line in build.gradle file

dependencies{
  implementation 'com.github.thientrong98:testLib:1.0.2' //add line
  ...
}

** Create Fragment Layout, and add code to file java/kt

kotlin:

override fun onStart() {
    super.onStart()
    FragmentManager.showFragment(activity as AppCompatActivity, null,null,null,null,null,fgMapFirst ="FG_TTQH_SO",bgMapFirst= "BG_NEN_BAN_DO")
}

java:


** include Fragment layout to your Activity

....
 <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragmemt_map"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.example.qqqqq.MapFragment"> //change your name fragment layout file
    </androidx.fragment.app.FragmentContainerView>
....


