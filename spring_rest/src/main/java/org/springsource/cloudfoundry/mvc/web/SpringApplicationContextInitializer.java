package org.springsource.cloudfoundry.mvc.web;

import org.apache.log4j.Logger;
import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringApplicationContextInitializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext> {

	static Logger log = Logger.getLogger(SpringApplicationContextInitializer.class);
	
    private CloudEnvironment cloudEnvironment = new CloudEnvironment();

    private boolean isCloudFoundry() {
        return cloudEnvironment.isCloudFoundry();
    }

    @Override
    public void initialize(AnnotationConfigWebApplicationContext applicationContext) {

    	log.debug("----------------STARTING INITIALIZE BATMAN------------------");
        String profile;

        if (isCloudFoundry()) {
            profile = "cloud";
            log.debug("----------------Profile is cloud foundry------------------");
        } else {
            profile = "default";
        }

        applicationContext.getEnvironment().setActiveProfiles(profile);

       /* Class<?>[] configs = {ServicesConfiguration.class, WebMvcConfiguration.class};

        String[] basePkgs = new String[configs.length];
        int i = 0;
        for (Class<?> pkg : configs)
            basePkgs[i++] = pkg.getPackage().getName();

        applicationContext.scan(basePkgs);*/
        applicationContext.register( WebMvcConfiguration.class );
        applicationContext.refresh();
    }
}