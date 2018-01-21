package bleizing.punyatemenuser;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ItemOneFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "ItemOneFragment";

    GoogleMap mMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private RequestQueue requestQueue;

    private CalonPenyewa calonPenyewa;

    private ArrayList<BarangSewa> barangSewaArrayList;
    private ArrayList<PermintaanBarang> permintaanBarangArrayList;

    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        calonPenyewa = Model.getCalonPenyewa();

        // Request Queue Volley Network Connection
        requestQueue = Volley.newRequestQueue(getActivity());
        barangSewaArrayList = new ArrayList<>();
        permintaanBarangArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_one, container, false);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();
        fragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
            if (barangSewaArrayList != null) {
                if (barangSewaArrayList.size() != 0) {
                    barangSewaArrayList.clear();
                }
            } else {
                barangSewaArrayList = new ArrayList<>();
            }

            if (permintaanBarangArrayList != null) {
                if (permintaanBarangArrayList.size() != 0) {
                    permintaanBarangArrayList.clear();
                }
            } else {
                permintaanBarangArrayList = new ArrayList<>();
            }
            getBarangSewa();
            getPermintaanBarang();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        getBarangSewa();
        getPermintaanBarang();

//        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng b1 = new LatLng(-6.179158, 106.876802);
//        mMap.addMarker(new MarkerOptions().position(b1).title("Jual Baju Second").icon(icon));
//
//        LatLng b2 = new LatLng(-6.180150, 106.875021);
//        mMap.addMarker(new MarkerOptions().position(b2).title("Mobil").icon(icon));
//
//        LatLng b3 = new LatLng(-6.176712, 106.873415);
//        mMap.addMarker(new MarkerOptions().position(b3).title("Motor").icon(icon));
//
//        LatLng b4 = new LatLng(-6.175871, 106.876963);
//        mMap.addMarker(new MarkerOptions().position(b4).title("Kerete Bayi").icon(icon));

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(b1));
        updateBarangLokasi();

        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(20.0f);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void getBarangSewa() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NetAPI.GET_BARANG_SEWA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "getBarangSewa response : " + response);

                try {
                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String nama = jsonObject.getString("nama");
                        String deskripsi = jsonObject.getString("deskripsi");
                        String tgl_mulai = jsonObject.getString("tanggal_mulai");
                        String tgl_berakhir = jsonObject.getString("tanggal_berakhir");
                        String foto = jsonObject.getString("foto");
                        String lat = jsonObject.getString("lat");
                        String lng = jsonObject.getString("lng");
                        int user_penyewa_id = Integer.parseInt(jsonObject.getString("user_penyewa_id"));

                        BarangSewa barangSewa = new BarangSewa(id, nama, deskripsi, tgl_mulai, tgl_berakhir, foto, lat, lng, user_penyewa_id);
                        barangSewaArrayList.add(barangSewa);
                    }
                    Model.setBarangSewaArrayList(barangSewaArrayList);
                    updateBarangLokasi();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error getBarangSewa : " + error);
            }
        });
        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void getPermintaanBarang() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NetAPI.GET_PERMINTAAN_BARANG_BY_CALON_PENYEWA_ID + calonPenyewa.getId(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "getPermintaanBarang response : " + response);

                try {
                    JSONArray data = response.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject = data.getJSONObject(i);
                        int id = Integer.parseInt(jsonObject.getString("id"));
                        String nama = jsonObject.getString("nama");
                        String deskripsi = jsonObject.getString("deskripsi");
                        String tgl_mulai = jsonObject.getString("tanggal_mulai");
                        String tgl_berakhir = jsonObject.getString("tanggal_berakhir");
                        String lat = jsonObject.getString("lat");
                        String lng = jsonObject.getString("lng");
                        int calon_penyewa_id = Integer.parseInt(jsonObject.getString("calon_penyewa_id"));

                        PermintaanBarang permintaanBarang = new PermintaanBarang(id, nama, deskripsi, tgl_mulai, tgl_berakhir, lat, lng, calon_penyewa_id);
                        permintaanBarangArrayList.add(permintaanBarang);
                    }
                    Model.setPermintaanBarangArrayList(permintaanBarangArrayList);
                    updatePermintaanBarangLokasi();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error getPermintaanBarang : " + error);
            }
        });
        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void updateBarangLokasi() {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        if (barangSewaArrayList.size() != 0) {
            if (mMap != null) {
                for (BarangSewa barangSewa : barangSewaArrayList) {
                    LatLng lokasiBarang = new LatLng(Double.parseDouble(barangSewa.getLat()), Double.parseDouble(barangSewa.getLng()));
                    mMap.addMarker(new MarkerOptions().position(lokasiBarang).title(barangSewa.getNama()).icon(icon));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiBarang));
                }
            }
        } else {
            // LatLng Monumen Nasional as Default
            LatLng lokasiBarang = new LatLng(-6.175206, 106.827131);
//            mMap.addMarker(new MarkerOptions().position(lokasiBarang).title("Jual Baju Second").icon(icon));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiBarang));
        }
    }

    private void updatePermintaanBarangLokasi() {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        if (permintaanBarangArrayList.size() != 0) {
            if (mMap != null) {
                for (PermintaanBarang permintaanBarang : permintaanBarangArrayList) {
                    LatLng lokasiBarang = new LatLng(Double.parseDouble(permintaanBarang.getLat()), Double.parseDouble(permintaanBarang.getLng()));
                    mMap.addMarker(new MarkerOptions().position(lokasiBarang).title(permintaanBarang.getNama()).icon(icon));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiBarang));
                }
            }
        } else {
            // LatLng Monumen Nasional as Default
            LatLng lokasiBarang = new LatLng(-6.175206, 106.827131);
//            mMap.addMarker(new MarkerOptions().position(lokasiBarang).title("Jual Baju Second").icon(icon));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiBarang));
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        Intent intent = new Intent(getActivity(), DetailItemInput.class);
//        intent.putExtra("BarangId", marker.getId());
//        startActivity(intent);
//        Log.d(TAG, "BarangId = " + marker.getId());
//        Toast.makeText(getActivity(), "BarangId = " + marker.getId(), Toast.LENGTH_SHORT).show();
        return false;
    }
}