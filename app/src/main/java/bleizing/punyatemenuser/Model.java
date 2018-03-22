package bleizing.punyatemenuser;

import java.util.ArrayList;

/**
 * Created by Bleizing on 1/21/2018.
 */

public class Model {
    private static CalonPenyewa calonPenyewa;
    private static ArrayList<BarangSewa> barangSewaArrayList;
    private static ArrayList<PermintaanBarang> permintaanBarangArrayList;

    private static double lat = -6.175206;
    private static double lng = 106.827131;

    public static void setCalonPenyewa(CalonPenyewa calonPenyewa) {
        Model.calonPenyewa = calonPenyewa;
    }

    public static CalonPenyewa getCalonPenyewa() {
        return calonPenyewa;
    }

    public static void setBarangSewaArrayList(ArrayList<BarangSewa> barangSewaArrayList) {
        Model.barangSewaArrayList = barangSewaArrayList;
    }

    public static ArrayList<BarangSewa> getBarangSewaArrayList() {
        return barangSewaArrayList;
    }

    public static void setPermintaanBarangArrayList(ArrayList<PermintaanBarang> permintaanBarangArrayList) {
        Model.permintaanBarangArrayList = permintaanBarangArrayList;
    }

    public static ArrayList<PermintaanBarang> getPermintaanBarangArrayList() {
        return permintaanBarangArrayList;
    }

    public static void setLat(double lat) {
        Model.lat = lat;
    }

    public static double getLat() {
        return lat;
    }

    public static void setLng(double lng) {
        Model.lng = lng;
    }

    public static double getLng() {
        return lng;
    }
}
