package org.ehfg.app.mvc.export;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 10.2015
 */
@Controller
@RequestMapping("export")
public class ExportController {
    @RequestMapping("tweets")
    public ModelAndView exportTweets() {
        return new ModelAndView("tweetExport");
    }
}
