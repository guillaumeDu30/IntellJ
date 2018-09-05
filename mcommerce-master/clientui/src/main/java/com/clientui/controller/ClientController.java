package com.clientui.controller;

import com.clientui.beans.ExpeditionBean;
import com.clientui.beans.ProductBean;
import com.clientui.proxies.MicroserviceExpeditionProxy;
import com.clientui.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy mProduitsProxy;

    @Autowired
    private MicroserviceExpeditionProxy mExpeditionProxy;

    @RequestMapping("/")
    public String accueil(Model model) {
        List<ProductBean> produits = mProduitsProxy.listeDesProduits();
        model.addAttribute("produits", produits);
        return "Accueil";
    }

    @RequestMapping("/details-produit/{id}")
    public String ficheProduit(@PathVariable int id, Model model) {

        ProductBean produit = mProduitsProxy.recupererUnProduit(id);

        model.addAttribute("produit", produit);

        return "FicheProduit";
    }

    @RequestMapping(value = "/suivi/{id}")
    public String etatExpedition(@PathVariable int id, Model model){
        Optional<ExpeditionBean> expeditionBean = mExpeditionProxy.getExpedition(id);
        model.addAttribute("expedition", expeditionBean.get());
        return "FicheExpedition";
    }
}
