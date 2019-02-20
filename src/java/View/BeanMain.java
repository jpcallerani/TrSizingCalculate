package View;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;
import java.io.IOException;
import java.math.BigDecimal;
import Control.ControlMain;
import java.util.ArrayList;
import Control.Arquivo;
import Modal.DaSizingEstimado;
import java.util.List;
import Modal.DaSizingQuestionario;
import org.primefaces.model.StreamedContent;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "beanMain")
@RequestScoped
public class BeanMain
{
    private StreamedContent _trDownload;
    private DaSizingQuestionario _sizing_questionario;
    private List<DaSizingEstimado> _sizing_estimados;
    private List<DaSizingQuestionario> _sizing_questionarios;
    private List<DaSizingQuestionario> _nucleo_comum;
    private List<DaSizingQuestionario> _importacao;
    private List<DaSizingQuestionario> _exportacao;
    private List<DaSizingQuestionario> _cambio_imp;
    private List<DaSizingQuestionario> _cambio_exp;
    private DaSizingEstimado _sizing_estimado;
    private String _nome_cliente;
    private String _erro;
    private List<Arquivo> _arquivos;
    
    public BeanMain() {
        this._nome_cliente = "";
        this._erro = "";
        this._arquivos = new ArrayList<Arquivo>();
        this._sizing_questionarios = new ControlMain().retornaQuestionario();
        this._sizing_estimado = new DaSizingEstimado();
        this._sizing_estimados = new ArrayList<DaSizingEstimado>();
        this._sizing_questionario = new DaSizingQuestionario();
        this._nucleo_comum = new ArrayList<DaSizingQuestionario>();
        this._exportacao = new ArrayList<DaSizingQuestionario>();
        this._importacao = new ArrayList<DaSizingQuestionario>();
        this._cambio_exp = new ArrayList<DaSizingQuestionario>();
        this._cambio_imp = new ArrayList<DaSizingQuestionario>();
        this.separaQuestionario();
        this.listaArquivos();
    }
    
    private void separaQuestionario() {
        this._nome_cliente = "";
        for (int i = 0; i < this._sizing_questionarios.size(); ++i) {
            final DaSizingQuestionario questao = this._sizing_questionarios.get(i);
            questao.setRespostaSimNao('S');
            questao.setQtdeInicial(BigDecimal.ZERO);
            questao.setPercCrescimentoMensal(BigDecimal.ZERO);
            if (questao.getId() == 34 || questao.getId() == 3 || questao.getId() == 4 || questao.getId() == 5 || questao.getId() == 20) {
                questao.setPercCrescimentoMensal(new BigDecimal(5));
            }
            questao.setQtdeMensal(BigDecimal.ZERO);
            if (questao.getCodSistema() == 0) {
                this._nucleo_comum.add(questao);
            }
            else if (questao.getCodSistema() == 2) {
                this._exportacao.add(questao);
            }
            else if (questao.getCodSistema() == 9) {
                this._importacao.add(questao);
            }
        }
        final DaSizingQuestionario cambio_imp_question = new DaSizingQuestionario();
        cambio_imp_question.setCodSistema(10);
        cambio_imp_question.setEntidade("---");
        cambio_imp_question.setRespostaSimNao('S');
        cambio_imp_question.setQuestao("Opera\u00e7\u00e3o de Importa\u00e7\u00e3o integrada com o Sistema de C\u00e2mbio Imp. da Softway?");
        this._cambio_imp.add(cambio_imp_question);
        final DaSizingQuestionario cambio_exp_question = new DaSizingQuestionario();
        cambio_exp_question.setCodSistema(11);
        cambio_exp_question.setEntidade("---");
        cambio_exp_question.setRespostaSimNao('S');
        cambio_exp_question.setQuestao("Opera\u00e7\u00e3o de Exporta\u00e7\u00e3o integrada com o Sistema de C\u00e2mbio Exp. da Softway?");
        this._cambio_exp.add(cambio_exp_question);
    }
    
    public void downloadArquivo(final Arquivo arquivo) throws IOException {
        this._trDownload = new ControlMain().convertFichier(arquivo);
    }
    
