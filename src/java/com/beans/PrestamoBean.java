/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.beans;

import com.crud.*;
import com.map.*;
import java.util.ArrayList;
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
    private AfActivofijo findequipo;
    private PersonaBanner findCli;
    private PersonaBanner usuarioId;
    private ArrayList<SelectItem> ItemsCli;
    private ArrayList<SelectItem> ItemsEqp;

    public PrestamoBean() {
        this.init();
        if (this.newPrstm == null
                || this.findequipo == null
                || this.findCli == null) {
            this.newPrstm = new Dztprst();
            this.findequipo = new AfActivofijo();
            this.findCli = new PersonaBanner();
        }
    }

    private void init() {
        this.newPrstm = new Dztprst();
        this.findequipo = new AfActivofijo();
        this.findCli = new PersonaBanner();
        this.ItemsCli = new ArrayList<SelectItem>();
        this.ItemsEqp = new ArrayList<SelectItem>();
        this.usuarioId = this.getUserInfo();
        this.dataCli = "";
        this.dataEqp = "";
        this.patterCli = "";
        this.patterEqp = "";
        this.loadItemsCli();
        this.loadItemsEqp();
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
            this.findequipo = Olympo8iMethods.FindActivoByCodigo(this.dataEqp);
        } else if (this.getPatterCli().equals("Serie")) {
            //this.findequipo = Olympo8iMethods.FindActivoByCodigo("", "", "", "");
        }
    }

    public Boolean getDataCliente() {

        Boolean exito = false;
        this.newPrstm.getDztcli().
                setDztcliCedula(this.findCli.getCedula());
        this.newPrstm.getDztcli().
                setDztcliEmail(this.findCli.getEmail());
        this.newPrstm.getDztcli().
                setDztcliIdbanner(this.findCli.getIdBanner());
        if (RapidLoansMethods.
                FindCliente(this.newPrstm.getDztcli()) != null) {
            this.newPrstm.getDztcli().
                    setDztcliId(RapidLoansMethods.
                            FindCliente(this.newPrstm.getDztcli()).getDztcliId());
            exito = RapidLoansMethods.UpdateCliente(this.newPrstm.getDztcli());
        } else {
            exito = RapidLoansMethods.InsertCliente(this.newPrstm.getDztcli());
        }

        return exito;
    }

    public Boolean getDataUsuario() {

        Boolean exito = false;
        this.newPrstm.getDztus().
                setDztusCampus(this.usuarioId.getCampus());
        this.newPrstm.getDztus().
                setDztusCedula(this.usuarioId.getCedula());
        this.newPrstm.getDztus().
                setDztusEmail(this.usuarioId.getEmail());
        this.newPrstm.getDztus().
                setDztusIdbanner(this.usuarioId.getIdBanner());
        if (RapidLoansMethods.
                FindUsuario(this.newPrstm.getDztus()) != null) {
            this.newPrstm.getDztus().
                    setDztusId(RapidLoansMethods.
                            FindUsuario(this.newPrstm.getDztus()).getDztusId());
            exito = RapidLoansMethods.UpdateUsuario(this.newPrstm.getDztus());
        } else {
            exito = RapidLoansMethods.InsertUsuario(this.newPrstm.getDztus());
        }
        return exito;
    }

    public Boolean getDataEquipo() {

        Boolean exito = false;
        this.newPrstm.getDzteqp().
                setDzteqpTipo(this.findequipo.getId().getTipo());
        this.newPrstm.getDzteqp().
                setDzteqpSubtipo(this.findequipo.getId().getSubtipo());
        this.newPrstm.getDzteqp().
                setDzteqpClase(this.findequipo.getId().getClase());
        this.newPrstm.getDzteqp().
                setDzteqpCodigo(this.findequipo.getId().getCodigo());
        this.newPrstm.getDzteqp().
                setDzteqpDescripcion(this.findequipo.getDescripcion());
        this.newPrstm.getDzteqp().
                setDzteqpMarca(this.findequipo.getMarca());
        this.newPrstm.getDzteqp().
                setDzteqpModelo(this.findequipo.getModelo());
        this.newPrstm.getDzteqp().
                setDzteqpSerie(this.findequipo.getNserie());
        this.newPrstm.getDzteqp().
                setDzteqpEstado(this.findequipo.getEstado());
        this.newPrstm.getDzteqp().
                setDzteqpUbicacion(this.findequipo.getSucursal());
        this.newPrstm.getDzteqp().
                setDzteqpCampus(this.findequipo.getDescDireccion());
        this.newPrstm.getDzteqp().
                setDzteqpEstadoAc(this.findequipo.getEstado());
        this.newPrstm.getDzteqp().
                setDzteqpCustodio1(this.findequipo.getCustodio1());
        this.newPrstm.getDzteqp().
                setDzteqpCustodio2(this.findequipo.getCustodio2());
        this.newPrstm.getDzteqp().
                setDzteqpCodBien(this.findequipo.getCodBien());
        this.newPrstm.getDzteqp().
                setDzteqpObservacion(this.findequipo.getObservacion());
        this.newPrstm.getDzteqp().
                setDzteqpFlag(RapidLoansMethods.$EQP_HABILITADO);
        if (RapidLoansMethods.
                FindEquipoByTp_St_Cl_Cod(this.newPrstm.getDzteqp()) != null) {
            this.newPrstm.getDzteqp().
                    setDzteqpId(RapidLoansMethods.
                            FindEquipoByTp_St_Cl_Cod(this.newPrstm.getDzteqp()).getDzteqpId());
            exito = RapidLoansMethods.UpdateEquipo(this.newPrstm.getDzteqp());
        } else {
            exito = RapidLoansMethods.InsertEquipo(this.newPrstm.getDzteqp());
        }
        return exito;
    }

    public Boolean getDataPrestamo() {

        Boolean exito = false;
        this.newPrstm.getDzteqp().
                setDzteqpId(RapidLoansMethods.
                        FindEquipoByTp_St_Cl_Cod(
                                this.newPrstm.getDzteqp()).getDzteqpId());
        this.newPrstm.getDztcli().
                setDztcliId(RapidLoansMethods.
                        FindCliente(this.newPrstm.getDztcli()).getDztcliId());
        this.newPrstm.getDztus().
                setDztusId(RapidLoansMethods.
                        FindUsuario(this.newPrstm.getDztus()).getDztusId());
        this.newPrstm.setDztprstFechaOut(
                RapidLoansMethods.getDateUpdate());
        this.newPrstm.
                setDztprstUnqcod(RapidLoansMethods.
                        getUnqCode(this.newPrstm));
        this.newPrstm.setDztprstFlag(RapidLoansMethods.$PRST_ABIERTO);

        exito = RapidLoansMethods.InsertPrestamo(this.newPrstm);
        return exito;
    }

    public void GeneratePrestamo(ActionEvent event) {

        FacesMessage message = null;
        Boolean exito = this.getDataCliente()
                && this.getDataUsuario()
                && this.getDataEquipo()
                && this.getDataPrestamo();
        if (exito) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha guardado exitosamente", "");
            this.init();
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
        this.ItemsCli.clear();
        for (String Item : selectItemsCli) {
            this.ItemsCli.add(new SelectItem(Item, Item));
        }
    }

    private void loadItemsEqp() {
        ArrayList<String> selectItemsEqp = new ArrayList<String>();
        selectItemsEqp.add("Codigo");
        selectItemsEqp.add("Serie");
        this.ItemsEqp.clear();
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
     * @return the findequipo
     */
    public AfActivofijo getFindactivo() {
        return findequipo;
    }

    /**
     * @param findequipo the findequipo to set
     */
    public void setFindactivo(AfActivofijo findequipo) {
        this.findequipo = findequipo;
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
