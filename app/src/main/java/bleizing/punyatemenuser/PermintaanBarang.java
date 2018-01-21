package bleizing.punyatemenuser;

/**
 * Created by Bleizing on 1/21/2018.
 */

public class PermintaanBarang {
    private int id;
    private String nama = "";
    private String deskripsi = "";
    private String tgl_mulai = "";
    private String tgl_berakhir = "";
    private String lat = "";
    private String lng = "";
    private int calon_penyewa_id;

    public PermintaanBarang() {

    }

    public PermintaanBarang(int id, String nama, String deskripsi, String tgl_mulai, String tgl_berakhir, String lat, String lng, int calon_penyewa_id) {
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tgl_mulai = tgl_mulai;
        this.tgl_berakhir = tgl_berakhir;
        this.lat = lat;
        this.lng = lng;
        this.calon_penyewa_id = calon_penyewa_id;
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

    public void setCalon_penyewa_id(int calon_penyewa_id) {
        this.calon_penyewa_id = calon_penyewa_id;
    }

    public int getCalon_penyewa_id() {
        return calon_penyewa_id;
    }
}
