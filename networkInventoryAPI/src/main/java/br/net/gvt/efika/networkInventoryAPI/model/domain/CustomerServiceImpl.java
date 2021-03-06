/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.net.gvt.efika.networkInventoryAPI.model.domain;

import br.net.gvt.efika.efika_customer.model.customer.EfikaCustomer;
import br.net.gvt.efika.networkInventoryAPI.dao.EfikaCustomerInterface;
import br.net.gvt.efika.networkInventoryAPI.dao.ExternalNetworkSigresDAO;
import br.net.gvt.efika.networkInventoryAPI.dao.FactoryDAO;
import br.net.gvt.efika.networkInventoryAPI.dao.OltDetailSigresFibraDAO;
import br.net.gvt.efika.networkInventoryAPI.model.domain.adapter.EfikaCustomerAdapter;
import br.net.gvt.efika.networkInventoryAPI.model.domain.exception.CustomerNotFound;
import br.net.gvt.efika.networkInventoryAPI.model.entity.ExternalNetworkSigres;
import br.net.gvt.efika.networkInventoryAPI.model.entity.NetworkInventoryGpon;
import br.net.gvt.efika.networkInventoryAPI.model.entity.NetworkInventoryMetalico;
import br.net.gvt.efika.networkInventoryAPI.model.entity.NetworkInventorySigresFibra;

/**
 *
 * @author G0042204
 */
public class CustomerServiceImpl implements CustomerService {

    private EfikaCustomerInterface<NetworkInventoryGpon> gpon;
    private EfikaCustomerInterface<NetworkInventoryMetalico> metalico;
    private EfikaCustomerInterface<NetworkInventorySigresFibra> sigresFibra;
    private OltDetailSigresFibraDAO oltDetail;
    private ExternalNetworkSigresDAO exnDAO;

    public CustomerServiceImpl() {
    }

    @Override
    public EfikaCustomer consultar(String instancia) throws Exception {
        try {
            gpon = FactoryDAO.createGponVivo2();
            return EfikaCustomerAdapter.adapter(gpon.consultarCliente(instancia));
        } catch (Exception e) {
            try {
                metalico = FactoryDAO.createMetalicoVivo2();
                return EfikaCustomerAdapter.adapter(metalico.consultarCliente(instancia));
            } catch (Exception ex) {
                try {
                    sigresFibra = FactoryDAO.createFibraVivo1();
                    NetworkInventorySigresFibra sigres = sigresFibra.consultarCliente(instancia);
                    return EfikaCustomerAdapter.adapter(sigres);
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                    throw new CustomerNotFound();
                }
            }
        }
    }

}
