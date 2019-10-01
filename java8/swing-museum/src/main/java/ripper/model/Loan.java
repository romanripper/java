package ripper.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loan")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "exhibit_id", nullable = false)
	private Exhibit exhibit;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "museum_id", nullable = false)
	private Museum museum;

	public Loan() {

	}

	public Loan(LocalDate startDate, LocalDate endDate, Exhibit exhibit, Museum museum) {
		setStartDate(startDate);
		setEndDate(endDate);
		setExhibit(exhibit);
		setMuseum(museum);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		if (startDate == null) {
			throw new RuntimeException("Start date can not be null");
		}
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		if (endDate == null) {
			throw new RuntimeException("Start date can not be null");
		}
		this.endDate = endDate;
	}

	public Exhibit getExhibit() {
		return exhibit;
	}

	private void setExhibit(Exhibit exhibit) {
		if (exhibit == null) {
			throw new RuntimeException("Exhibit can not be null");
		}
		this.exhibit = exhibit;
		this.exhibit.addLoan(this);
	}

	public Museum getMuseum() {
		return museum;
	}

	private void setMuseum(Museum museum) {
		if (museum == null) {
			throw new RuntimeException("Exhibit can not be null");
		}
		this.museum = museum;
		this.museum.addLoan(this);
	}

	public void remove() {
		if (exhibit != null) {
			this.exhibit.removeLoan(this);
			this.exhibit = null;
		}
		if (museum != null) {
			this.museum.removeLoan(this);
			this.museum = null;
		}
	}

}
