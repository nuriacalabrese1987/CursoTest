package org.nuria.junit5app.ejemplos.models;


import org.nuria.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;


    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() { return banco; }

    public void setBanco(Banco banco) { this.banco = banco; }


    public void debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }


    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }


    @Override
    public boolean equals(Object obj) {
    //Sobreescribimos el metodo de equals, primero comparamos que el objeto sea nulo o distinto de cuenta
        if(obj==null || !(obj instanceof Cuenta)){
            return false;
        }
        Cuenta c = (Cuenta)obj;
    //Despues miramos que nungun atributo sea nulo
        if(this.persona == null || this.saldo == null){
            return false;
        }
    //retorna true si tu persona tiene los atributos iguales que el objeto que esta pasando
        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}

