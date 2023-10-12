package org.casbin.casdoor;
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.Application;
import org.casbin.casdoor.service.ApplicationService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    private ApplicationService applicationService = new ApplicationService(TestDefaultConfig.InitConfig());

    @Test
    public void testApplication() {
        String name = TestDefaultConfig.getRandomName("application");

        // Add a new object
        Application application = new Application(
                "Admin",
                name,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                name,
                "https://cdn.casbin.org/img/casdoor-logo_1185x256.png",
                "https://casdoor.org",
                "Casdoor Website",
                "casbin"
        );
        assertDoesNotThrow(() -> applicationService.addApplication(application));

        // Get all objects, check if our added object is inside the list
        List<Application> applications;
        try {
            applications = applicationService.getApplications();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = applications.stream().anyMatch(item -> item.getName().equals(name));
        assertTrue(found,"Added object not found in list");

        // Get the object
        Application retrievedApplication;
        try {
            retrievedApplication = applicationService.getApplication(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedApplication.getName(), "Retrieved object does not match added object");

        // Update the object
        String updatedDescription = "Updated Casdoor Website";
        retrievedApplication.setDescription(updatedDescription);
        assertDoesNotThrow(() -> applicationService.updateApplication(retrievedApplication));

        // Validate the update
        Application updatedApplication;
        try {
            updatedApplication = applicationService.getApplication(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDescription, updatedApplication.getDescription(), "Failed to update object, description mismatch");

        // Delete the object
        assertDoesNotThrow(() -> applicationService.deleteApplication(name));

        // Validate the deletion
        Application deletedApplication;
        try {
            deletedApplication = applicationService.getApplication(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedApplication,"Failed to delete object, it's still retrievable");
    }
}
