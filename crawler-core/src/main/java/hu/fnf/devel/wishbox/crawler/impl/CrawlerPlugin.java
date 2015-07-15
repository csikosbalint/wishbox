package hu.fnf.devel.wishbox.crawler.impl;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;

@Service( hu.fnf.devel.wishbox.crawler.Crawler.class )
@Component(
        policy = ConfigurationPolicy.OPTIONAL
)
@Properties(
        {
//                @Property( name = SimpleWSClientConstants.MODE, value = "Simple" )
        }
)
public class CrawlerPlugin implements hu.fnf.devel.wishbox.crawler.Crawler {
    @Activate
    public void activate( ComponentContext context ) {

    }

    @Modified
    public void modified( ComponentContext context ) {

    }
}
