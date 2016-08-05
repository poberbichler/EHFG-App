package org.ehfg.app.rest;

import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author poberbichler
 * @since 08.2016
 */
@RestController
@RequestMapping("rest/mapcategories")
public class MapCategoryRestEndpoint {
    private final MasterDataFacade masterDataFacade;

    @Autowired
    public MapCategoryRestEndpoint(MasterDataFacade masterDataFacade) {
        this.masterDataFacade = masterDataFacade;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<? extends MapCategoryRepresentation> findAllCategories() {
        return masterDataFacade.findAllMapCategories();
    }
}
