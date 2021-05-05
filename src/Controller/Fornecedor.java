package Controller;

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

}
