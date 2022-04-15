** Add this line in build.gradle file <br />

dependencies{ <br />
    ... <br />
    implementation 'com.github.thientrong98:testLib:1.0.2' //add line  <br />
    ... <br />
}<br />

** Create Fragment Layout, and add code to file java/kt<br />

kotlin:<br />

override fun onStart() {<br />
super.onStart()<br />
FragmentManager.showFragment(activity as AppCompatActivity, location = LatLng(),center=null,bb=null, zoom = null, maxZoom = null,
minZoom = null,fgMapFirst ="FG_TTQH_SO",bgMapFirst= "BG_NEN_BAN_DO", tileBaseMap = "", tileSatellite = "")<br />
}<br />

java:<br />

** include Fragment layout to your Activity<br />

