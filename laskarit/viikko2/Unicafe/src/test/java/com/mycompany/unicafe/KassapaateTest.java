
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void kassanRahatAlussaOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void kassanLounaatAlussaOikein() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty() + kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahatKasvavatKunRahatRiittavatEdulliseen() {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassaAntaaVaihtoRahanOikeinEdullisessa() {
        assertEquals(10, kassa.syoEdullisesti(250));
    }
    
    @Test
    public void kassanRahatKasvavatKunRahatRiittavatMaukkaaseen() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassaAntaaVaihtoRahanOikeinMaukkaassa() {
        assertEquals(10, kassa.syoMaukkaasti(410));
    }
    
    @Test
    public void edullistenMaaraKasvaaKunRahaRiittaa() {
        kassa.syoEdullisesti(250);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaidenMaaraKasvaaKunRahaRiittaa() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanSaldoEiMuutuKunEdullisestaEiMaksetaTarpeeksi() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kaikkiRahatPalautetaanKunEdullisenMaksuEiRiita() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void edullistenMaaraEiMuutuKunMaksuEiRiita() {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassanSaldoEiMuutuKunMaukkaastaEiMaksetaTarpeeksi() {
        kassa.syoMaukkaasti(300);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kaikkiRahatPalautetaanKunMaukkaanMaksuEiRiita() {
        assertEquals(300, kassa.syoMaukkaasti(300));
    }
    
    @Test
    public void maukkaidenMaaraEiMuutuKunMaksuEiRiita() {
        kassa.syoMaukkaasti(300);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortiltaVeloitetaanEdullisenVerranKunSaldoRiittaa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void palautetaanTrueKunKortillaTarpeeksiEdulliseen() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void kortiltaVeloitetaanMaukkaanVerranKunSaldoRiittaa() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void palautetaanTrueKunKortillTarpeeksiMaukkaaseen() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void edullistenMaaraKasvaaKunKortillaTarpeeksiEdulliseen() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaidenMaaraKasvaaKunKortillaTarpeeksiMaukkaaseen() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortinRahaMaaraEiMuutuKunEiVaraaEdulliseen() {
        Maksukortti k = new Maksukortti(200);
        kassa.syoEdullisesti(k);
        assertEquals(200, k.saldo());
    }
    
    @Test
    public void lounaidenMaaraEiMuutuKunKortillaEiTarpeeksiEdulliseen() {
        Maksukortti k = new Maksukortti(200);
        kassa.syoEdullisesti(k);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void palautetaanFalseKunKortillaEiTarpeeksiEdulliseen() {
        Maksukortti k = new Maksukortti(200);
        assertEquals(false, kassa.syoEdullisesti(k));
    }
    
    @Test
    public void kortinRahaMaaraEiMuutuKunEiVaraaMaukkaaseen() {
        Maksukortti k = new Maksukortti(200);
        kassa.syoMaukkaasti(k);
        assertEquals(200, k.saldo());
    }
    
    @Test
    public void lounaidenMaaraEiMuutuKunKortillaEiTarpeeksiMaukkaaseen() {
        Maksukortti k = new Maksukortti(200);
        kassa.syoMaukkaasti(k);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void palautetaanFalseKunKortillaEiTarpeeksiMaukkaaseen() {
        Maksukortti k = new Maksukortti(200);
        assertEquals(false, kassa.syoMaukkaasti(k));
    }
    
    @Test
    public void KassaEiMuutuKortillaEdullinen() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassaEiMuutuKortillaMaukas() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortinSaldoMuuttuuRahaaLadattaessa() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(1500, kortti.saldo());
    }
    
    @Test
    public void kassanRahatKasvaaKorttiaLadattaessa() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(100500, kassa.kassassaRahaa());
    }
    
    @Test
    public void negatiivinenLatausEiMuutaKorttia() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void negatiivinenLatausEiMuutaKassaa() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
}
