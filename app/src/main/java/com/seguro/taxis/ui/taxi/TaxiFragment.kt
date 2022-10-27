package com.seguro.taxis.ui.taxi

import android.Manifest
import android.Manifest.permission.CALL_PHONE
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuponstore.linkers.Activities.taxi.adapter.TaxiAdapter
import com.facebook.FacebookSdk
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.firebase.database.*
import com.google.gson.Gson
import com.seguro.taxis.R
import com.seguro.taxis.databinding.FragmentTaxiBinding
import com.seguro.taxis.firebase.references.FirebaseKeys
import com.seguro.taxis.manager.Constants
import com.seguro.taxis.manager.MyUserManager
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.models.TaxiModel
import com.seguro.taxis.ui.base.BaseFragment
import com.seguro.taxis.ui.configuration.location.MapData
import com.seguro.taxis.ui.login.LoginOptionsActivity
import com.seguro.taxis.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_taxi.*
import okhttp3.OkHttpClient
import okhttp3.Request

class TaxiFragment : BaseFragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentTaxiBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var userLocationClient: FusedLocationProviderClient
    private lateinit var userLocationCallback: LocationCallback
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var taxiAdapter: TaxiAdapter? = null
    private var userMarker: Marker? = null
    private var destinationLatitude: Double? = 19.691754712345585
    private var destinationLongitude: Double? = -100.54664541658678

    var userId = String()
    var servicesNumber: Int = 0
    var addressLat: String? = null
    var addressLong: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTaxiBinding.inflate(inflater, container, false)

        mapFragment =
            childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(binding.root.context)

        locationUser()
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }


    override fun init() {
        if (ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                destinationLatitude = location?.latitude
                destinationLongitude = location?.longitude
        }
        rvTakeTransport?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        setupAdapter()
        getInfoTaxi()
        getLastLocation()
    }

    override fun isWifi(wifi: Boolean) {}

    override fun setUp(view: View?) {}

    private fun getInfoTaxi() {
        showLoader()

        val preferences = SharedPreferencesManager(requireActivity())
        val locationGPS = preferences.getLocationGPS()
        val taxiModel = arrayListOf<TaxiModel>()

        databaseRef = FirebaseDatabase.getInstance().reference
        val rootRef = Constants.BaseFirebaseReference.child(FirebaseKeys.profileTaxi).child(userId)

        rootRef.orderByChild(FirebaseKeys.location).equalTo(locationGPS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    hideLoader()
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    servicesNumber = dataSnapshot.children.count()

                    if (servicesNumber != 0) {
                        val children = dataSnapshot.children
                        children.forEach {
                            println("The element is $it")
                            val car = it.child("car").value as? String ?: ""
                            val nameSiteTaxi = it.child("nameSiteTaxi").value as? String ?: ""
                            val nameTaxi = it.child("nameTaxi").value as? String ?: ""
                            val schedule = it.child("schedule").value as? String ?: ""
                            val phoneTaxi = it.child("phoneTaxi").value as? String ?: ""
                            val photoTaxi = it.child("photoTaxi").value as? String ?: ""
                            val lat = it.child("lat").value as? String ?: ""
                            val long = it.child("long").value as? String ?: ""

                            val key = it.key
                            val isAvailableTaxi = it.child("availableTaxi").value as? Boolean
                            hideLoader()
                            taxiModel.add(
                                TaxiModel(
                                    car,
                                    nameSiteTaxi,
                                    nameTaxi,
                                    phoneTaxi,
                                    schedule,
                                    photoTaxi,
                                    key,
                                    "",
                                    isAvailableTaxi,
                                    lat,
                                    long
                                )
                            )
                            taxiAdapter?.swap(taxiModel)
                        }
                    } else {
                        //UIUtils.AlertCustomGeneral(activity!!, Constants.ALERT_SUBTITLE_TAXI, "#D2FC6500")
                        hideLoader()
                    }
                }
            })
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        userLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val userLocationRequest = LocationRequest().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        userLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    getLocation(location.latitude.toString(), location.longitude.toString())
                    userLocationClient.removeLocationUpdates(userLocationCallback)
                }
            }
        }

        userLocationClient.requestLocationUpdates(userLocationRequest, userLocationCallback, null)
    }

    fun getLocation(latitude: String, longitude: String) {
        addressLat = latitude
        addressLong = longitude
    }

    fun setupAdapter() {
        taxiAdapter = TaxiAdapter(
            activity as MainActivity,
            FacebookSdk.getApplicationContext(),
            object : TaxiAdapter.TaxiAdapterDelegate {
                override fun tapProfileTaxiButton(taxiModel: TaxiModel) {
                    setupMap(taxiModel.lat?.toDouble() ?: 0.0, taxiModel.long?.toDouble() ?: 0.0)
                }

                override fun tapWhatsAppCallButton(taxiModel: TaxiModel) {
                    if (!MyUserManager.instance.isSignedIn()!!) {
                        val intent = Intent(activity, LoginOptionsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        val intent = Intent(Intent.ACTION_VIEW)

                        if (addressLat != null) {
                            val uri =
                                "https://api.whatsapp.com/send?phone=52${taxiModel.phoneTaxi}&text=Cupón Store Taxi: Requiero un taxi en esta dirección -> http://maps.google.com/maps?q=loc:${addressLat},${addressLong}"
                            intent.data = Uri.parse(uri)
                        } else {
                            val uri =
                                "https://api.whatsapp.com/send?phone=52${taxiModel.phoneTaxi}&text=CupónStoreTaxi: ¡Requiero un Taxi!"
                            intent.data = Uri.parse(uri)
                        }
                        startActivity(intent)
                    }
                }

                override fun tapCallButton(taxiModel: TaxiModel) {
                    if (!MyUserManager.instance.isSignedIn()!!) {
                        val intent = Intent(activity, LoginOptionsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:" + taxiModel.phoneTaxi)

                        if (ContextCompat.checkSelfPermission(
                                activity!!,
                                CALL_PHONE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            startActivity(callIntent)
                        } else {
                            requestPermissions(arrayOf(CALL_PHONE), 1)
                        }
                    }
                }
            })

        rvTakeTransport?.adapter = taxiAdapter
    }

    //Setup Map
    private fun setupMap(lat: Double, long: Double) {
        val ai: ApplicationInfo = binding.root.context.applicationContext.packageManager
            .getApplicationInfo(
                binding.root.context.applicationContext.packageName,
                PackageManager.GET_META_DATA
            )


        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()

        if (!Places.isInitialized()) {
            Places.initialize(binding.root.context.applicationContext, apiKey)
        }

        mapFragment.getMapAsync {
            mMap = it
            mMap.clear()
            val originLocation =
                LatLng(lat, long)
            userMarker = mMap.addMarker(
                MarkerOptions()
                    .position(originLocation)
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin_taxi))
            )

            val destinationLocation =
                LatLng(destinationLatitude ?: 0.0, destinationLongitude ?: 0.0)
            userMarker = mMap.addMarker(
                MarkerOptions()
                    .position(destinationLocation)
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
            )

            val urll = getDirectionURL(originLocation, destinationLocation, apiKey)
            GetDirection(urll).execute()
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    originLocation,
                    15F
                )
            )
        }
    }

    private fun locationUser() {
        mapFragment.getMapAsync {
            mMap = it
            mMap.clear()
            val destinationLocation =
                LatLng(destinationLatitude ?: 0.0, destinationLongitude ?: 0.0)
            userMarker = mMap.addMarker(
                MarkerOptions()
                    .position(destinationLocation)
                    .title("")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))
            )
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    destinationLocation,
                    15F
                )
            )
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        if (p0 != null) {
            mMap = p0
        }
        mMap.clear()
    }

    private fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()

            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path = ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(18f)
                lineoption.color(Color.RED)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }


    companion object {
        private val IDENTIFIER = "TaxiFragment"
        fun newInstance(): TaxiFragment {
            return TaxiFragment()
        }
    }
}
