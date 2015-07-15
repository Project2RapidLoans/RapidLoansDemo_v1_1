/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.crud.BannerMethos;
import com.crud.Olympo8iMethods;
import static com.crud.RapidLoansCrud.findEquipoByTp_St_Cl_Cod;
import static com.crud.RapidLoansCrud.findUsuarioClienteByIdBanners;
import com.crud.RapidLoansMethods;
import com.map.AfActivofijo;
import com.map.Dzteqp;
import com.map.Dztprst;
import com.map.Dztuscli;
import com.map.PersonaBanner;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

/**
 *
 * @author IVAN
 */
@ManagedBean
@ViewScoped
public class PrestamoBean {

    /**
     * Creates a new instance of PrestamoBean
     */
    private String patterCli;
    private String patterEqp;
    private String dataEqp;
    private String dataCli;
    private Dztprst newPrstm;
    private AfActivofijo findactivo;
    private PersonaBanner findCli;
    private PersonaBanner usuarioId;
    private ArrayList<SelectItem> ItemsCli;
    private ArrayList<SelectItem> ItemsEqp;

    public PrestamoBean() {
        if (this.newPrstm == null || this.findactivo == null || this.findCli == null) {
            this.newPrstm = new Dztprst();
            this.findactivo = new AfActivofijo();
            this.findCli = new PersonaBanner();
        }
        this.findCli = new PersonaBanner();
        this.findactivo = new AfActivofijo();
        this.ItemsCli = new ArrayList<SelectItem>();
        this.ItemsEqp = new ArrayList<SelectItem>();
        this.loadItemsCli();
        this.loadItemsEqp();
        this.usuarioId = this.getUserInfo();
    }

    public void FindClient(ActionEvent event) {

        if (this.getPatterCli().equals("Id Banner")) {
            this.findCli = BannerMethos.FindPersonBannerByIdBanner(this.dataCli);
        } else if (this.getPatterCli().equals("Cedula")) {
            this.findCli = BannerMethos.FindPersonBannerByCdula(this.dataCli);
        } else if (this.getPatterCli().equals("Movil")) {

        }
    }

    public void FindEquipo(ActionEvent event) {

        if (this.getPatterEqp().equals("Codigo")) {
            this.findactivo = Olympo8iMethods.FindActivoByCodigo(this.dataEqp);
        } else if (this.getPatterCli().equals("Serie")) {
            //this.findactivo = Olympo8iMethods.FindActivoByCodigo("", "", "", "");
        }
    }

    public void getDataPrestamo() {

        if (this.findCli != null && this.findactivo != null && this.usuarioId != null) {
            this.newPrstm.getDzteqp().setDzteqpTipo(this.findactivo.getId().getTipo());
            this.newPrstm.getDzteqp().setDzteqpSubtipo(this.findactivo.getId().getSubtipo());
            this.newPrstm.getDzteqp().setDzteqpClase(this.findactivo.getId().getClase());
            this.newPrstm.getDzteqp().setDzteqpCodigo(this.findactivo.getId().getCodigo());
            this.newPrstm.getDzteqp().setDzteqpDescripcion(this.findactivo.getDescripcion());
            this.newPrstm.getDzteqp().setDzteqpMarca(this.findactivo.getMarca());
            this.newPrstm.getDzteqp().setDzteqpModelo(this.findactivo.getModelo());
            this.newPrstm.getDzteqp().setDzteqpSerie(this.findactivo.getNserie());
            this.newPrstm.getDzteqp().setDzteqpEstado(this.findactivo.getEstado());
            this.newPrstm.getDzteqp().setDzteqpUbicacion(this.findactivo.getSucursal());
            this.newPrstm.getDzteqp().setDzteqpCampus(this.findactivo.getDescDireccion());
            this.newPrstm.getDzteqp().setDzteqpEstadoAc(this.findactivo.getEstado());
            this.newPrstm.getDzteqp().setDzteqpCustodios(this.findactivo.getCustodio1() + "-" + this.findactivo.getCustodio2());
            this.newPrstm.getDzteqp().setDzteqpCodBien(this.findactivo.getCodBien());
            this.newPrstm.getDzteqp().setDzteqpObservacion(this.findactivo.getObservacion());

            this.newPrstm.getDztuscli().setDztuscliCampus(this.usuarioId.getCampus());
            this.newPrstm.getDztuscli().setDztuscliCedulaUs(this.usuarioId.getCedula());
            this.newPrstm.getDztuscli().setDztuscliIdbannerUs(this.usuarioId.getIdBanner());
            this.newPrstm.getDztuscli().setDztuscliCedulaCli(this.findCli.getCedula());
            this.newPrstm.getDztuscli().setDztuscliIdbannerCli(this.findCli.getIdBanner());

            this.newPrstm.setDztprstFechaIn(new Date());
            this.newPrstm.setDztprstFechaOut(new Date());
            this.newPrstm.setDztprstUnqcod("123456789");

        }

    }

