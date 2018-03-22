package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void rahanLatausKasvattaaArvoaOikein() {
        kortti.lataaRahaa(100);
        assertEquals("saldo: 1.10", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinKunSaldoRiittaa() {
        kortti.otaRahaa(10);
        assertEquals("saldo: 0.0", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutoKunSaldoaEiTarpeeksi() {
        kortti.otaRahaa(20);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void palauttaTrueKunTarpeeksiRahaa() {
        assertEquals(true, kortti.otaRahaa(5));
    }
    
    @Test
    public void palauttaaFalseKunSaldoEiRiita() {
        assertEquals(false, kortti.otaRahaa(20));
    }
}
