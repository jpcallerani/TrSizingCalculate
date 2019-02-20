package View;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;
import java.awt.event.ActionEvent;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "beanLogin")
@RequestScoped
public class BeanLogin
{
    String _username;
    String _password;
    
    public BeanLogin() {
        this._username = "";
        this._password = "";
    }
    
    public void login(final ActionEvent actionEvent) {
        final RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        if (this._username.equals("ADM") && this._password.equals("swadm")) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aten\u00e7\u00e3o", "Login efetuado com sucesso!");
        }
        else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no login.", "Username/password invalid");
        }
        this._username = "";
        this._password = "";
        FacesContext.getCurrentInstance().addMessage((String)null, msg);
        context.addCallbackParam("loggedIn", (Object)loggedIn);
    }
    
    public String getUsername() {
        return this._username;
    }
    
    public void setUsername(final String _username) {
        this._username = _username;
    }
    
    public String getPassword() {
        return this._password;
    }
    
    public void setPassword(final String _password) {
        this._password = _password;
    }
}