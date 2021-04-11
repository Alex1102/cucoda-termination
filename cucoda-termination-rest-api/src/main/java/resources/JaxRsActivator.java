package resources;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class is used to register all rest-services or classes. Default builtin true. Don't register servlet any more.
 */
@ApplicationPath("/")
//@SecurityScheme(securitySchemeName = "basicAuth", type = HTTP, scheme = "basic")
public class JaxRsActivator extends Application {
}
