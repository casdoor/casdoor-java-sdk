package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorApplication;
import org.casbin.casdoor.service.CasdoorApplicationService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ApplicationTest {

    @Test
    public void test() {
        // Initialize the CasDoor SDK config
        CasdoorConfig config = TestDefaultConfig.InitConfig();
        // Create a CasDoorApplicationService object
        CasdoorApplicationService applicationService = new CasdoorApplicationService(config);

        String name = TestDefaultConfig.getRandomName("application");
        // Add a new application
        CasdoorApplication application = new CasdoorApplication();
        application.setOwner("Admin");
        application.setName(name);
        application.setCreatedTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        application.setDisplayName(name);
        application.setLogo("https://cdn.casbin.org/img/casdoor-logo_1185x256.png");
        application.setHomepageUrl("https://casdoor.org");
        application.setDescription("Casdoor Website!!!!!!");
        application.setOrganization("casbinEasTWiind");


        try {
            applicationService.addApplication(application);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Get all applications, check if our added application is inside the list
        List<CasdoorApplication> applications = null;
        try {
            applications = applicationService.getApplications();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean found = false;
        for (CasdoorApplication item : applications) {
            if (item.getName().equals(application.getName())) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Added application not found in list");
        }

        // Get the application
        CasdoorApplication retrievedApplication = null;
        try {
            retrievedApplication = applicationService.getApplication(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!retrievedApplication.getName().equals(name)) {
            throw new RuntimeException("Retrieved application does not match added application: " + retrievedApplication.getName() + " != " + name);
        }

        // Update the application
        String updatedDescription = "Updated Casdoor Website";
        retrievedApplication.setDescription(updatedDescription);
        try {
            applicationService.updateApplication(retrievedApplication);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Validate the update
        CasdoorApplication updatedApplication = null;
        try {
            updatedApplication = applicationService.getApplication(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!updatedApplication.getDescription().equals(updatedDescription)) {
            throw new RuntimeException("Failed to update application, description mismatch: " + updatedApplication.getDescription() + " != +" + updatedDescription);
        }

        // Delete the application
        try {
            applicationService.deleteApplication(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Validate the deletion
        CasdoorApplication deletedApplication = null;
        try {
            deletedApplication = applicationService.getApplication(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (deletedApplication != null) {
            throw new RuntimeException("Failed to delete application, it's still retrievable");
        }
    }
}