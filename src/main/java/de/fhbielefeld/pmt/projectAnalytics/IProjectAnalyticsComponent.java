package de.fhbielefeld.pmt.projectAnalytics;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.UnsupportedViewTypeException;
import de.fhbielefeld.pmt.projectAnalytics.impl.event.GetAnalyticsData;

/**
 * @author Kerem Cevik
 *
 */
public interface IProjectAnalyticsComponent extends IViewAccessor {

	void onGetAnalyticsData(GetAnalyticsData event);

}
