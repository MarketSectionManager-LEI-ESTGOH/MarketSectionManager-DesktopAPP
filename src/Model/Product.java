package Model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

public class Product {

    private int num_int, fresh, venda;
    private String name, brand, freshString, ean;
    private float price;

    public Product(){

    }

    public Product(int aNumInt, String aName, int aFresh, float aPrice, int aVenda, String aEAN, String aBrand){
        num_int = aNumInt;
        name = aName;
        fresh = aFresh;
        price = aPrice;
        venda = aVenda;
        ean = aEAN;
        brand = aBrand;
        if(fresh == 1){
            freshString = "Sim";
            ean = null;
        }else if(fresh == 0){
            freshString = "Não";
        }
    }

    public void setNum_int(int num_int) {
        this.num_int = num_int;
    }

    public void setFresh(int fresh) {
        this.fresh = fresh;
    }

    public void setVenda(int venda) {
        this.venda = venda;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNum_int() {
        return num_int;
    }

    public int getFresh() {
        return fresh;
    }

    public int getVenda() {
        return venda;
    }

    public String getEan() {
        return ean;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public float getPrice() {
        return price;
    }

    public String getFreshString() {
        return freshString;
    }

    public static boolean removeProductFromDB(Product toRemove){
        try {
            String stmt = "DELETE FROM produto WHERE n_interno = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, toRemove.getNum_int());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){

        }
        return false;
    }

    public static boolean checkNumInt(int aNum){
        return ConnectDB.checkProductNumIt(aNum);
    }
}
