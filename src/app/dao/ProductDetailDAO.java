/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.dbconnect.DBConnector;
import app.model.ProductDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.*;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class ProductDetailDAO implements ModelDAO<ProductDetail> {

    public static ProductDetailDAO getInstance() {
        return new ProductDetailDAO();
    }

    @Override
    public int insert(ProductDetail t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO ChiTietSP\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, t.getId());
            stm.setObject(2, t.getIdProduct().getId());
            stm.setObject(3, t.getIdCompany().getId());
            stm.setObject(4, t.getIdColor().getId());
            stm.setObject(5, t.getIdType().getId());
            stm.setObject(6, t.getIdCPU().getId());
            stm.setObject(7, t.getIdGPU().getId());
            stm.setInt(8, t.getROM());
            stm.setInt(9, t.getRAM());
            stm.setString(10, t.getSerial());
            stm.setInt(11, t.getNamBH());
            stm.setString(12, t.getMoTa());
            stm.setBigDecimal(13, t.getGiaNhap());
            stm.setBigDecimal(14, t.getGiaBan());
            stm.setBoolean(15, t.isTinhTrang());

            int res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int update(ProductDetail t) {
        try {
            Connection conn = new DBConnector().getConnection();
            String sql = "UPDATE ChiTietSP\n"
                    + "SET IdSP = ?, "
                    + "IdNSX = ?, "
                    + "IdMauSac = ?, "
                    + "IdDongSP = ?, "
                    + "IdCPU = ?, "
                    + "IdCardVga = ?, "
                    + "ROM = ?, "
                    + "RAM = ?, "
                    + "Serial = ?, "
                    + "NamBH = ?, "
                    + "MoTa = ?, "
                    + "GiaNhap = ?, "
                    + "GiaBan = ?, "
                    + "TinhTrang = ? \n"
                    + "WHERE Id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, t.getIdProduct().getId());
            stm.setObject(2, t.getIdCompany().getId());
            stm.setObject(3, t.getIdColor().getId());
            stm.setObject(4, t.getIdType().getId());
            stm.setObject(5, t.getIdCPU().getId());
            stm.setObject(6, t.getIdGPU().getId());
            stm.setInt(7, t.getROM());
            stm.setInt(8, t.getRAM());
            stm.setString(9, t.getSerial());
            stm.setInt(10, t.getNamBH());
            stm.setString(11, t.getMoTa());
            stm.setBigDecimal(12, t.getGiaNhap());
            stm.setBigDecimal(13, t.getGiaBan());
            stm.setBoolean(14, t.isTinhTrang());
            stm.setObject(15, t.getId());

            int res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public ArrayList<ProductDetail> getAll() {
        ArrayList<ProductDetail> listProductDetails = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select * from ChiTietSP";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setId(rs.getObject(1, UUID.class));
                productDetail.setIdProduct(ProductDAO.getInstance().selectByUUID(rs.getObject(2, UUID.class)));
                productDetail.setIdCompany(CompanyDAO.getInstance().selectByUUID(rs.getObject(3, UUID.class)));
                productDetail.setIdColor(ColorDAO.getInstance().selectByUUID(rs.getObject(4, UUID.class)));
                productDetail.setIdType(ProductTypeDAO.getInstance().selectByUUID(rs.getObject(5, UUID.class)));
                productDetail.setIdCPU(CPUInforDAO.getInstance().selectByUUID(rs.getObject(6, UUID.class)));
                productDetail.setIdGPU(GPUInforDAO.getInstance().selectByUUID(rs.getObject(7, UUID.class)));
                productDetail.setROM(rs.getInt(8));
                productDetail.setRAM(rs.getInt(9));
                productDetail.setSerial(rs.getString(10));
                productDetail.setNamBH(rs.getInt(11));
                productDetail.setMoTa(rs.getString(12));
                productDetail.setGiaNhap(rs.getBigDecimal(13));
                productDetail.setGiaBan(rs.getBigDecimal(14));
                productDetail.setTinhTrang(rs.getBoolean(15));
                listProductDetails.add(productDetail);
            }
            conn.close();
            return listProductDetails;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<ProductDetail> selectByPrice(BigDecimal priceMin, BigDecimal priceMax) {
        ArrayList<ProductDetail> listProductDetails = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select * from ChiTietSP\n"
                    + "WHERE GiaBan >= ? AND GiaBan <= ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setBigDecimal(1, priceMin);
            stm.setBigDecimal(2, priceMax);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setId(rs.getObject(1, UUID.class));
                productDetail.setIdProduct(ProductDAO.getInstance().selectByUUID(rs.getObject(2, UUID.class)));
                productDetail.setIdCompany(CompanyDAO.getInstance().selectByUUID(rs.getObject(3, UUID.class)));
                productDetail.setIdColor(ColorDAO.getInstance().selectByUUID(rs.getObject(4, UUID.class)));
                productDetail.setIdType(ProductTypeDAO.getInstance().selectByUUID(rs.getObject(5, UUID.class)));
                productDetail.setIdCPU(CPUInforDAO.getInstance().selectByUUID(rs.getObject(6, UUID.class)));
                productDetail.setIdGPU(GPUInforDAO.getInstance().selectByUUID(rs.getObject(7, UUID.class)));
                productDetail.setROM(rs.getInt(8));
                productDetail.setRAM(rs.getInt(9));
                productDetail.setSerial(rs.getString(10));
                productDetail.setNamBH(rs.getInt(11));
                productDetail.setMoTa(rs.getString(12));
                productDetail.setGiaBan(rs.getBigDecimal(13));
                productDetail.setGiaNhap(rs.getBigDecimal(14));
                productDetail.setTinhTrang(rs.getBoolean(15));
                listProductDetails.add(productDetail);
            }
            conn.close();
            return listProductDetails;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<String> getNameProduct() {
        ArrayList<String> listNameProduct = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select Ten FROM SanPham";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listNameProduct.add(rs.getString(1));
            }
            conn.close();
            return listNameProduct;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<String> getListSerial(String nameProduct) {
        ArrayList<String> listSerial = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "select Serial from ChiTietSP \n"
                    + "join SanPham on SanPham.Id = ChiTietSP.IdSP\n"
                    + "where Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nameProduct);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listSerial.add(rs.getString(1));
            }
            conn.close();
            return listSerial;
        } catch (SQLException e) {
            return null;
        }
    }

    public ProductDetail getProductDetailBySerial(String serial) {
        ProductDetail productDetail = new ProductDetail();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "select * from ChiTietSP\n"
                    + "where Serial = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, serial);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                productDetail.setId(rs.getObject(1, UUID.class));
                productDetail.setIdProduct(ProductDAO.getInstance().selectByUUID(rs.getObject(2, UUID.class)));
                productDetail.setIdCompany(CompanyDAO.getInstance().selectByUUID(rs.getObject(3, UUID.class)));
                productDetail.setIdColor(ColorDAO.getInstance().selectByUUID(rs.getObject(4, UUID.class)));
                productDetail.setIdType(ProductTypeDAO.getInstance().selectByUUID(rs.getObject(5, UUID.class)));
                productDetail.setIdCPU(CPUInforDAO.getInstance().selectByUUID(rs.getObject(6, UUID.class)));
                productDetail.setIdGPU(GPUInforDAO.getInstance().selectByUUID(rs.getObject(7, UUID.class)));
                productDetail.setROM(rs.getInt(8));
                productDetail.setRAM(rs.getInt(9));
                productDetail.setSerial(rs.getString(10));
                productDetail.setNamBH(rs.getInt(11));
                productDetail.setMoTa(rs.getString(12));
                productDetail.setGiaNhap(rs.getBigDecimal(13));
                productDetail.setGiaBan(rs.getBigDecimal(14));
                productDetail.setTinhTrang(rs.getBoolean(15));
            }
            conn.close();
            return productDetail;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updateStatusProduct(String serial) {
        try {
            ProductDetail productDetail = getProductDetailBySerial(serial);
            boolean status = productDetail.isTinhTrang();
            System.out.println(status);
            Connection conn = DBConnector.getConnection();
            String sql = "UPDATE ChiTietSP\n"
                    + "SET TinhTrang = ?\n"
                    + "WHERE Serial = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            if (status) {
                stm.setBoolean(1, false);
            } else {
                stm.setBoolean(1, true);
            }
            stm.setString(2, serial);
            stm.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<ProductDetail> getListByName(String nameProduct) {
        ArrayList<ProductDetail> listProductDetails = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "select * from ChiTietSP join SanPham on ChiTietSP.IdSP = SanPham.Id where Ten like ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + nameProduct + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setId(rs.getObject(1, UUID.class));
                productDetail.setIdProduct(ProductDAO.getInstance().selectByUUID(rs.getObject(2, UUID.class)));
                productDetail.setIdCompany(CompanyDAO.getInstance().selectByUUID(rs.getObject(3, UUID.class)));
                productDetail.setIdColor(ColorDAO.getInstance().selectByUUID(rs.getObject(4, UUID.class)));
                productDetail.setIdType(ProductTypeDAO.getInstance().selectByUUID(rs.getObject(5, UUID.class)));
                productDetail.setIdCPU(CPUInforDAO.getInstance().selectByUUID(rs.getObject(6, UUID.class)));
                productDetail.setIdGPU(GPUInforDAO.getInstance().selectByUUID(rs.getObject(7, UUID.class)));
                productDetail.setROM(rs.getInt(8));
                productDetail.setRAM(rs.getInt(9));
                productDetail.setSerial(rs.getString(10));
                productDetail.setNamBH(rs.getInt(11));
                productDetail.setMoTa(rs.getString(12));
                productDetail.setGiaNhap(rs.getBigDecimal(13));
                productDetail.setGiaBan(rs.getBigDecimal(14));
                productDetail.setTinhTrang(rs.getBoolean(15));
                listProductDetails.add(productDetail);
            }
            conn.close();
            return listProductDetails;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ArrayList<ProductDetail> getListByCode(String codeProduct) {
        ArrayList<ProductDetail> listProductDetails = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = """
                         SELECT [ChiTietSP].[Id]
                               ,[IdSP]
                               ,[IdNsx]
                               ,[IdMauSac]
                               ,[IdDongSP]
                               ,[idCPU]
                               ,[idCardVga]
                               ,[ROM]
                               ,[RAM]
                               ,[Serial]
                               ,[NamBH]
                               ,[MoTa]
                               ,[GiaNhap]
                               ,[GiaBan]
                               ,[TinhTrang]
                           FROM [dbo].[ChiTietSP]
                         	JOIN SanPham ON ChiTietSP.IdSP = SanPham.Id
                         	WHERE Ma = ?
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, codeProduct);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setId(rs.getObject(1, UUID.class));
                productDetail.setIdProduct(ProductDAO.getInstance().selectByUUID(rs.getObject(2, UUID.class)));
                productDetail.setIdCompany(CompanyDAO.getInstance().selectByUUID(rs.getObject(3, UUID.class)));
                productDetail.setIdColor(ColorDAO.getInstance().selectByUUID(rs.getObject(4, UUID.class)));
                productDetail.setIdType(ProductTypeDAO.getInstance().selectByUUID(rs.getObject(5, UUID.class)));
                productDetail.setIdCPU(CPUInforDAO.getInstance().selectByUUID(rs.getObject(6, UUID.class)));
                productDetail.setIdGPU(GPUInforDAO.getInstance().selectByUUID(rs.getObject(7, UUID.class)));
                productDetail.setROM(rs.getInt(8));
                productDetail.setRAM(rs.getInt(9));
                productDetail.setSerial(rs.getString(10));
                productDetail.setNamBH(rs.getInt(11));
                productDetail.setMoTa(rs.getString(12));
                productDetail.setGiaNhap(rs.getBigDecimal(13));
                productDetail.setGiaBan(rs.getBigDecimal(14));
                productDetail.setTinhTrang(rs.getBoolean(15));
                listProductDetails.add(productDetail);
            }
            conn.close();
            return listProductDetails;
        } catch (SQLException e) {
            return null;
        }
    }

    public int getQuantityProductById(String code) {
        int productQuantity = 0;
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         select count(ChiTietSP.Id) from ChiTietSP
                         join SanPham on ChiTietSP.IdSP = SanPham.Id
                         where SanPham.Ma = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productQuantity = resultSet.getInt(1);
            }
            return productQuantity;
        } catch (SQLException e) {
            return 0;
        }
    }
    
    public ProductDetail selectByUUID(UUID id) {
        ProductDetail productDetail = new ProductDetail();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = """
                        SELECT [Id]
                               ,[IdSP]
                               ,[IdNsx]
                               ,[IdMauSac]
                               ,[IdDongSP]
                               ,[idCPU]
                               ,[idCardVga]
                               ,[ROM]
                               ,[RAM]
                               ,[Serial]
                               ,[NamBH]
                               ,[MoTa]
                               ,[GiaNhap]
                               ,[GiaBan]
                               ,[TinhTrang]
                           FROM [dbo].[ChiTietSP]
                         WHERE [Id] = ?
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                productDetail.setId(resultSet.getObject(1, UUID.class));
                productDetail.setIdProduct(ProductDAO.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                productDetail.setIdCompany(CompanyDAO.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                productDetail.setIdColor(ColorDAO.getInstance().selectByUUID(resultSet.getObject(4, UUID.class)));
                productDetail.setIdType(ProductTypeDAO.getInstance().selectByUUID(resultSet.getObject(5, UUID.class)));
                productDetail.setIdCPU(CPUInforDAO.getInstance().selectByUUID(resultSet.getObject(6, UUID.class)));
                productDetail.setIdGPU(GPUInforDAO.getInstance().selectByUUID(resultSet.getObject(7, UUID.class)));
                productDetail.setROM(resultSet.getInt(8));
                productDetail.setRAM(resultSet.getInt(9));
                productDetail.setSerial(resultSet.getString(10));
                productDetail.setNamBH(resultSet.getInt(11));
                productDetail.setMoTa(resultSet.getString(12));
                productDetail.setGiaNhap(resultSet.getBigDecimal(13));
                productDetail.setGiaBan(resultSet.getBigDecimal(14));
                productDetail.setTinhTrang(resultSet.getBoolean(15));
            }
            conn.close();
            return productDetail;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ProductDetail selectById(String name) {
       throw new UnsupportedOperationException("Not support");
    }

    @Override
    public String generateNextModelCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
