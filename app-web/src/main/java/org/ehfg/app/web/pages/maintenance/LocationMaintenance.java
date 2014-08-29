package org.ehfg.app.web.pages.maintenance;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Cached;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.ActionLink;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.ehfg.app.base.LocationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.program.ProgramFacade;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 19.07.2014
 */
public class LocationMaintenance {
	@Component
	private BootstrapLayout layout;

	@Component(parameters = { "source=locations", "add=xCoordinate,yCoordinate,showOnMap,delete", "row=currentLocation" })
	private Grid locationGrid;

	@Component
	private Form inputForm;
	
	@Component(parameters = {"value=newLocation.name", "model=availableNames", "blankOption=NEVER"})
	private Select locationName;

	@Component(parameters = { "value=newLocation.coordinate.xValue" })
	private TextField xCoordinate;

	@Component(parameters = { "value=newLocation.coordinate.yValue" })
	private TextField yCoordinate;

	@Component
	private Submit submitInputForm;

	@Component(parameters = { "context=currentLocation.id" })
	private ActionLink deleteLink;

	@Property
	private LocationDTO newLocation;

	@Property
	private LocationDTO currentLocation;
	
	@Inject
	private MasterDataFacade masterDataFacade;
	
	@Inject
	private ProgramFacade programFacade;
	
	public void onPrepare() {
		newLocation = new LocationDTO();
	}

	@Cached
	public List<LocationDTO> getLocations() {
		return masterDataFacade.findAllLocation();
	}
	
	@Cached
	public List<String> getAvailableNames() {
		List<String> availableLocations = programFacade.findAvailableLocations();
		List<LocationDTO> alreadyAssignedLocations = getLocations();
		
		for (LocationDTO location : alreadyAssignedLocations) {
			availableLocations.remove(location.getName());
		}
		
		Collections.sort(availableLocations);
		return availableLocations;
	}

	@OnEvent(component = "inputForm", value = EventConstants.SUCCESS)
	public void onSuccessFromInputForm() {
		masterDataFacade.saveLocation(newLocation);
	}
	
	@OnEvent(component = "deleteLink", value = EventConstants.ACTION)
	public void onActionFromDeleteLink(Long locationId) {
		masterDataFacade.deleteLocation(locationId);
	}
	
	Object onException(Exception e) {
		inputForm.recordError(e.getMessage());
		return this;
	}

	public String getMapLink() {
		final Double yValue = currentLocation.getCoordinate().getyValue();
		final Double xValue = currentLocation.getCoordinate().getxValue();
		return String.format("http://maps.google.com/maps?q=%s,%s&ll=%s,%s&z=17", xValue, yValue, xValue, yValue);
	}
}
