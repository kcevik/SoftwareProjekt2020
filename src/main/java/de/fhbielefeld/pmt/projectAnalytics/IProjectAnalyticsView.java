package de.fhbielefeld.pmt.projectAnalytics;

import org.jfree.data.general.DefaultPieDataset;

import de.fhbielefeld.pmt.IViewAccessor;
import de.fhbielefeld.pmt.JPAEntities.Project;
import de.fhbielefeld.pmt.projectAnalytics.impl.event.TransportAnalyticsData;

/**
 * @author Kerem Cevik
 *
 */
public interface IProjectAnalyticsView extends IViewAccessor {

	void getData(Project project);

	void onTransportAnalyticsData(TransportAnalyticsData event);

	DefaultPieDataset createCostPieDataset();

	DefaultPieDataset createFullFillmentPieDataset();

	DefaultPieDataset createTimePieDataset();

	int calcFullfillment();

	int calcAvailableHours();

	int calcExtendedHours();

	double calcIncurredCosts();

	void createCharts();

}
