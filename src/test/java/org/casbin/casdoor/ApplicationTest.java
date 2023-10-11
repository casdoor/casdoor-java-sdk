package org.casbin.casdoor;

import org.casbin.casdoor.config.CasdoorConfig;
import org.casbin.casdoor.entity.CasdoorApplication;
import org.casbin.casdoor.entity.CasdoorUser;
import org.casbin.casdoor.service.CasdoorApplicationService;
import org.casbin.casdoor.service.CasdoorUserService;
import org.casbin.casdoor.support.ConfigFactory;
import org.casbin.casdoor.util.http.CasdoorResponse;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ApplicationTest {

    public static String getRandomCode(int length) {
        byte[] stdNums = "0123456789".getBytes();
        byte[] result = new byte[length];
        Random r = new Random(System.nanoTime());
        for (int i = 0; i < length; i++) {
            result[i] = stdNums[r.nextInt(stdNums.length)];
        }
        return new String(result);
    }

    public static String getRandomName(String prefix) {
        return prefix + "_" + getRandomCode(6);
    }

    private static final String TEST_CASDOOR_ENDPOINT = "https://demo.casdoor.com";
    private static final String TEST_CLIENT_ID = "294b09fbc17f95daf2fe";
    private static final String TEST_CLIENT_SECRET = "dd8982f7046ccba1bbd7851d5c1ece4e52bf039d";
    private static final String TEST_CASDOOR_ORGANIZATION = "casbin";
    private static final String TEST_CASDOOR_APPLICATION = "app-vue-python-example";
    private static final String TEST_CASDOOR_CERTIFICATE =
            "MIIE+TCCAuGgAwIBAgIDAeJAMA0GCSqGSIb3DQEBCwUAMDYxHTAbBgNVBAoTFENh\n" +
                    "c2Rvb3IgT3JnYW5pemF0aW9uMRUwEwYDVQQDEwxDYXNkb29yIENlcnQwHhcNMjEx\n" +
                    "MDE1MDgxMTUyWhcNNDExMDE1MDgxMTUyWjA2MR0wGwYDVQQKExRDYXNkb29yIE9y\n" +
                    "Z2FuaXphdGlvbjEVMBMGA1UEAxMMQ2FzZG9vciBDZXJ0MIICIjANBgkqhkiG9w0B\n" +
                    "AQEFAAOCAg8AMIICCgKCAgEAsInpb5E1/ym0f1RfSDSSE8IR7y+lw+RJjI74e5ej\n" +
                    "rq4b8zMYk7HeHCyZr/hmNEwEVXnhXu1P0mBeQ5ypp/QGo8vgEmjAETNmzkI1NjOQ\n" +
                    "CjCYwUrasO/f/MnI1C0j13vx6mV1kHZjSrKsMhYY1vaxTEP3+VB8Hjg3MHFWrb07\n" +
                    "uvFMCJe5W8+0rKErZCKTR8+9VB3janeBz//zQePFVh79bFZate/hLirPK0Go9P1g\n" +
                    "OvwIoC1A3sarHTP4Qm/LQRt0rHqZFybdySpyWAQvhNaDFE7mTstRSBb/wUjNCUBD\n" +
                    "PTSLVjC04WllSf6Nkfx0Z7KvmbPstSj+btvcqsvRAGtvdsB9h62Kptjs1Yn7GAuo\n" +
                    "I3qt/4zoKbiURYxkQJXIvwCQsEftUuk5ew5zuPSlDRLoLByQTLbx0JqLAFNfW3g/\n" +
                    "pzSDjgd/60d6HTmvbZni4SmjdyFhXCDb1Kn7N+xTojnfaNkwep2REV+RMc0fx4Gu\n" +
                    "hRsnLsmkmUDeyIZ9aBL9oj11YEQfM2JZEq+RVtUx+wB4y8K/tD1bcY+IfnG5rBpw\n" +
                    "IDpS262boq4SRSvb3Z7bB0w4ZxvOfJ/1VLoRftjPbLIf0bhfr/AeZMHpIKOXvfz4\n" +
                    "yE+hqzi68wdF0VR9xYc/RbSAf7323OsjYnjjEgInUtRohnRgCpjIk/Mt2Kt84Kb0\n" +
                    "wn8CAwEAAaMQMA4wDAYDVR0TAQH/BAIwADANBgkqhkiG9w0BAQsFAAOCAgEAn2lf\n" +
                    "DKkLX+F1vKRO/5gJ+Plr8P5NKuQkmwH97b8CS2gS1phDyNgIc4/LSdzuf4Awe6ve\n" +
                    "C06lVdWSIis8UPUPdjmT2uMPSNjwLxG3QsrimMURNwFlLTfRem/heJe0Zgur9J1M\n" +
                    "8haawdSdJjH2RgmFoDeE2r8NVRfhbR8KnCO1ddTJKuS1N0/irHz21W4jt4rxzCvl\n" +
                    "2nR42Fybap3O/g2JXMhNNROwZmNjgpsF7XVENCSuFO1jTywLaqjuXCg54IL7XVLG\n" +
                    "omKNNNcc8h1FCeKj/nnbGMhodnFWKDTsJcbNmcOPNHo6ixzqMy/Hqc+mWYv7maAG\n" +
                    "Jtevs3qgMZ8F9Qzr3HpUc6R3ZYYWDY/xxPisuKftOPZgtH979XC4mdf0WPnOBLqL\n" +
                    "2DJ1zaBmjiGJolvb7XNVKcUfDXYw85ZTZQ5b9clI4e+6bmyWqQItlwt+Ati/uFEV\n" +
                    "XzCj70B4lALX6xau1kLEpV9O1GERizYRz5P9NJNA7KoO5AVMp9w0DQTkt+LbXnZE\n" +
                    "HHnWKy8xHQKZF9sR7YBPGLs/Ac6tviv5Ua15OgJ/8dLRZ/veyFfGo2yZsI+hKVU5\n" +
                    "nCCJHBcAyFnm1hdvdwEdH33jDBjNB6ciotJZrf/3VYaIWSalADosHAgMWfXuWP+h\n" +
                    "8XKXmzlxuHbTMQYtZPDgspS5aK+S4Q9wb8RRAYo=";

    @Test
    public void test2(){
        // Initialize the Casdoor SDK config
        CasdoorConfig config = new CasdoorConfig(TEST_CASDOOR_ENDPOINT, TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_CASDOOR_CERTIFICATE, TEST_CASDOOR_ORGANIZATION, TEST_CASDOOR_APPLICATION);
        // Create a CasdoorApplicationService object
        CasdoorUserService userService = new CasdoorUserService(config);
        // Add a new application
        String name = getRandomName("application");
        CasdoorUser user = new CasdoorUser();
        user.setOwner("casbin");
        user.setName(name);
        user.setCreatedTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        user.setDisplayName(name);
        user.setAvatar("https://cdn.casbin.org/img/casdoor-logo_1185x256.png");
        user.setEmail("https://casdoor.org");
        user.setPhone("Casdoor Website!!!!!!");

        try {
            userService.addUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        // Get all applications, check if our added application is inside the list
        List<CasdoorUser> users = null;
        try {
            users = userService.getUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean found = false;
        for (CasdoorUser item : users) {
            if (item.getName().equals(user.getName())) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Added application not found in list");
        }

        // Get the application
        CasdoorUser retrievedApplication = null;
        try {
            retrievedApplication = userService.getUser(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!retrievedApplication.getName().equals(name)) {
            System.out.printf("Retrieved application does not match added application: %s != %s", retrievedApplication.getName(), name);
        }

        // Update the application
        String updatedPhone = "Updated Casdoor Website";
        retrievedApplication.setPhone(updatedPhone);
        try {
            userService.updateUser(retrievedApplication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Validate the update
        CasdoorUser updatedApplication = null;
        try {
            updatedApplication = userService.getUser(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!updatedApplication.getPhone().equals(updatedPhone)) {
            System.out.printf("Failed to update application, phone mismatch: %s != %s", updatedApplication.getPhone(), updatedPhone);
        }

        // Delete the application
        try {
            userService.deleteUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Validate the deletion
        CasdoorUser deletedApplication = null;
        try {
            deletedApplication = userService.getUser(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (deletedApplication != null) {
            System.out.println("Failed to delete application, it's still retrievable");
        }
    }



    @Test
    public void test(){
        // Initialize the Casdoor SDK config
        CasdoorConfig config = new CasdoorConfig(TEST_CASDOOR_ENDPOINT, TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_CASDOOR_CERTIFICATE, TEST_CASDOOR_ORGANIZATION, TEST_CASDOOR_APPLICATION);
        // Create a CasdoorApplicationService object
        CasdoorApplicationService applicationService = new CasdoorApplicationService(config);
        // Add a new application
        String name = getRandomName("application");
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
            throw new RuntimeException(e);
        }



        // Get all applications, check if our added application is inside the list
        List<CasdoorApplication> applications = null;
        try {
            applications = applicationService.getApplications();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean found = false;
        for (CasdoorApplication item : applications) {
            if (item.getName().equals(application.getName())) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Added application not found in list");
        }

        // Get the application
        CasdoorApplication retrievedApplication = null;
        try {
            retrievedApplication = applicationService.getApplication(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!retrievedApplication.getName().equals(name)) {
            System.out.printf("Retrieved application does not match added application: %s != %s", retrievedApplication.getName(), name);
        }

        // Update the application
        String updatedDescription = "Updated Casdoor Website";
        retrievedApplication.setDescription(updatedDescription);
        try {
            applicationService.updateApplication(retrievedApplication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Validate the update
        CasdoorApplication updatedApplication = null;
        try {
            updatedApplication = applicationService.getApplication(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!updatedApplication.getDescription().equals(updatedDescription)) {
            System.out.printf("Failed to update application, description mismatch: %s != %s", updatedApplication.getDescription(), updatedDescription);
        }

        // Delete the application
        try {
            applicationService.deleteApplication(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Validate the deletion
        CasdoorApplication deletedApplication = null;
        try {
            deletedApplication = applicationService.getApplication(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (deletedApplication != null) {
            System.out.println("Failed to delete application, it's still retrievable");
        }
    }
}