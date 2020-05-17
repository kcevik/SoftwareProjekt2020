package de.fhbielefeld.pmt.projectActivity.impl.event;

import de.fhbielefeld.pmt.JPAEntities.ProjectActivity;
import de.fhbielefeld.pmt.projectActivity.IProjectActivityView;

/**
 * 
 * @author David Bistron
 *
 */
public class SendProjectActivityToDBEvent {

		private ProjectActivity selectedActivity;
		
		public SendProjectActivityToDBEvent(IProjectActivityView view, ProjectActivity selectedActivity) {
			super();
			this.selectedActivity = selectedActivity;
			
		}
		
		public ProjectActivity getSelectedProjectActivity() {
			return selectedActivity;
			
		}
		
		public void setSelectedProjectActivity(ProjectActivity selectedActivity) {
			this.selectedActivity = selectedActivity;
		}
}
