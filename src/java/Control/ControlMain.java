package Control;

import java.nio.file.Path;
import java.util.Date;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import org.primefaces.model.DefaultStreamedContent;
import java.nio.channels.Channels;
import java.io.RandomAccessFile;
import org.primefaces.model.StreamedContent;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import javax.faces.context.ExternalContext;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import javax.faces.context.FacesContext;
import DAO.SysDao;
import java.util.ArrayList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Criterion;
import Modal.DaSizingEstimado;
import Modal.DaSizingQuestionario;
import java.util.List;

public class ControlMain
{
    private List<DaSizingQuestionario> _sizing_questionarios;
    private List<DaSizingEstimado> _sizing_estimados;
    private DaSizingQuestionario _sizing_questionario;
    private DaSizingEstimado _sizing_estimado;
    private List<Criterion> _argumentos;
    private Order _order;
    private String _erro;
    
    public ControlMain() {
        this._erro = "";
        this._argumentos = new ArrayList<Criterion>();
        this._sizing_questionarios = new ArrayList<DaSizingQuestionario>();
    }
    
    public List<DaSizingQuestionario> retornaQuestionario() {
        this._order = Order.asc("sequencia");
        return this._sizing_questionarios = (List<DaSizingQuestionario>)new SysDao().listagem(DaSizingQuestionario.class, this._argumentos, this._order, 0);
    }
    
    public String atualizaDadosSizing(final List<DaSizingQuestionario> p_list_questionario) {
        for (int i = 0; i < p_list_questionario.size(); ++i) {
            final DaSizingQuestionario questionario = p_list_questionario.get(i);
            this._erro = new SysDao().update(questionario);
            if (!this._erro.isEmpty()) {
                this._erro = this._erro + "\n\r" + this._erro;
            }
        }
        return this._erro;
    }
    
    public List<DaSizingEstimado> retornaSizingEstimado() {
        return this._sizing_estimados = (List<DaSizingEstimado>)new SysDao().listagem(DaSizingEstimado.class, this._argumentos, this._order, 0);
    }
    
    public String calculaSizing() {
        return this._erro = new SysDao().executeObject("pkg_da_sizing.prc_estima_sizing");
    }
    
