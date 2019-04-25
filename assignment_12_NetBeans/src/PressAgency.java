
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class PressAgency {
    private Map<Topics, List<Person>> subscribers;

    public PressAgency() {
        subscribers = new HashMap<>();
    }
    
    public void subscribe(Topics topics, Person p){
        p.checkSubscribtion(topics);
        if(!(subscribers.containsKey(topics)))
            subscribers.put(topics, new ArrayList<>());
        subscribers.get(topics).add(p);
        
    }
    
    public void broadcast(Topics topics, String news){
        List<Person> persons = subscribers.get(topics);
        for (Person p : persons)
            p.addNews(topics, news);
    }
    
}
