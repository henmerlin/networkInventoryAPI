/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.domain;

/**
 *
 * @author G0042204
 */
public class FactoryService {
    
    public static CustomerServiceInter create(){
        return new ConsultaFacade();
    }
    
}
