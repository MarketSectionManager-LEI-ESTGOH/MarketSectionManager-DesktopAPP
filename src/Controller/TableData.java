package Controller;

public class TableData {
    int data = -1;
    String dataDesignation = "";

    public TableData(String aDataDesignation, int aData){
        dataDesignation = aDataDesignation;
        data = aData;
    }

    public int getData() {
        return data;
    }

    public String getDataDesignation() {
        return dataDesignation;
    }
}
