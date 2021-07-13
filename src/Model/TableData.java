package Model;

public class TableData {
    int data = -1;
    String dataDesignation = "";

    /**
     * Contrutor da Classe
     * @param aDataDesignation Nome dos Dados
     * @param aData Valor dos Dados
     */
    public TableData(String aDataDesignation, int aData){
        dataDesignation = aDataDesignation;
        data = aData;
    }

    /**
     * Obter os dados
     * @return Int
     */
    public int getData() {
        return data;
    }

    /**
     * Obter a Designação dos dados
     * @return
     */
    public String getDataDesignation() {
        return dataDesignation;
    }
}
