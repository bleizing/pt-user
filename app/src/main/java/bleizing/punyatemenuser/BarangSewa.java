package bleizing.punyatemenuser;

/**
 * Created by Bleizing on 1/21/2018.
 */

public class BarangSewa {
    private int id;
    private String nama = "";
    private String deskripsi = "";
    private String tgl_mulai = "";
    private String tgl_berakhir = "";
    private String foto_barang = "";
    private String lat = "";
    private String lng = "";
    private Double harga;
    private int user_penyewa_id;
    private String user_penyewa_nama;
    private String user_penyewa_no_hp;

    public BarangSewa() {

    }

    public BarangSewa(int id, String nama, String deskripsi, String tgl_mulai, String tgl_berakhir, String foto_barang, String lat, String lng, Double harga, int user_penyewa_id, String user_penyewa_nama, String user_penyewa_no_hp) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tgl_mulai = tgl_mulai;
        this.tgl_berakhir = tgl_berakhir;
        this.foto_barang = foto_barang;
        this.lat = lat;
        this.lng = lng;
        this.harga = harga;
        this.user_penyewa_id = user_penyewa_id;
        this.user_penyewa_nama = user_penyewa_nama;
        this.user_penyewa_no_hp = user_penyewa_no_hp;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setFoto_barang(String foto_barang) {
        this.foto_barang = foto_barang;
    }

    public String getFoto_barang() {
        return foto_barang;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setTgl_berakhir(String tgl_berakhir) {
        this.tgl_berakhir = tgl_berakhir;
    }

    public String getTgl_berakhir() {
        return tgl_berakhir;
    }

    public void setTgl_mulai(String tgl_mulai) {
        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_mulai() {
        return tgl_mulai;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public Double getHarga() {
        return harga;
    }

    public void setUser_penyewa_id(int user_penyewa_id) {
        this.user_penyewa_id = user_penyewa_id;
    }

    public int getUser_penyewa_id() {
        return user_penyewa_id;
    }

    public void setUser_penyewa_nama(String user_penyewa_nama) {
        this.user_penyewa_nama = user_penyewa_nama;
    }

    public String getUser_penyewa_nama() {
        return user_penyewa_nama;
    }

    public void setUser_penyewa_no_hp(String user_penyewa_no_hp) {
        this.user_penyewa_no_hp = user_penyewa_no_hp;
    }

    public String getUser_penyewa_no_hp() {
        return user_penyewa_no_hp;
    }
}
