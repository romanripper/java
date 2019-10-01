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
public interface Artist {
	String getNick();
	void setNick(String nick);
	
	Set<Exhibit> getExhibits();
	void setExhibits(Set<Exhibit> exhibits);
	void addExhibit(Exhibit exhibit);
	void removeExhibit(Exhibit exhibit);
	
	Set<IndividualExhibition> getIndividualExhibitions();
	void setIndividualExhibitions(Set<IndividualExhibition> individualExhibitions);
	void addIndividualExhibition(IndividualExhibition individualExhibition);
	void removeIndividualExhibition(IndividualExhibition individualExhibition);
	
}
