package com.curso.junit.udemy.models;

import java.math.BigDecimal;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public String getPersona() {
        return this.persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return this.saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto){
        
        this.saldo = this.saldo.subtract(monto);
    }
    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object o) {
        // PAra compar por valor y no por referencia en memoria
        // Validamos que no haya nulos
        if (!(o instanceof Cuenta) || this.saldo == null || this.persona == null) {
            return false;
        }
        // Casteamos y comprobamos
        Cuenta cuenta = (Cuenta) o;
        return this.persona.equals(cuenta.getPersona()) && this.saldo.equals(cuenta.getSaldo());
    }

}
