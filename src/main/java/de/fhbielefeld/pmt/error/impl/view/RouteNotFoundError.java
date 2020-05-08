package de.fhbielefeld.pmt.error.impl.view;

import javax.servlet.http.HttpServletResponse;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
/**
 * 
 * @author Sebastian Siegmann
 *
 */
@Tag(Tag.DIV)
public class RouteNotFoundError extends Component
       implements HasErrorParameter<NotFoundException> {

	private static final long serialVersionUID = 1L;

	@Override
    public int setErrorParameter(BeforeEnterEvent event,
          ErrorParameter<NotFoundException> parameter) {
        getElement().setText("Error 404 "
                    + event.getLocation().getPath()
                    + " wurde nicht gefunden...");
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
