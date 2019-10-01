package ripper.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "TEMPORARY")
public class Temporary extends ExhibitionLengthType {
	
	@ManyToOne
	@JoinColumn(name = "second_curator_id")
	private Person secondCurator;

	public Temporary() {

	}

	public Temporary(Exhibition exhibition, Person curator) {
		super(exhibition);
		setSecondCurator(curator);
	}

	public Person getSecondCurator() {
		return secondCurator;
	}

	public void setSecondCurator(Person curator) {
		if (this.secondCurator != curator) {
			if (curator != null) {
				if (this.secondCurator != null) {
					this.secondCurator.removeTemporaryExhibition(getExhibition());
				}
				this.secondCurator = curator;
				this.secondCurator.addTemporaryExhibition(getExhibition());
			} else {
				if (this.secondCurator != null) {
					this.secondCurator.removeTemporaryExhibition(getExhibition());
				}
				this.secondCurator = curator;
			}
		}
	}

}
