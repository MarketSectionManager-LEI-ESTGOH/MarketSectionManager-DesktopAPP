package Controller;

import Model.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDFExporter extends Component {

    public static boolean genPdf(String tableName, String path){
        try {
            Date current = new Date();
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String ts = isoFormat.format(current);
            String file = tableName+"_"+ts;
            String filename = path+"/"+file+".pdf";
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filename));

            SimpleDateFormat formatTitle = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ts = formatTitle.format(current);


            document.open();
            Image imgSoc = Image.getInstance("src/Images/MSM_main_logo_black.png");
            imgSoc.scaleToFit(200,200);
            imgSoc.setAbsolutePosition(350, 720);
            document.add(imgSoc);

            switch (tableName){
                case "utilizadores":
                    title(document, "Tabela Utilizadores", ts);
                    ObservableList<User> t = ConnectDB.getAllUsers();
                    PdfPTable table = new PdfPTable(4);
                    PdfPCell c1 = new PdfPCell(new Phrase("Nome"));
                    table.addCell(c1);
                    PdfPCell c2 = new PdfPCell(new Phrase("Email"));
                    table.addCell(c2);
                    PdfPCell c3 = new PdfPCell(new Phrase("Num. Interno"));
                    table.addCell(c3);
                    PdfPCell c4 = new PdfPCell(new Phrase("Tipo"));
                    table.addCell(c4);
                    table.setHeaderRows(1);
                    t.forEach((User) -> {
                        table.addCell(User.getUsername());
                        table.addCell(User.getEmail());
                        table.addCell(Integer.toString(User.getUserID()));
                        table.addCell(User.getUserTypeConv());
                    });
                    document.add(table);
                    break;
                case "arcasFrig":
                    title(document, "Tabela Arcas Frigoríficas", ts);
                    ObservableList<ArcaFrigorifica> a = ConnectDB.getAllArcas();
                    PdfPTable tableA = new PdfPTable(9);
                    PdfPCell c1a = new PdfPCell(new Phrase("Número"));
                    tableA.addCell(c1a);
                    PdfPCell c2a = new PdfPCell(new Phrase("Designação"));
                    tableA.addCell(c2a);
                    PdfPCell c3a = new PdfPCell(new Phrase("Fabricante"));
                    tableA.addCell(c3a);
                    PdfPCell c4a = new PdfPCell(new Phrase("Data de Adição"));
                    tableA.addCell(c4a);
                    PdfPCell c5a = new PdfPCell(new Phrase("Temp. Min."));
                    tableA.addCell(c5a);
                    PdfPCell c6a = new PdfPCell(new Phrase("Temp. Máx."));
                    tableA.addCell(c6a);
                    PdfPCell c7a = new PdfPCell(new Phrase("Data Limp."));
                    tableA.addCell(c7a);
                    PdfPCell c8a = new PdfPCell(new Phrase("Util. Limp."));
                    tableA.addCell(c8a);
                    PdfPCell c9a = new PdfPCell(new Phrase("Nome Util."));
                    tableA.addCell(c9a);
                    tableA.setHeaderRows(1);
                    a.forEach((Arca) -> {
                        tableA.addCell(Integer.toString(Arca.getNumero()));
                        tableA.addCell(Arca.getDesignacao());
                        tableA.addCell(Arca.getFabricante());
                        tableA.addCell(Arca.getAddDate().toString());
                        tableA.addCell(Float.toString(Arca.getTempMin()));
                        tableA.addCell(Float.toString(Arca.getTempMax()));
                        if(Arca.getLimpDate() != null){
                            tableA.addCell(Arca.getLimpDate().toString());
                        }else{
                            tableA.addCell(" ");
                        }
                        if(Arca.getUserLimp() != null){
                            tableA.addCell(Arca.getUserLimp().toString());
                        }else{
                            tableA.addCell(" ");
                        }
                        tableA.addCell(Arca.getUserName());
                    });
                    document.add(tableA);
                    break;
                case "areasCont":
                    title(document, "Tabela Áreas Controladas", ts);
                    ObservableList<Area> b = ConnectDB.getAllAreasCont();
                    PdfPTable tableb = new PdfPTable(2);
                    PdfPCell c1b = new PdfPCell(new Phrase("Número"));
                    tableb.addCell(c1b);
                    PdfPCell c2b = new PdfPCell(new Phrase("Designação"));
                    tableb.addCell(c2b);
                    tableb.setHeaderRows(1);
                    b.forEach((Area) -> {
                        tableb.addCell(Integer.toString(Area.getNumero()));
                        tableb.addCell(Area.getDesignacao());
                    });
                    document.add(tableb);
                    break;
                case "fornecedores":
                    title(document, "Tabela Fornecedores", ts);
                    ObservableList<Fornecedor> c = ConnectDB.getAllFornecedores();
                    PdfPTable tablec = new PdfPTable(5);
                    PdfPCell c1c = new PdfPCell(new Phrase("Identificador"));
                    tablec.addCell(c1c);
                    PdfPCell c2c = new PdfPCell(new Phrase("Nome"));
                    tablec.addCell(c2c);
                    PdfPCell c3c = new PdfPCell(new Phrase("Contacto"));
                    tablec.addCell(c3c);
                    PdfPCell c4c = new PdfPCell(new Phrase("Email"));
                    tablec.addCell(c4c);
                    PdfPCell c5c = new PdfPCell(new Phrase("Morada"));
                    tablec.addCell(c5c);
                    tablec.setHeaderRows(1);
                    c.forEach((Forn) -> {
                        tablec.addCell(Forn.getIdentificador());
                        tablec.addCell(Forn.getNome());
                        tablec.addCell(Integer.toString(Forn.getContacto()));
                        tablec.addCell(Forn.getEmail());
                        tablec.addCell(Forn.getMorada());
                    });
                    document.add(tablec);
                    break;
                case "produtos":
                    title(document, "Tabela Produtos", ts);
                    ObservableList<Product> d = ConnectDB.getAllProducts();
                    PdfPTable tabled = new PdfPTable(6);
                    PdfPCell c1d = new PdfPCell(new Phrase("Num. Interno"));
                    tabled.addCell(c1d);
                    PdfPCell c2d = new PdfPCell(new Phrase("Nome"));
                    tabled.addCell(c2d);
                    PdfPCell c3d = new PdfPCell(new Phrase("Fresco"));
                    tabled.addCell(c3d);
                    PdfPCell c4d = new PdfPCell(new Phrase("Preço"));
                    tabled.addCell(c4d);
                    PdfPCell c5d = new PdfPCell(new Phrase("EAN"));
                    tabled.addCell(c5d);
                    PdfPCell c6d = new PdfPCell(new Phrase("Marca"));
                    tabled.addCell(c6d);
                    tabled.setHeaderRows(1);
                    d.forEach((Prod) -> {
                        tabled.addCell(Integer.toString(Prod.getNum_int()));
                        tabled.addCell(Prod.getName());
                        tabled.addCell(Prod.getFreshString());
                        tabled.addCell(Float.toString(Prod.getPrice()));
                        tabled.addCell(Prod.getEan());
                        tabled.addCell(Prod.getBrand());
                    });
                    document.add(tabled);
                    break;
                case "limpezas":
                    title(document, "Tabela Limpezas", ts);
                    ObservableList<Limpeza> e = ConnectDB.getAllLimp();
                    PdfPTable tablee = new PdfPTable(6);
                    PdfPCell c1e = new PdfPCell(new Phrase("Área"));
                    tablee.addCell(c1e);
                    PdfPCell c2e = new PdfPCell(new Phrase("Componente"));
                    tablee.addCell(c2e);
                    PdfPCell c3e = new PdfPCell(new Phrase("Data. Limp."));
                    tablee.addCell(c3e);
                    PdfPCell c4e = new PdfPCell(new Phrase("Efetuado por"));
                    tablee.addCell(c4e);
                    PdfPCell c5e = new PdfPCell(new Phrase("Num. Interno"));
                    tablee.addCell(c5e);
                    PdfPCell c6e = new PdfPCell(new Phrase("Validado"));
                    tablee.addCell(c6e);
                    tablee.setHeaderRows(1);
                    e.forEach((Limp) -> {
                        tablee.addCell(Limp.getAreaName());
                        tablee.addCell(Limp.getCompName());
                        tablee.addCell(Limp.getDataLimp().toString());
                        tablee.addCell(Limp.getUserName());
                        tablee.addCell(Integer.toString(Limp.getUserNumInterno()));
                        if(Limp.getValidate().isSelected()){
                            tablee.addCell("Sim");
                        }else{
                            tablee.addCell("Não");
                        }
                    });
                    document.add(tablee);
                    break;
                case "rastreabilidade":
                    title(document, "Tabela Rastreabilidade", ts);
                    ObservableList<Rastreabilidade> f = ConnectDB.getAllRast();
                    PdfPTable tablef = new PdfPTable(7);
                    PdfPCell c1f = new PdfPCell(new Phrase("Lote"));
                    tablef.addCell(c1f);
                    PdfPCell c2f = new PdfPCell(new Phrase("Data Entrada"));
                    tablef.addCell(c2f);
                    PdfPCell c3f = new PdfPCell(new Phrase("Origem"));
                    tablef.addCell(c3f);
                    PdfPCell c4f = new PdfPCell(new Phrase("Produto"));
                    tablef.addCell(c4f);
                    PdfPCell c5f = new PdfPCell(new Phrase("Inserido por"));
                    tablef.addCell(c5f);
                    PdfPCell c6f = new PdfPCell(new Phrase("Fornecedor"));
                    tablef.addCell(c6f);
                    PdfPCell c7f = new PdfPCell(new Phrase("Validado"));
                    tablef.addCell(c7f);
                    tablef.setHeaderRows(1);
                    f.forEach((Rast) -> {
                        tablef.addCell(Integer.toString(Rast.getLote()));
                        tablef.addCell(Rast.getDataEntrada().toString());
                        if(Rast.getOrigem() != null){
                            tablef.addCell(Rast.getOrigem());
                        }else{
                            tablef.addCell(" ");
                        }
                        tablef.addCell(Rast.getProduto());
                        tablef.addCell(Rast.getUtilizador());
                        tablef.addCell(Rast.getFornecedor());
                        if(Rast.getValidate().isSelected()){
                            tablef.addCell("Sim");
                        }else{
                            tablef.addCell("Não");
                        }
                    });
                    document.add(tablef);
                    break;
                case "temperaturas":
                    title(document, "Tabela Temperaturas", ts);
                    ObservableList<Temperatura> g = ConnectDB.getAllTemp();
                    PdfPTable tableg = new PdfPTable(5);
                    PdfPCell c1g = new PdfPCell(new Phrase("Área Frig."));
                    tableg.addCell(c1g);
                    PdfPCell c2g = new PdfPCell(new Phrase("Temperatura"));
                    tableg.addCell(c2g);
                    PdfPCell c3g = new PdfPCell(new Phrase("Data / Hora"));
                    tableg.addCell(c3g);
                    PdfPCell c4g = new PdfPCell(new Phrase("Inserido por"));
                    tableg.addCell(c4g);
                    PdfPCell c5g = new PdfPCell(new Phrase("Validado"));
                    tableg.addCell(c5g);
                    tableg.setHeaderRows(1);
                    g.forEach((Temp) -> {
                        tableg.addCell(Temp.getAreaFrig());
                        tableg.addCell(Float.toString(Temp.getTemperatura()));
                        tableg.addCell(Temp.getDataHora().toString());
                        tableg.addCell(Temp.getUtilizador());
                        if(Temp.getValidate().isSelected()){
                            tableg.addCell("Sim");
                        }else{
                            tableg.addCell("Não");
                        }
                    });
                    document.add(tableg);
                    break;
                case "validades":
                    title(document, "Tabela Validades", ts);
                    ObservableList<ExprirationDate> h = ConnectDB.getAllExpirationDates();
                    PdfPTable tableh = new PdfPTable(6);
                    PdfPCell c1h = new PdfPCell(new Phrase("Num. Interno"));
                    tableh.addCell(c1h);
                    PdfPCell c2h = new PdfPCell(new Phrase("EAN"));
                    tableh.addCell(c2h);
                    PdfPCell c3h = new PdfPCell(new Phrase("Nome"));
                    tableh.addCell(c3h);
                    PdfPCell c4h = new PdfPCell(new Phrase("Validade"));
                    tableh.addCell(c4h);
                    PdfPCell c5h = new PdfPCell(new Phrase("Offset"));
                    tableh.addCell(c5h);
                    PdfPCell c6h = new PdfPCell(new Phrase("Markdown"));
                    tableh.addCell(c6h);
                    tableh.setHeaderRows(1);
                    h.forEach((Exp) -> {
                        tableh.addCell(Exp.getNumInterno());
                        tableh.addCell(Exp.getEan());
                        tableh.addCell(Exp.getNome());
                        tableh.addCell(Exp.getExpirationDate().toString());
                        tableh.addCell(Integer.toString(Exp.getOffset()));
                        tableh.addCell(Exp.getMardown());
                    });
                    document.add(tableh);
                    break;
            }

            document.close();
            return true;
        } catch (DocumentException e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "DU", " "+e).showAndWait();
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "DU", " "+e).showAndWait();
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "DU", " "+e).showAndWait();
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            MainScreenController.alerts(Alert.AlertType.ERROR, "DU", " "+e).showAndWait();
            e.printStackTrace();
            return false;
        }
    }

    private static void title(Document aD, String aType, String aTime){
        Paragraph para = new Paragraph("                "+aType);
        try {
            aD.add(para);
            aD.add(new Paragraph(" "));
            aD.add(new Paragraph(" "));
            aD.add(new Paragraph(" "));
            aD.add(new Paragraph(" "));
            aD.add(new Paragraph(" "));
            aD.add(new Paragraph("                Exportado por: "+Main.u.getUsername()+" | "+aTime));
            aD.add(new Paragraph(" "));
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}
