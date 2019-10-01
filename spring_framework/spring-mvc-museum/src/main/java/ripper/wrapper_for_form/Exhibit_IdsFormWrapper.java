package ripper.wrapper_for_form;

import java.util.ArrayList;
import java.util.List;

public class Exhibit_IdsFormWrapper {
	private List<Long> exhibit_ids;

	public Exhibit_IdsFormWrapper() {
		exhibit_ids = new ArrayList<>();
	}

	public List<Long> getExhibit_ids() {
		return exhibit_ids;
	}

	public void setExhibit_ids(List<Long> exhibit_ids) {
		this.exhibit_ids = exhibit_ids;
	}
	
}
