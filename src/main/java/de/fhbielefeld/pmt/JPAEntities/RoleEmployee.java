package JPAEntities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RoleEmployee
 *
 */
@Entity

public class RoleEmployee extends Role implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public RoleEmployee() {
		super();
	}
	
	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("Mitarbeiter")){
			return true;
		} else {
			return super.hasType(type);
		}
	}
	
	public String toString() {
		return "Rolle: Mitarbeiter";
	}
   
}
