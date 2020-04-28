package JPAEntities;


import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RoleCEO
 *
 */
@Entity

public class RoleCEO extends Role implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public RoleCEO() {
		super();
	}
	
	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("Geschaeftsfuehrer")){
			return true;
		} else {
			return super.hasType(type);
		}
	}
	
	public String toString() {
		return "Rolle: Geschaeftsfuehrer";
	}
   
}
