/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.beans;

import com.crud.BannerMethos;
import com.crud.RapidLoansMethods;
import com.map.Dztuscli;
import com.map.PersonaBanner;
import com.web.RapidLoansUtil;
import java.awt.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author IVAN
 */
@ManagedBean
@ApplicationScoped
public class LoginBean {

    /**
     * Creates a new instance of LoginBean
     */
    private PersonaBanner usuario = new PersonaBanner();
    HttpServletRequest origRequest
            = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private String urlRequest;
    
    public LoginBean() {
        this.urlRequest = origRequest.getRequestURI().toString();
        if(this.usuario == null){
            this.usuario = new PersonaBanner();
        }
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/index.xhtml", ""));
        this.setUrlRequest(this.urlRequest = this.urlRequest.replace("faces/loginPage", ""));
    }
    
    public void login(ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean LoggedIn;
        String ruta = "";
        PersonaBanner findperson = BannerMethos.FindPersonBannerByIdBanner(this.usuario.getIdBanner());
        if(findperson.getCedula().equals(this.usuario.getCedula())){
            LoggedIn = true;
            this.usuario  = findperson;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("IdBanner", this.usuario.getIdBanner());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("Cedula", this.usuario.getCedula());
            ruta = RapidLoansUtil.getURL_Login()+"views/inicio.xhtml";
        }else{
            LoggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN,"No esta Autorizado",null);
            if(findperson == null){
                this.usuario = new PersonaBanner();
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", LoggedIn);
        context.addCallbackParam("ruta", ruta);
        
    }
    
    public void logout(){
        String ruta = RapidLoansUtil.getURL_Login()+"index.xhtml";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.invalidate();      
        context.addCallbackParam("loggerOut", true);
        context.addCallbackParam("ruta", ruta);
    }

    /**
     * @return the urlRequest
     */
    public String getUrlRequest() {
        return urlRequest;
    }

    /**
     * @param urlRequest the urlRequest to set
     */
    public void setUrlRequest(String urlRequest) {
        this.urlRequest = urlRequest;
    }
    
}
