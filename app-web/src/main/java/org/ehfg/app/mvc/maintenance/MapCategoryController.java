package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.base.MapCategoryDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

/**
 * @author poberbichler
 * @since 08.2016
 */
@Controller
@RequestMapping("maintenance/mapcategory")
public class MapCategoryController {
    private final MasterDataFacade masterDataFacade;

    @Autowired
    public MapCategoryController(MasterDataFacade masterDataFacade) {
        this.masterDataFacade = masterDataFacade;
    }

    @RequestMapping(value = {"", "{id}"}, method = RequestMethod.GET)
    public ModelAndView getPage(@PathVariable("id") Optional<String> categoryId) {
        ModelAndView view = new ModelAndView("mapcategory");

        view.addObject("activePage", "mapcategory");
        Collection<MapCategoryDTO> allCategories = masterDataFacade.findAllMapCategories();
        view.addObject("categories", allCategories);

        MapCategoryDTO editCategory = allCategories.stream()
                .filter(c -> c.getId().equals(categoryId.orElseGet(String::new)))
                .findFirst()
                .orElseGet(MapCategoryDTO::new);

        view.addObject("editCategory", editCategory);
        return view;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveCategory(@Valid MapCategoryDTO mapCategoryDTO) {
        masterDataFacade.saveMapCategory(mapCategoryDTO);
        return "redirect:/maintenance/mapcategory";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable("id") String categoryId) {
        masterDataFacade.deleteMapCategory(categoryId);
        return "redirect:/maintenance/mapcategory";
    }
}
