/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class ProductDetail {

    private UUID id;
    private Product idProduct;
    private Company idCompany;
    private ColorProduct idColor;
    private ProductType idType;
    private CPUInfo idCPU;
    private GPUInfo idGPU;
    private int ROM;
    private int RAM;
    private String Serial;
    private int NamBH;
    private String MoTa;
    private BigDecimal GiaNhap;
    private BigDecimal GiaBan;
    private boolean TinhTrang;

    public ProductDetail(Product idProduct, Company idCompany, ColorProduct idColor, ProductType idType, CPUInfo idCPU, GPUInfo idGPU, int ROM, int RAM, String Serial, int NamBH, String MoTa, BigDecimal GiaNhap, BigDecimal GiaBan, boolean TinhTrang) {
        this.id = UUID.randomUUID();
        this.idProduct = idProduct;
        this.idCompany = idCompany;
        this.idColor = idColor;
        this.idType = idType;
        this.idCPU = idCPU;
        this.idGPU = idGPU;
        this.ROM = ROM;
        this.RAM = RAM;
        this.Serial = Serial;
        this.NamBH = NamBH;
        this.MoTa = MoTa;
        this.GiaNhap = GiaNhap;
        this.GiaBan = GiaBan;
        this.TinhTrang = TinhTrang;
    }

    public ProductDetail() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public Company getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Company idCompany) {
        this.idCompany = idCompany;
    }

    public ColorProduct getIdColor() {
        return idColor;
    }

    public void setIdColor(ColorProduct idColor) {
        this.idColor = idColor;
    }

    public ProductType getIdType() {
        return idType;
    }

    public void setIdType(ProductType idType) {
        this.idType = idType;
    }

    public CPUInfo getIdCPU() {
        return idCPU;
    }

    public void setIdCPU(CPUInfo idCPU) {
        this.idCPU = idCPU;
    }

    public GPUInfo getIdGPU() {
        return idGPU;
    }

    public void setIdGPU(GPUInfo idGPU) {
        this.idGPU = idGPU;
    }

    public int getROM() {
        return ROM;
    }

    public void setROM(int ROM) {
        this.ROM = ROM;
    }

    public int getRAM() {
        return RAM;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String Serial) {
        this.Serial = Serial;
    }

    public int getNamBH() {
        return NamBH;
    }

    public void setNamBH(int NamBH) {
        this.NamBH = NamBH;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public BigDecimal getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(BigDecimal GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    public BigDecimal getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(BigDecimal GiaBan) {
        this.GiaBan = GiaBan;
    }

    public boolean isTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(boolean TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

}
