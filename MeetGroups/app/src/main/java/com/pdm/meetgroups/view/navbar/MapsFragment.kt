package com.pdm.meetgroups.view.navbar

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pdm.meetgroups.R
import com.pdm.meetgroups.databinding.FragmentMapsBinding
import com.pdm.meetgroups.model.entities.Journal
import com.pdm.meetgroups.viewmodel.maps.MapsViewModelImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {
    private var nearJournalsHash: MutableLiveData<Hashtable<Location, Journal>?> = MutableLiveData()
    private val mapsVMImpl : MapsViewModelImpl by viewModels()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient : FusedLocationProviderClient
    private lateinit var binding: FragmentMapsBinding

    companion object {
        const val LOCATION_REQUEST_CODE = 1
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setupMap()
    }

    private fun setupMap() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE
            )
            return
        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                if (mapsVMImpl.isAdmin()) {
                    mapsVMImpl.updateUserLocation(location)
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
                }
                CoroutineScope(Dispatchers.IO).launch {
                    nearJournalsHash.postValue(mapsVMImpl.getNearJournalsAndLocations(location))
                }
            }
        }
    }

    private fun placeMarkerOnApp(location : Location, journal: Journal) {
        val currentLatLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions().position(currentLatLng)
        markerOptions.title(journal.getAdmin().getState().nickname)

        map.addMarker(markerOptions)?.tag = nearJournalsHash.value?.get(location)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMapsBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        nearJournalsHash.observe(viewLifecycleOwner, { hash ->
            hash?.forEach { entry ->
                placeMarkerOnApp(entry.key, entry.value)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        //show the journal readOnly activity
        val journal = p0.tag as Journal
        return false
    }
}