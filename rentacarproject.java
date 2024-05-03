
package com.mycompany.rentacarproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RentACarProject {

    public static void main(String[] args) {
         String[] arabaModelleri = new String[10];
        double[] arabaFiyatlari = new double[10];
        boolean[] musaitlikDurumlari = new boolean[10];
        double total;
        String onay;
        
        
        Scanner scan = new Scanner(System.in);
        
        // Araba bilgilerini dosyadan oku
        try (BufferedReader reader = new BufferedReader(new FileReader("araba_bilgileri.txt"))) {
            for (int i = 0; i < 10; i++) {
                String line = reader.readLine();
                if (line != null) {
                    String[] arabaBilgileri = line.split(",");
                    arabaModelleri[i] = arabaBilgileri[0];
                    arabaFiyatlari[i] = Double.parseDouble(arabaBilgileri[1]);
                    musaitlikDurumlari[i] = Boolean.parseBoolean(arabaBilgileri[2]);
                }
            }
        } 
        catch (IOException e) {
            System.err.println("Dosya okuma hatası: " + e);
            return;
        }
        
        //Bilgileri ekrana yazdır
        System.out.printf("%-5s %-15s %-10s%n", "No", "Model", "Fiyat");
        System.out.println("-----------------------------");
        for (int k = 0; k < arabaModelleri.length; k++) 
        {
            System.out.printf("%-5d %-15s %-10.2f %-10s%n", (k + 1), arabaModelleri[k], arabaFiyatlari[k],(musaitlikDurumlari[k] ? "Müsait" : "Müsait değil"));
        }

        
        //Kullanıcıya model seçtir
        System.out.println("İstediğiniz modeli giriniz:");
        int modelNo=scan.nextInt();
        modelNo--;
        
        //Müsaitlik kontrolü
        if (musaitlikDurumlari[modelNo])
        {
            // Kullanıcı bilgilerini al
            System.out.print("İsim: ");
            String isim = scan.next();
            System.out.print("Soyisim: ");
            String soyisim = scan.next();
            System.out.print("Telefon Numarası: ");
            String telefonNumarasi = scan.next();

            // Kiralama tarihlerini al
            System.out.print("Kiralama Başlangıç Tarihi (GG.AA.YYYY): ");
            String baslangicTarihiStr = scan.next();
            System.out.print("Kiralama Bitiş Tarihi (GG.AA.YYYY): ");
            String bitisTarihiStr = scan.next();
            System.out.println("Kiralama Süresi (Ay)");
            int kiralama_suresi=scan.nextInt();
  
            double indirimOrani;

            if (kiralama_suresi >= 6 && 12 <= kiralama_suresi)
            {
                indirimOrani = 0.10;
            }

            else if (kiralama_suresi > 13 && kiralama_suresi <= 18 )
            {
                indirimOrani = 0.15;
            }
            
            else if (kiralama_suresi >= 19 && kiralama_suresi <=24)
            {
                indirimOrani=0.20;
            }
            
            else if (kiralama_suresi > 25)
            {
                indirimOrani=0.30;
            }
            
            else
            {
                indirimOrani = 0;
            }
            
            //Total hesaplama
            total=((arabaFiyatlari[modelNo]*kiralama_suresi)*(1.0-indirimOrani))*1.16;//KDV dahil
            
            //Müşteri onayı
            System.out.println(baslangicTarihiStr+"-"+bitisTarihiStr+" tarihleri arasında "+arabaModelleri[modelNo]+" vergiler dahil kiralama bedeli: "+total+". Onaylıyor musunuz?(Evet için E, Hayır için H)");
            onay=scan.next();
            if (onay.equals("E"))
            {
                // Verileri dosyaya yaz
                try (PrintWriter writer = new PrintWriter(new FileWriter("musteri_bilgileri.txt", true)))

                {
                    writer.print(isim+"\t"+soyisim+"\t"+telefonNumarasi+"\t"+baslangicTarihiStr+"\t"+bitisTarihiStr+"\t"+ kiralama_suresi + " Ay"+"\t"+(indirimOrani * 100)+total+"\t");
                    System.out.println("İşleminiz tamamlanmıştır.");
                }
                catch (IOException e)
                {
                        System.err.println("Dosya yazma hatası: " + e);
                }
            }
          
            else if (onay.equals("H"))
            {
                System.out.println("İşleminiz iptal ediliyor...");
                System.exit(0); 
            }
            
            else
            {
                System.out.println("Hatalı Giriş!!!");
                System.exit(0);
            }
        
        }
        else
        {
            System.out.print("Araba müsait değil lütfen başka araba seçiniz !");
             System.exit(0);
            
        }
    }
}
