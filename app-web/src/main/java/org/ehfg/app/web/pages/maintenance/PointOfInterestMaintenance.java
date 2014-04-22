package org.ehfg.app.web.pages.maintenance;

import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextArea;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.ehfg.app.api.dto.PointOfInterestDTO;
import org.ehfg.app.api.facade.MasterDataFacade;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 12.04.2014
 */
public class PointOfInterestMaintenance {
	@Component
	private BootstrapLayout layout;

	@Property
	@Component(parameters = { "source=pointList", "row=currentPoint", "add=edit", "exclude=id" })
	private Grid pointGrid;

	@Component(parameters = { "context=currentPoint.id", "zone=inputZone" })
	private ActionLink editPointLink;

	@Component(parameters = { "zone=inputZone" })
	private ActionLink createPoint;

	@Component(parameters = { "context=currentPoint.id" })
	private ActionLink removePoint;

	@Component
	private Zone inputZone;

	@Component
	private Form inputForm;

	@Component(parameters = { "value=editPoint.name" })
	private TextField name;

	@Component(parameters = { "value=editPoint.address" })
	private TextField address;

	@Component(parameters = { "value=editPoint.description" })
	private TextArea description;
	
	@Component(parameters = { "value=editPoint.website"})
	private TextField website;
	
	@Component(parameters = { "value=editPoint.contact"})
	private TextField contact;

	@Component(parameters = { "value=editPoint.coordinate.xValue" })
	private TextField xCoordinate;

	@Component(parameters = { "value=editPoint.coordinate.yValue" })
	private TextField yCoordinate;
	
	@Component
	private Submit formSubmit;

	@Inject
	private MasterDataFacade masterDataFacade;

	@Property
	private PointOfInterestDTO currentPoint;

	@Persist
	@Property
	private PointOfInterestDTO editPoint;

	void beginRender() {
		editPoint = null;
	}

	@Cached
	public List<PointOfInterestDTO> getPointList() {
		return masterDataFacade.findAllPointsOfInterest();
	}

	@OnEvent(component = "inputForm", value = EventConstants.ACTION)
	Object onSuccessFromInputForm() {
		masterDataFacade.savePointOfInterest(editPoint);
		editPoint = null;
		return this;
	}

	@OnEvent(component = "createPoint", value = EventConstants.ACTION)
	Object onActionFromCreatePoint() {
		editPoint = new PointOfInterestDTO();
		return inputZone.getBody();
	}

	@OnEvent(component = "editPointLink", value = EventConstants.ACTION)
	Object onActionFromEditPointLink(Long id) {
		final List<PointOfInterestDTO> pointList = getPointList();
		for (final PointOfInterestDTO point : pointList) {
			if (point.getId().equals(id)) {
				editPoint = point;
				break;
			}
		}
		
		return inputZone;
	}

	@OnEvent(component = "removePoint", value = EventConstants.ACTION)
	void onActionFromRemovePoint(Long id) {
		masterDataFacade.removePoint(id);
	}

	public boolean getEditPointAvailable() {
		return editPoint != null;
	}
}
