package ripper.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "museum")
public class Museum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "museum", cascade = CascadeType.ALL)
	private Set<Loan> loans = new HashSet<>();

	public Museum() {

	}

	public Museum(String name) {
		setName(name);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new RuntimeException("Name can not be null");
		}
		this.name = name;
	}

	public Set<Loan> getLoans() {
		return new HashSet<>(loans);
	}

	public void setLoans(Set<Loan> loans) {
		this.loans = loans;
	}

	public void addLoan(Loan loan) {
		if (!(loans.contains(loan))) {
			if (loan == null) {
				throw new RuntimeException("Loan can not be null");
			}
			if (loan.getMuseum() != this) {
				throw new RuntimeException("Loan of different museum");
			}
			loans.add(loan);
		}
	}

	public void removeLoan(Loan loan) {
		if (loans.contains(loan)) {
			loans.remove(loan);
			loan.remove();
		}
	}

}
