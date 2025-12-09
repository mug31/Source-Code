class Mahasiswa {
    private long nim;
    private String nama;
    private String asal;

    public Mahasiswa(long nim, String nama, String asal) {
        this.nim = nim;
        this.nama = nama;
        this.asal = asal;
    }

    public void displayMhs() {
        System.out.println("NIM: " + nim + ", Nama: " + nama + ", Asal: " + asal);
    }

    public long getNim() { return nim; }
    public String getNama() { return nama; }
}
