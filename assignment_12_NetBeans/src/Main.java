/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class Main {
    public static void main(String[] args) {
        Person john = new Person("John");
        Person kate = new Person("Kate");
        Person bill = new Person("Bill");
        
        PressAgency agency = new PressAgency();
        
        agency.subscribe(Topics.POLITICS, john);
        agency.subscribe(Topics.SPORT, john);
        agency.subscribe(Topics.POLITICS, kate);
        agency.subscribe(Topics.FASHION, kate);
        agency.subscribe(Topics.CELEBRITIES, bill);
        agency.subscribe(Topics.SPORT, bill);
        
        agency.broadcast(Topics.POLITICS,
                "Obama's speech in Washington");
        agency.broadcast(Topics.FASHION,
                "Skirts get shorter this season!");
        agency.broadcast(Topics.SPORT,
                "Real-Atletico 2:1");
        agency.broadcast(Topics.POLITICS,
                "British-French summit in Paris");
        agency.broadcast(Topics.SPORT,
                "Hamilton wins in Australia");
        agency.broadcast(Topics.FASHION,
                "New handbags from Versace!");
        
        System.out.println(bill);
        System.out.println(john);
        System.out.println(kate);
    }
}
