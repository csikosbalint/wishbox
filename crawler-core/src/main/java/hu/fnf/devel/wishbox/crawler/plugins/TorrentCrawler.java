package hu.fnf.devel.wishbox.crawler.plugins;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.ConfigurationPolicy;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Service;

@Service( TorrentCrawler.class )
@Component(
        policy = ConfigurationPolicy.OPTIONAL
)
@Properties(
        {
//                @Property( name = SimpleWSClientConstants.MODE, value = "Simple" )
        }
)
public class TorrentCrawler {
}
