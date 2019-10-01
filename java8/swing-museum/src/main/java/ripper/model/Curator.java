/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ripper.model;

import java.util.Set;

/**
 *
 * @author admin
 */
public interface Curator {
    Double getSalary();
    void setSalary(Double salary);
    
    Set<Exhibition> getExhibitions();
    void setExhibitions(Set<Exhibition> exhibitions);
    void addExhibition(Exhibition exhibition);
    void removeExhibition(Exhibition exhibition);
    
    Set<Exhibition> getTemporaryExhibitions();
    void setTemporaryExhibitions(Set<Exhibition> temporaryExhibitions);
    void addTemporaryExhibition(Exhibition temporaryExhibition);
    void removeTemporaryExhibition(Exhibition temporaryExhibition);
}
