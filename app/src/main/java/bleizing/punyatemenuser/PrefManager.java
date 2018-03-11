package bleizing.punyatemenuser;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bleizing on 1/8/2018.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String ID = "id";
    private static final String NAMA = "nama";
    private static final String TEMPAT_LAHIR = "tempat_lahir";
    private static final String TANGGAL_LAHIR = "tanggal_lahir";
    private static final String JEKEL = "jekel";
    private static final String ALAMAT = "alamat";
    private static final String NO_HP = "no_hp";
    private static final String NO_KTP = "no_ktp";
    private static final String EMAIL = "email";
    private static final String FOTO_KTP = "foto_ktp";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setUser(CalonPenyewa calonPenyewa) {
        editor.putInt(ID, calonPenyewa.getId());
        editor.putString(NAMA, calonPenyewa.getNama());
        editor.putString(TEMPAT_LAHIR, calonPenyewa.getTempat_lahir());
        editor.putString(TANGGAL_LAHIR, calonPenyewa.getTanggal_lahir());
        editor.putString(JEKEL, calonPenyewa.getJekel());
        editor.putString(ALAMAT, calonPenyewa.getAlamat());
        editor.putString(NO_HP, calonPenyewa.getNo_hp());
        editor.putString(NO_KTP, calonPenyewa.getNo_ktp());
        editor.putString(EMAIL, calonPenyewa.getEmail());
        editor.putString(FOTO_KTP, calonPenyewa.getFoto_ktp());

        editor.commit();
    }

    public boolean getUser() {
        CalonPenyewa calonPenyewa = new CalonPenyewa();
        calonPenyewa.setId(pref.getInt(ID, 0));
        calonPenyewa.setNama(pref.getString(NAMA, ""));
        calonPenyewa.setTempat_lahir(pref.getString(TEMPAT_LAHIR, ""));
        calonPenyewa.setTanggal_lahir(pref.getString(TANGGAL_LAHIR, ""));
        calonPenyewa.setJekel(pref.getString(JEKEL, ""));
        calonPenyewa.setAlamat(pref.getString(ALAMAT, ""));
        calonPenyewa.setNo_hp(pref.getString(NO_HP, ""));
        calonPenyewa.setNo_ktp(pref.getString(NO_KTP, ""));
        calonPenyewa.setEmail(pref.getString(EMAIL, ""));
        calonPenyewa.setFoto_ktp(pref.getString(FOTO_KTP, ""));

        if (calonPenyewa.getId() != 0) {
            Model.setCalonPenyewa(calonPenyewa);
            return true;
        } else {
            return false;
        }
    }

    public  void clearUser() {
        editor.putInt(ID, 0);
        editor.putString(NAMA, "");
        editor.putString(TEMPAT_LAHIR, "");
        editor.putString(TANGGAL_LAHIR, "");
        editor.putString(JEKEL, "");
        editor.putString(ALAMAT, "");
        editor.putString(NO_HP, "");
        editor.putString(NO_KTP, "");
        editor.putString(EMAIL, "");
        editor.putString(FOTO_KTP, "");

        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }
}
