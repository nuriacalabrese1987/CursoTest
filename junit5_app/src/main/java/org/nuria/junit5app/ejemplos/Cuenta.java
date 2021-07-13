package org.nuria.junit5app.ejemplos;


import java.math.BigDecimal;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;


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


    public void debito(BigDecimal monto){
        this. saldo = this.saldo.subtract(monto);
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

