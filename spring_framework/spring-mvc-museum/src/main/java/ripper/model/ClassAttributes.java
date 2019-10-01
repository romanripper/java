package ripper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "class_attributes")
public class ClassAttributes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "min_ticket_price", nullable = false)
	private double minTicketPrice;

	public ClassAttributes() {
	}

	public ClassAttributes(double minTicketPrice) {
		setMinTicketPrice(minTicketPrice);
	}

	@PrePersist
	private void saveMinTicketPrice() {
		setMinTicketPrice(Exhibition.getMinTicketPrice());
	}

	@PostLoad
	private void retreiveMinTicketPrice() {
		Exhibition.setMinTicketPrice(minTicketPrice);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getMinTicketPrice() {
		return minTicketPrice;
	}

	public void setMinTicketPrice(double minTicketPrice) {
		this.minTicketPrice = minTicketPrice;
	}

}
