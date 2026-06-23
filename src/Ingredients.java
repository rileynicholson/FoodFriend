import java.time.LocalDate;

public class Ingredients {
	private String name; 
	private LocalDate expirationDate;
	
	public Ingredients(String name, LocalDate expirationDate) {
		this.name = name;
		this.expirationDate = expirationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
}