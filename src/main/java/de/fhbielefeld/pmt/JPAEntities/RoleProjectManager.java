package JPAEntities;

import JPAEntities.Role;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RoleProjectManager
 *
 */
@Entity

public class RoleProjectManager extends Role implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public RoleProjectManager() {
		super();
	}
	
	public boolean hasType(String type) {
		if (type.equalsIgnoreCase("Projektleiter")){
			return true;
		} else {
			return super.hasType(type);
		}
	}
	
	public String toString() {
		return "Rolle: Projektleiter";
	}
   
}
