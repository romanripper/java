package ripper.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PERMANENT")
public class Permanent extends ExhibitionLengthType {
	
	@Column(name = "electronic_catalogue_url")
	private String electronicCatalogueURL;

	public Permanent() {
	}

	public Permanent(Exhibition exhibition, String internatCatalogueURL) {
		super(exhibition);
		setElectronicCatalogueURL(internatCatalogueURL);
	}

	public String getElectronicCatalogueURL() {
		return electronicCatalogueURL;
	}

	public void setElectronicCatalogueURL(String internatCatalogueURL) {
		if (internatCatalogueURL == null) {
			throw new RuntimeException("Internet catalogue URL can not be null");
		}
		this.electronicCatalogueURL = internatCatalogueURL;
	}

}
