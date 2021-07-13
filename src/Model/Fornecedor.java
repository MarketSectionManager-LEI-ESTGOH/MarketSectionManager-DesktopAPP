package Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Fornecedor {

    private String identificador, nome, email, morada;
    private int contacto;

    /**
     * Construtor da Classe
     * @param aIdentficador Identificador do Fornecedor
     * @param aNome Nome do Fornecedor
     * @param aContacto Contacto Telefónico do Fornecedor
     * @param aEmail Email do Fornecedor
     * @param aMorada Morada do Fornecedor
     */
    public Fornecedor(String aIdentficador, String aNome, int aContacto, String aEmail, String aMorada){
        identificador = aIdentficador;
        nome = aNome;
        email = aEmail;
        morada = aMorada;
        contacto = aContacto;
    }

    /**
     * Obter Identificador do Fornecedor
     * @return String
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Definir Identificador do Fornecedor
     * @param identificador String - Identificador do Fornecedor
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * Obter Nome do Fornecedor
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * Definir Nome do Fornecedor
     * @param nome String - Nome do Fornecedor
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obter Email do Fornecedor
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Definir Email do Fornecedor
     * @param email String - Email do Fornecedor
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obter Morada do Fornecedor
     * @return String
     */
    public String getMorada() {
        return morada;
    }

    /**
     * Definir Morada do Fornecedor
     * @param morada String - Morada do Fornecedor
     */
    public void setMorada(String morada) {
        this.morada = morada;
    }

    /**
     * Obter Contacto do Fornecedor
     * @return Int
     */
    public int getContacto() {
        return contacto;
    }

    /**
     * Definir contacto do Fornecedor
     * @param contacto Int - Contacto do Fornecedor
     */
    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    /**
     * Remover Fornecedor da Base de Dados
     * @param toRemove Objeto do tipo Fornecedor que irá ser removido
     * @return True/False
     */
    public static boolean removeFornFromDB(Fornecedor toRemove){
        try {
            String stmt = "DELETE FROM fornecedor WHERE identificador = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1, toRemove.getIdentificador());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Regista um novo fornecedor.
     * @param aIdentificador Identificador do Fornecedor
     * @param aName Nome do Fornecedor
     * @param aContacto Contacto Telefónico do Fornecedor
     * @param aEmail Email do Fornecedor
     * @param aMorada Morada do Fornecedor
     * @return True/False
     */
    public static boolean addFornecedor(String aIdentificador, String aName, int aContacto, String aEmail, String aMorada){
        try {
            String stmt = "INSERT INTO fornecedor (identificador, nome, contacto, email, morada) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1, aIdentificador);
            ps.setString(2, aName);
            ps.setInt(3, aContacto);
            ps.setString(4, aEmail);
            ps.setString(5, aMorada);
            return ConnectDB.insertIntoTable(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /**
     *Check se identificador existe na DB
     * true = exists, false = is free
     * @param aID Id do Fornecedor
     * @return True/False
     */
    public static boolean checkIdentificador(String aID){
        try {
            String stmt = "SELECT identificador FROM fornecedor WHERE identificador = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1, aID);
            return ConnectDB.checkString(ps, aID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

}
