package Model;

import java.sql.PreparedStatement;

public class Product {

    private int num_int, fresh, venda;
    private String name, brand, freshString, ean;
    private float price;

    /**
     * Construtor Vazio da Classe
     */
    public Product(){ }

    /**
     * Construtor da Classe
     * @param aNumInt Número Interno do Produto
     * @param aName Nome do Produto
     * @param aFresh Se o Produto é Fresco
     * @param aPrice Preço do Produto
     * @param aVenda --
     * @param aEAN EAN do Produto
     * @param aBrand Marca do Produto
     */
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

    /**
     * Definir Número Interno do Produto
     * @param num_int Int - Número Interno do Produto
     */
    public void setNum_int(int num_int) {
        this.num_int = num_int;
    }

    /**
     * Definir o Estado Fresco do Produto
     * @param fresh Int - Estado do Produto (Fresco ou não)
     */
    public void setFresh(int fresh) {
        this.fresh = fresh;
    }

    /**
     * --
     * @param venda --
     */
    public void setVenda(int venda) {
        this.venda = venda;
    }

    /**
     * Definir EAn do Produto
     * @param ean String - EAN do Produto
     */
    public void setEan(String ean) {
        this.ean = ean;
    }

    /**
     * Definir Nome do Produto
     * @param name String - Nome do Produto
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Definir Marca do Produto
     * @param brand String - Marca do Produto
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Definir Preço do Produto
     * @param price Float - Preço do Produto
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Obter Número Interno do Produto
     * @return Int
     */
    public int getNum_int() {
        return num_int;
    }

    /**
     * Obter Estado Fresco do Produto
     * @return Int
     */
    public int getFresh() {
        return fresh;
    }

    /**
     * --
     * @return --
     */
    public int getVenda() {
        return venda;
    }

    /**
     * Obter EAN do Produto
     * @return String
     */
    public String getEan() {
        return ean;
    }

    /**
     * Obter Nome do Produto
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Obter Marca do Produto
     * @return String
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Obter Preço do Produto
     * @return Float
     */
    public float getPrice() {
        return price;
    }

    /**
     * Obter Estado Fresco do Produto (String)
     * @return String
     */
    public String getFreshString() {
        return freshString;
    }

    /**
     * Remover Produto da Base de Dados
     * @param toRemove Objeto do tipo Product que irá ser removido
     * @return True/False
     */
    public static boolean removeProductFromDB(Product toRemove){
        try {
            String stmt = "DELETE FROM produto WHERE n_interno = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setInt(1, toRemove.getNum_int());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Verifica se o código interno recebido por parâmetro já se encontra em uso na Base de Dados
     * @param aNum Número Interno a verificar
     * @return True / False
     */
    public static boolean checkNumInt(int aNum){
        return ConnectDB.checkProductNumIt(aNum);
    }
}