    public void GeneratePrestamo(ActionEvent event) {

        /**
         * Observacion, destino y movil
         */
        FacesMessage message = null;
        this.getDataPrestamo();
        Boolean ex2 = RapidLoansMethods.InsertUsuarioCliente(this.newPrstm.getDztuscli());
        Boolean ex3 = RapidLoansMethods.InsertActivo(this.newPrstm.getDzteqp());
        Dzteqp eqpnew = findEquipoByTp_St_Cl_Cod(this.newPrstm.getDzteqp());
        Dztuscli usclinew = findUsuarioClienteByIdBanners(this.newPrstm.getDztuscli());
        this.newPrstm.getId().setDzteqpId(eqpnew.getDzteqpId());
        this.newPrstm.getId().setDztuscliId(usclinew.getDztuscliId());
        int count = RapidLoansMethods.CountPrestamoById(this.newPrstm);
        //this.newPrstm.setDztprstUnqcod(""+eqpnew.getDzteqpId()+""+usclinew.getDztuscliId()+""+count);
        Boolean ex1 = RapidLoansMethods.InsertPrestamo(this.newPrstm);

        if (ex1 && ex2 && ex3) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha guardado exitosamente", "");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha guardado", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    private PersonaBanner getUserInfo() {
        PersonaBanner Usuario = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session == null) {
            Usuario = new PersonaBanner();
        } else {
            Object currentUser = session.getAttribute("IdBanner");
            Usuario = BannerMethos.FindPersonBannerByIdBanner(currentUser.toString());
        }
        return Usuario;
    }

    private void loadItemsCli() {
        ArrayList<String> selectItemsCli = new ArrayList<String>();
        selectItemsCli.add("Id Banner");
        selectItemsCli.add("Cedula");
        selectItemsCli.add("Movil");
        for (String Item : selectItemsCli) {
            this.ItemsCli.add(new SelectItem(Item, Item));
        }
    }

    private void loadItemsEqp() {
        ArrayList<String> selectItemsEqp = new ArrayList<String>();
        selectItemsEqp.add("Codigo");
        selectItemsEqp.add("Serie");
        for (String Item : selectItemsEqp) {
            this.ItemsEqp.add(new SelectItem(Item, Item));
        }
    }

    /**
     * @return the patterCli
     */
    public String getPatterCli() {
        return patterCli;
    }

    /**
     * @param patterCli the patterCli to set
     */
    public void setPatterCli(String patterCli) {
        this.patterCli = patterCli;
    }

    /**
     * @return the patterEqp
     */
    public String getPatterEqp() {
        return patterEqp;
    }

    /**
     * @param patterEqp the patterEqp to set
     */
    public void setPatterEqp(String patterEqp) {
        this.patterEqp = patterEqp;
    }

    /**
     * @return the dataEqp
     */
    public String getDataEqp() {
        return dataEqp;
    }

    /**
     * @param dataEqp the dataEqp to set
     */
    public void setDataEqp(String dataEqp) {
        this.dataEqp = dataEqp;
    }

    /**
     * @return the dataCli
     */
    public String getDataCli() {
        return dataCli;
    }

    /**
     * @param dataCli the dataCli to set
     */
    public void setDataCli(String dataCli) {
        this.dataCli = dataCli;
    }

    /**
     * @return the newPrstm
     */
    public Dztprst getNewPrstm() {
        return newPrstm;
    }

    /**
     * @param newPrstm the newPrstm to set
     */
    public void setNewPrstm(Dztprst newPrstm) {
        this.newPrstm = newPrstm;
    }

    /**
     * @return the findactivo
     */
    public AfActivofijo getFindactivo() {
        return findactivo;
    }

    /**
     * @param findactivo the findactivo to set
     */
    public void setFindactivo(AfActivofijo findactivo) {
        this.findactivo = findactivo;
    }

    /**
     * @return the findCli
     */
    public PersonaBanner getFindCli() {
        return findCli;
    }

    /**
     * @param findCli the findCli to set
     */
    public void setFindCli(PersonaBanner findCli) {
        this.findCli = findCli;
    }

    /**
     * @return the ItemsCli
     */
    public ArrayList<SelectItem> getItemsCli() {
        return ItemsCli;
    }

    /**
     * @param ItemsCli the ItemsCli to set
     */
    public void setItemsCli(ArrayList<SelectItem> ItemsCli) {
        this.ItemsCli = ItemsCli;
    }

    /**
     * @return the ItemsEqp
     */
    public ArrayList<SelectItem> getItemsEqp() {
        return ItemsEqp;
    }

    /**
     * @param ItemsEqp the ItemsEqp to set
     */
    public void setItemsEqp(ArrayList<SelectItem> ItemsEqp) {
        this.ItemsEqp = ItemsEqp;
    }

    /**
     * @return the usuarioId
     */
    public PersonaBanner getUsuarioId() {
        return usuarioId;
    }

    /**
     * @param usuarioId the usuarioId to set
     */
    public void setUsuarioId(PersonaBanner usuarioId) {
        this.usuarioId = usuarioId;
    }

}
