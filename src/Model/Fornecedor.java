package Model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Fornecedor {

    private String identificador, nome, email, morada;
    private int contacto;

    public Fornecedor(String aIdentficador, String aNome, int aContacto, String aEmail, String aMorada){
        identificador = aIdentficador;
        nome = aNome;
        email = aEmail;
        morada = aMorada;
        contacto = aContacto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    public static boolean removeFornFromDB(Fornecedor toRemove){
        try {
            String stmt = "DELETE FROM fornecedor WHERE identificador = ?";
            PreparedStatement ps = ConnectDB.getConn().prepareStatement(stmt);
            ps.setString(1, toRemove.getIdentificador());
            return ConnectDB.removeFromDB(ps);
        }catch (Exception e){

        }
        return false;
    }

    /**
     * Regista um novo fornecedor.
     * @param aIdentificador
     * @param aName
     * @param aContacto
     * @param aEmail
     * @param aMorada
     * @return
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
     * @param aID
     * @return
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
