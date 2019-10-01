package ripper.util;

import java.util.ArrayList;
import java.util.List;

import ripper.model.Exhibit;

public class Utils {
	
	
	/** 
	 * Returns list of exhibit's IDs from provided list of exhibits
	 * 
	 * @param exhibits
	 * @return IDs of exhibits
	 */
	public static List<Long> getIdsFromExhibits(List<Exhibit> exhibits){
		List<Long> ids = new ArrayList<>();
		
		exhibits.stream().forEach(exhibit -> ids.add(exhibit.getId()));
		
		return ids;
	}
}