    public void listaArquivos() {
        Collections.sort(this._arquivos = new ControlMain().listaArquivos());
    }
    
    public String formatDate(final Date data) {
        return new SimpleDateFormat("dd/MM/yyyy H:mm:ss").format(data);
    }
    
    public void geraDadosSizing() throws IOException {
        FacesMessage msg = null;
        try {
            this._erro += new ControlMain().atualizaDadosSizing(this._nucleo_comum);
            this._erro += new ControlMain().atualizaDadosSizing(this._importacao);
            this._erro += new ControlMain().atualizaDadosSizing(this._exportacao);
            this._erro += new ControlMain().calculaSizing();
            if (this._erro.isEmpty()) {
                this._sizing_estimados = new ControlMain().retornaSizingEstimado();
                this._erro = new ControlMain().criaPlanilhaSizing(this._sizing_estimados, this._nome_cliente);
                final String nome_cliente = this._nome_cliente;
                this.separaQuestionario();
                this.listaArquivos();
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aten\u00e7\u00e3o", "Dados enviado com sucesso!");
            }
            else {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aten\u00e7\u00e3o", this._erro);
            }
        }
        catch (Exception e) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aten\u00e7\u00e3o", e.getMessage());
            FacesContext.getCurrentInstance().addMessage((String)null, msg);
        }
        FacesContext.getCurrentInstance().addMessage((String)null, msg);
    }
    
    public DaSizingQuestionario getSizing_questionario() {
        return this._sizing_questionario;
    }
    
    public void setSizing_questionario(final DaSizingQuestionario _sizing_questionario) {
        this._sizing_questionario = _sizing_questionario;
    }
    
    public DaSizingEstimado getSizing_estimado() {
        return this._sizing_estimado;
    }
    
    public void setSizing_estimado(final DaSizingEstimado _sizing_estimado) {
        this._sizing_estimado = _sizing_estimado;
    }
    
    public List<DaSizingQuestionario> getSizing_questionarios() {
        return this._sizing_questionarios;
    }
    
    public void setSizing_questionarios(final List<DaSizingQuestionario> _sizing_questionarios) {
        this._sizing_questionarios = _sizing_questionarios;
    }
    
    public List<DaSizingQuestionario> getNucleo_comum() {
        return this._nucleo_comum;
    }
    
    public void setNucleo_comum(final List<DaSizingQuestionario> _nucleo_comum) {
        this._nucleo_comum = _nucleo_comum;
    }
    
    public List<DaSizingQuestionario> getImportacao() {
        return this._importacao;
    }
    
    public void setImportacao(final List<DaSizingQuestionario> _importacao) {
        this._importacao = _importacao;
    }
    
    public List<DaSizingQuestionario> getExportacao() {
        return this._exportacao;
    }
    
    public void setExportacao(final List<DaSizingQuestionario> _exportacao) {
        this._exportacao = _exportacao;
    }
    
    public List<DaSizingQuestionario> getCambio_imp() {
        return this._cambio_imp;
    }
    
    public void setCambio_imp(final List<DaSizingQuestionario> _cambio_imp) {
        this._cambio_imp = _cambio_imp;
    }
    
    public List<DaSizingQuestionario> getCambio_exp() {
        return this._cambio_exp;
    }
    
    public void setCambio_exp(final List<DaSizingQuestionario> _cambio_exp) {
        this._cambio_exp = _cambio_exp;
    }
    
    public String getNome_cliente() {
        return this._nome_cliente;
    }
    
    public void setNome_cliente(final String _nome_cliente) {
        this._nome_cliente = _nome_cliente;
    }
    
    public StreamedContent getTrDownload() {
        return this._trDownload;
    }
    
    public void setTrDownload(final StreamedContent _trDownload) {
        this._trDownload = _trDownload;
    }
    
    public List<Arquivo> getArquivos() {
        return this._arquivos;
    }
    
    public void setArquivos(final List<Arquivo> _arquivos) {
        this._arquivos = _arquivos;
    }
}