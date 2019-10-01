package ripper.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "length_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ExhibitionLengthType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@OneToOne(mappedBy = "exhibitionLengthType")
	private Exhibition exhibition;

	public ExhibitionLengthType() {

	}

	public ExhibitionLengthType(Exhibition exhibition) {
		setExhibition(exhibition);
	}
	
	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Exhibition getExhibition() {
		return exhibition;
	}

	private void setExhibition(Exhibition exhibition) {
		this.exhibition = exhibition;
	}

}