    public String criaPlanilhaSizing(final List<DaSizingEstimado> p_sizing_estimado, final String p_nome_cliente) throws IOException {
        final ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet firstSheet = workbook.createSheet("Sizing");
        FileOutputStream fileOutputStream = null;
        try {
            HSSFRow row1 = firstSheet.createRow(3);
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor((short)52);
            //cellStyle.setFillPattern((short)1);
            //cellStyle.setAlignment((short)2);
            Font font = (Font)workbook.createFont();
            font.setFontHeightInPoints((short)11);
            font.setFontName("Calibri");
            font.setColor((short)9);
            font.setBold(true);
            cellStyle.setFont(font);
            final HSSFCell cell1 = row1.createCell(3);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue("USUARIO");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell2 = row1.createCell(4);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue("KB_INICIAL");
            firstSheet.autoSizeColumn(4);
            final HSSFCell cell3 = row1.createCell(5);
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue("KB_MENSAL");
            firstSheet.autoSizeColumn(5);
            final HSSFCell cell4 = row1.createCell(6);
            cell4.setCellStyle(cellStyle);
            cell4.setCellValue("KB_ANUAL");
            firstSheet.autoSizeColumn(6);
            final HSSFCell cell5 = row1.createCell(7);
            cell5.setCellStyle(cellStyle);
            cell5.setCellValue("MB_INICIAL");
            firstSheet.autoSizeColumn(7);
            final HSSFCell cell6 = row1.createCell(8);
            cell6.setCellStyle(cellStyle);
            cell6.setCellValue("MB_MENSAL");
            firstSheet.autoSizeColumn(8);
            final HSSFCell cell7 = row1.createCell(9);
            cell7.setCellStyle(cellStyle);
            cell7.setCellValue("MB_ANUAL");
            firstSheet.autoSizeColumn(9);
            final HSSFCell cell8 = row1.createCell(10);
            cell8.setCellStyle(cellStyle);
            cell8.setCellValue("GB_INICIAL");
            firstSheet.autoSizeColumn(10);
            final HSSFCell cell9 = row1.createCell(11);
            cell9.setCellStyle(cellStyle);
            cell9.setCellValue("GB_MENSAL");
            firstSheet.autoSizeColumn(11);
            final HSSFCell cell10 = row1.createCell(12);
            cell10.setCellStyle(cellStyle);
            cell10.setCellValue("GB_ANUAL");
            firstSheet.autoSizeColumn(12);
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor((short)9);
            //cellStyle.setFillPattern((short)1);
            //cellStyle.setAlignment((short)2);
            cellStyle.setBottomBorderColor((short)52);
            //cellStyle.setBorderBottom((short)1);
            //cellStyle.setBorderTop((short)1);
            cellStyle.setTopBorderColor((short)52);
            font = (Font)workbook.createFont();
            font.setFontHeightInPoints((short)11);
            font.setFontName("Calibri");
            font.setColor((short)8);
            cellStyle.setFont(font);
            int i = 4;
            for (final DaSizingEstimado sizing : p_sizing_estimado) {
                row1 = firstSheet.createRow(i);
                final HSSFCell cell_resp_3 = row1.createCell(3);
                cell_resp_3.setCellType(0);
                cell_resp_3.setCellStyle(cellStyle);
                cell_resp_3.setCellValue(sizing.getUsuario());
                firstSheet.autoSizeColumn(3);
                final HSSFCell cell_resp_4 = row1.createCell(4);
                cell_resp_4.setCellType(0);
                cell_resp_4.setCellStyle(cellStyle);
                cell_resp_4.setCellValue(Double.parseDouble(String.valueOf(sizing.getKbInicial())));
                firstSheet.autoSizeColumn(4);
                final HSSFCell cell_resp_5 = row1.createCell(5);
                cell_resp_5.setCellType(0);
                cell_resp_5.setCellStyle(cellStyle);
                cell_resp_5.setCellValue(Double.parseDouble(String.valueOf(sizing.getKbMensal())));
                firstSheet.autoSizeColumn(5);
                final HSSFCell cell_resp_6 = row1.createCell(6);
                cell_resp_6.setCellType(0);
                cell_resp_6.setCellStyle(cellStyle);
                cell_resp_6.setCellValue(Double.parseDouble(String.valueOf(sizing.getKbAnual())));
                firstSheet.autoSizeColumn(6);
                final HSSFCell cell_resp_7 = row1.createCell(7);
                cell_resp_7.setCellType(0);
                cell_resp_7.setCellStyle(cellStyle);
                cell_resp_7.setCellValue(Double.parseDouble(String.valueOf(sizing.getMbInicial())));
                firstSheet.autoSizeColumn(7);
                final HSSFCell cell_resp_8 = row1.createCell(8);
                cell_resp_8.setCellType(0);
                cell_resp_8.setCellStyle(cellStyle);
                cell_resp_8.setCellValue(Double.parseDouble(String.valueOf(sizing.getMbMensal())));
                firstSheet.autoSizeColumn(8);
                final HSSFCell cell_resp_9 = row1.createCell(9);
                cell_resp_9.setCellType(0);
                cell_resp_9.setCellStyle(cellStyle);
                cell_resp_9.setCellValue(Double.parseDouble(String.valueOf(sizing.getMbAnual())));
                firstSheet.autoSizeColumn(9);
                final HSSFCell cell_resp_10 = row1.createCell(10);
                cell_resp_10.setCellType(0);
                cell_resp_10.setCellStyle(cellStyle);
                cell_resp_10.setCellValue(Double.parseDouble(String.valueOf(sizing.getGbInicial())));
                firstSheet.autoSizeColumn(10);
                final HSSFCell cell_resp_11 = row1.createCell(11);
                cell_resp_11.setCellType(0);
                cell_resp_11.setCellStyle(cellStyle);
                cell_resp_11.setCellValue(Double.parseDouble(String.valueOf(sizing.getGbMensal())));
                firstSheet.autoSizeColumn(11);
                final HSSFCell cell_resp_12 = row1.createCell(12);
                cell_resp_12.setCellType(0);
                cell_resp_12.setCellStyle(cellStyle);
                cell_resp_12.setCellValue(Double.parseDouble(String.valueOf(sizing.getGbAnual())));
                firstSheet.autoSizeColumn(12);
                ++i;
            }
            final HSSFCellStyle cellStyle2 = workbook.createCellStyle();
            cellStyle2.setFillForegroundColor((short)9);
            //cellStyle2.setFillPattern((short)1);
            //cellStyle2.setAlignment((short)2);
            cellStyle2.setBottomBorderColor((short)52);
            //cellStyle2.setBorderBottom((short)1);
            //cellStyle2.setBorderTop((short)1);
            cellStyle2.setTopBorderColor((short)52);
            final Font font2 = (Font)workbook.createFont();
            font2.setFontHeightInPoints((short)11);
            font2.setFontName("Calibri");
            font2.setColor((short)8);
            font2.setBold(true);
            cellStyle2.setFont(font2);
            row1 = firstSheet.createRow(10);
            final HSSFCell cell_total = row1.createCell(3);
            cell_total.setCellStyle(cellStyle2);
            cell_total.setCellValue("TOTAL");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_kb_inicial = row1.createCell(4);
            cell_kb_inicial.setCellStyle(cellStyle2);
            cell_kb_inicial.setCellType(2);
            cell_kb_inicial.setCellFormula("SUM(E5:E10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_kb_mensal = row1.createCell(5);
            cell_kb_mensal.setCellStyle(cellStyle2);
            cell_kb_mensal.setCellType(2);
            cell_kb_mensal.setCellFormula("SUM(F5:F10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_kb_anual = row1.createCell(6);
            cell_kb_anual.setCellStyle(cellStyle2);
            cell_kb_anual.setCellType(2);
            cell_kb_anual.setCellFormula("SUM(G5:G10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_mb_inicial = row1.createCell(7);
            cell_mb_inicial.setCellStyle(cellStyle2);
            cell_mb_inicial.setCellType(2);
            cell_mb_inicial.setCellFormula("SUM(H5:H10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_mb_mensal = row1.createCell(8);
            cell_mb_mensal.setCellStyle(cellStyle2);
            cell_mb_mensal.setCellType(2);
            cell_mb_mensal.setCellFormula("SUM(I5:I10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_mb_anual = row1.createCell(9);
            cell_mb_anual.setCellStyle(cellStyle2);
            cell_mb_anual.setCellType(2);
            cell_mb_anual.setCellFormula("SUM(J5:J10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_gb_inicial = row1.createCell(10);
            cell_gb_inicial.setCellStyle(cellStyle2);
            cell_gb_inicial.setCellType(2);
            cell_gb_inicial.setCellFormula("SUM(K5:K10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_gb_mensal = row1.createCell(11);
            cell_gb_mensal.setCellStyle(cellStyle2);
            cell_gb_mensal.setCellType(2);
            cell_gb_mensal.setCellFormula("SUM(L5:L10)");
            firstSheet.autoSizeColumn(3);
            final HSSFCell cell_gb_anual = row1.createCell(12);
            cell_gb_anual.setCellStyle(cellStyle2);
            cell_gb_anual.setCellType(2);
            cell_gb_anual.setCellFormula("SUM(M5:M10)");
            firstSheet.autoSizeColumn(3);
            final File planilha_calculada = new File(extContext.getRealPath("//planilhas//Sizing_" + p_nome_cliente + ".xls"));
            fileOutputStream = new FileOutputStream(planilha_calculada);
            workbook.write((OutputStream)fileOutputStream);
        }
        catch (NumberFormatException ex) {}
        catch (IOException e) {
            System.out.println("Erro ao exportar arquivo" + e.getMessage());
        }
        finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        return this._erro;
    }
    
    public StreamedContent convertFichier(final Arquivo arquivo) throws FileNotFoundException, IOException {
        final ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        final File planilha_calculada = new File(extContext.getRealPath("//planilhas//" + arquivo.getNome()));
        final FileChannel channel = new RandomAccessFile(planilha_calculada, "r").getChannel();
        final InputStream is = Channels.newInputStream(channel);
        final String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(planilha_calculada.getPath());
        final StreamedContent file = (StreamedContent)new DefaultStreamedContent(is, contentType, planilha_calculada.getName());
        return file;
    }
    
    public List<Arquivo> listaArquivos() {
        final List<Arquivo> pasta_arquivos = new ArrayList<Arquivo>();
        try {
            final ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
            final File pasta = new File(extContext.getRealPath("//planilhas//"));
            final File[] arquivos = pasta.listFiles();
            for (int i = 0; i < arquivos.length; ++i) {
                final File file = arquivos[i];
                final Arquivo arq = new Arquivo();
                final Path filePath = file.toPath();
                final BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class, new LinkOption[0]);
                arq.setNome(file.getName());
                arq.setData(new Date(attributes.creationTime().toMillis()));
                pasta_arquivos.add(arq);
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        return pasta_arquivos;
    }
}