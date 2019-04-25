
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author admin
 */
public class Person {
    public static int count = 0;
    private int id;
    private String name;
    private Map<Topics, List<String>> subscriptions;

    public Person(String name) {
        id = ++count;
        this.name = name;
        subscriptions = new HashMap<>();
    }
    
    public void checkSubscribtion(Topics topics){
        if(!(subscriptions.containsKey(topics)))
            subscriptions.put(topics, new ArrayList<>());
    }
    
    public void addNews(Topics topics, String news){
        subscriptions.get(topics).add(news);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + " (id=" + id + ") \n");
        for (Map.Entry<Topics, List<String>> entry : subscriptions.entrySet()) {
            List<String> news = entry.getValue();
            if (news.size() > 0) {
                sb.append(entry.getKey() + ": " + news.size() + " news \n");
                for (String s : news) {
                    sb.append("\t" + s + "\n");
                }
            }
            else {
                sb.append(entry.getKey() + ": no news \n");
            }
        }

        return sb.toString();
    }

}
