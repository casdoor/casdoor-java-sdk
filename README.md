# Casdoor Java SDK

<p align="center">
  <a href="#badge">
    <img alt="semantic-release" src="https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg">
  </a>
  <a href="https://github.com/casdoor/casdoor-java-sdk/actions/workflows/maven-ci.yml">
    <img alt="GitHub Workflow Status (branch)" src="https://img.shields.io/github/actions/workflow/status/casdoor/casdoor-java-sdk/maven-ci.yml?branch=master">
  </a>
  <a href="https://github.com/casdoor/casdoor-java-sdk/releases/latest">
    <img alt="GitHub Release" src="https://img.shields.io/github/v/release/casdoor/casdoor-java-sdk.svg">
  </a>
  <a href="https://mvnrepository.com/artifact/org.casbin/casdoor-java-sdk/latest">
    <img alt="Maven Central" src="https://img.shields.io/maven-central/v/org.casbin/casdoor-java-sdk.svg">
  </a>
  <a href="https://www.javadoc.io/doc/org.casbin/casdoor-java-sdk">
    <img alt="Javadocs" src="https://www.javadoc.io/badge/org.casbin/casdoor-java-sdk.svg">
  </a>
</p>

<p align="center">
  <a href="https://github.com/casdoor/casdoor-java-sdk/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/casdoor/casdoor-java-sdk?style=flat-square" alt="license">
  </a>
  <a href="https://github.com/casdoor/casdoor-java-sdk/issues">
    <img alt="GitHub issues" src="https://img.shields.io/github/issues/casdoor/casdoor-java-sdk?style=flat-square">
  </a>
  <a href="#">
    <img alt="GitHub stars" src="https://img.shields.io/github/stars/casdoor/casdoor-java-sdk?style=flat-square">
  </a>
  <a href="https://github.com/casdoor/casdoor-java-sdk/network">
    <img alt="GitHub forks" src="https://img.shields.io/github/forks/casdoor/casdoor-java-sdk?style=flat-square">
  </a>
  <a href="https://discord.gg/5rPsrAzK7S">
    <img alt="Casdoor" src="https://img.shields.io/discord/1022748306096537660?style=flat-square&logo=discord&label=discord&color=5865F2">
  </a>
</p>

Casdoor Java SDK is the official Java client library for [Casdoor](https://casdoor.ai/). It lets your Java backend sign users in with Casdoor (OAuth 2.0 / OIDC), verify the JWT tokens issued by Casdoor, and manage users, organizations, applications, roles, permissions and all the other Casdoor objects through the Casdoor APIs.

The SDK has the same features as [casdoor-go-sdk](https://github.com/casdoor/casdoor-go-sdk). For Spring Boot, use [casdoor-spring-boot-starter](https://github.com/casdoor/casdoor-spring-boot-starter), which is built on this SDK.

## 📋 Table of Contents

- [Features](#-features)
- [Installation](#-installation)
- [Quick Start](#-quick-start)
- [Configuration](#️-configuration)
- [Authentication](#-authentication)
- [Resource Management](#-resource-management)
- [API Reference](#-api-reference)
- [Development](#-development)
- [Documentation](#-documentation)
- [License](#-license)

## ✨ Features

- **OAuth 2.0 Authentication**: authorization code, password and refresh token grants, token introspection, SSO logout, OIDC logout URL
- **JWT Verification**: verify the tokens signed by Casdoor (RSA and EC algorithms)
- **Calling APIs as the User**: `Config.withAccessToken()` calls the APIs with the user's own permissions
- **User Management**: CRUD, lookup by email / phone / user ID, pagination, password check and change
- **Organization & Application Management**: organizations, applications, groups, certificates, providers, LDAP
- **Authorization**: roles, permissions, models, adapters, enforcers, policies, `enforce()` and `batchEnforce()`
- **Billing**: products, orders, payments, plans, pricings, subscriptions and transactions
- **Messaging**: send emails, SMS and notifications
- **Multi-Factor Authentication (MFA)**: TOTP, email and SMS MFA setup
- **Other Objects**: sessions, tokens, webhooks, syncers, invitations, resources (file upload) and records
- **Java 8+**

## 📦 Installation

Maven:

```xml
<dependency>
    <groupId>org.casbin</groupId>
    <artifactId>casdoor-java-sdk</artifactId>
    <version>${casdoor-java-sdk.version}</version>
</dependency>
```

Gradle:

```groovy
implementation 'org.casbin:casdoor-java-sdk:<version>'
```

The latest version is shown by the Maven Central badge above.

## 🚀 Quick Start

```java
import org.casbin.casdoor.config.Config;
import org.casbin.casdoor.entity.User;
import org.casbin.casdoor.service.UserService;

Config config = new Config(
        "http://localhost:8000",   // endpoint
        "<client-id>",             // clientId
        "<client-secret>",         // clientSecret
        "<certificate>",           // x509 certificate of the application's cert
        "my-organization",         // organizationName
        "my-application"           // applicationName
);

UserService userService = new UserService(config);
List<User> users = userService.getUsers();
System.out.println("Found " + users.size() + " users");
```

## ⚙️ Configuration

### Configuration Parameters

| Parameter        | Required | Description                                                                   |
|------------------|----------|-------------------------------------------------------------------------------|
| endpoint         | Yes      | Casdoor server URL, such as `http://localhost:8000`                           |
| clientId         | Yes      | Client ID of the Casdoor application                                          |
| clientSecret     | Yes      | Client secret of the Casdoor application                                      |
| certificate      | Yes      | x509 certificate (PEM) of the application's cert, used to verify JWT tokens   |
| organizationName | Yes      | Name of the Casdoor organization                                              |
| applicationName  | Yes      | Name of the Casdoor application                                               |

### Getting the Configuration from Casdoor

1. **endpoint**: the URL of your Casdoor server
2. **clientId** and **clientSecret**: the application's edit page in the Casdoor admin panel
3. **certificate**: the certificate of the cert selected in the application's "Cert" field (Certs page → the cert → "Certificate")
4. **organizationName**: the organization that owns your users
5. **applicationName**: the name of your application

### Services

The APIs are grouped into services, create the ones you need with the config:

```java
AuthService authService = new AuthService(config);
UserService userService = new UserService(config);
RoleService roleService = new RoleService(config);
```

The services are stateless and thread-safe, so they can be created once and shared.

### Custom HTTP Headers and HTTP Client

```java
// Added to all the API requests, e.g. for localized error messages
config.customHeaders.put("Accept-Language", "de");

// Use your own OkHttpClient for timeouts, proxies or self-signed certificates
HttpClient.setHttpClient(new OkHttpClient.Builder()
        .callTimeout(Duration.ofSeconds(30))
        .build());
```

The `Authorization` header is always managed by the SDK.

### Errors

The API methods throw `org.casbin.casdoor.exception.Exception` (a `RuntimeException`) with Casdoor's error message when Casdoor returns an error. `getXxx(name)` returns `null` when the object doesn't exist. `addXxx()`, `updateXxx()` and `deleteXxx()` return Casdoor's response, whose `getData()` is `"Affected"` when the object is changed.

## 🔐 Authentication

### OAuth 2.0 Flow

#### Step 1: Redirect the User to Casdoor

```java
String signinUrl = authService.getSigninUrl("http://localhost:8080/callback", state);
```

`getSignupUrl()`, `getUserProfileUrl(username, accessToken)` and `getMyProfileUrl(accessToken)` build the URLs of the other Casdoor pages.

#### Step 2: Handle the Callback

Casdoor redirects back to your application with `code` and `state`, e.g. `http://localhost:8080/callback?code=xxx&state=yyy`. Exchange the code for the access token and verify it:

```java
String accessToken = authService.getOAuthToken(code, state);
User user = authService.parseJwtToken(accessToken);

// e.g. save the user in the session
request.getSession().setAttribute("user", user);
```

`parseJwtToken()` verifies the signature and the expiration of the token with the certificate, and throws `AuthException` when the token is invalid.

### Password Grant, Impersonation and Token Refresh

```java
// Resource Owner Password Credentials grant, the application must enable the "Password" grant type
OAuthToken token = authService.getOAuthTokenByPassword("alice", "password");

// Sign in as any user of the organization with the organization's master password
OAuthToken token2 = authService.impersonateUser("alice", "<master password>");

OAuthToken refreshed = authService.refreshOAuthToken(token.refreshToken);

java.util.Map<String, Object> result = new TokenService(config).introspectToken(token.accessToken, "access_token");
boolean active = (Boolean) result.get("active");
```

### Calling APIs With the User's Access Token

By default, the SDK calls the Casdoor APIs as the application itself: it authenticates with the client ID and client secret, so the calls have the application's (admin) permissions.

To call the APIs on behalf of the signed-in user instead, create the services with `config.withAccessToken()`. It returns a copy of the config that makes the services send the `Authorization: Bearer <access_token>` header, so Casdoor treats the requests as being made by that user and the user's own permissions apply:

```java
Config userConfig = config.withAccessToken(accessToken);

// "Who am I"
User account = new UserService(userConfig).getAccount();

// Any other API can be called in the same way
List<User> users = new UserService(userConfig).getUsers();
```

The original config is not changed, so it's safe to create one such config per incoming HTTP request.

**Note**: a non-admin user can only access their own data. If an API throws a permission error, the user simply isn't allowed to call it — use the application's config (without `withAccessToken()`) for admin operations.

### Logout

```java
// Sign the user out of all the applications and devices (SSO logout)
authService.logout(accessToken);

// Only sign out the session of this access token
authService.logoutCurrentSession(accessToken);

// Or log out through the browser (OIDC RP-Initiated Logout), Casdoor redirects back to
// postLogoutRedirectUri, which must be in the application's Redirect URIs
String logoutUrl = authService.getLogoutUrl(idToken, postLogoutRedirectUri, state);
```

## 📦 Resource Management

### Object Owner

Every object in Casdoor is identified by an ID of the form `owner/name`, where the owner is an organization (`role`, `group`, `user`, `product`, `ldap`, ...) or the built-in `admin` owner (`organization`, `application`, `token`).

By default the SDK fills in the owner for you: the `organizationName` of the config, or `admin` for the object types listed above. You can address an object in another organization by passing a qualified `owner/name` ID instead of a plain name, and by setting the `owner` field explicitly when creating or updating an object:

```java
roleService.getRole("my-role");            // "my-organization/my-role"
roleService.getRole("other-org/my-role");  // "other-org/my-role"

Role role = new Role("other-org", "my-role", createdTime, "My Role", "");
roleService.addRole(role);                 // created in "other-org"
```

> [!IMPORTANT]
> **Behavior change:** `addXxx()`, `updateXxx()` and `deleteXxx()` used to overwrite the `owner` field of the object with the config's organization, and to ignore any owner set by the caller. They now only fill `owner` in when it is empty. If your code sets `owner` to a value other than the config's organization (for example the literal `"admin"`), the request is now sent to that owner instead of being silently redirected, so clear the field or set it to the intended organization.

### Method Patterns

Most services have the same methods:

- `getXxxs()` - get all the objects of the organization
- `getPaginationXxxs(p, pageSize, queryMap)` - get a page of the objects, returns a map with the objects and `data2`, the total count. `queryMap` can filter and sort, e.g. `field`, `value`, `sortField`, `sortOrder`
- `getXxx(name)` - get an object by name (or `owner/name` ID)
- `addXxx(object)` - create an object
- `updateXxx(object)` - update an object
- `updateXxxForColumns(object, columns...)` - only update the given columns (users, roles, permissions, sessions, tokens, invitations)
- `deleteXxx(object)` - delete an object

### Users

```java
UserService userService = new UserService(config);

userService.getUsers();
userService.getPaginationUsers(1, 10, null);
userService.getUser("alice");
userService.getUserByEmail("alice@example.com");
userService.getUserByPhone("2025550123");
userService.getUserByUserId("<user id>");
userService.getSortedUsers("created_time", 10);
userService.getGlobalUsers();         // users of all organizations
userService.getUserCount("1");        // "1" for online users, "0" for offline users, "" for all users

userService.addUser(user);
userService.updateUser(user);
userService.updateUserForColumns(user, "displayName", "email");
userService.updateUserById("my-organization/alice", user);
userService.updateUserByUserId("my-organization", "<user id>", user);
userService.deleteUser(user);

user.password = "123456";
userService.checkUserPassword(user);  // true or false
new AccountService(config).setPassword("alice", "123456", "654321");
```

### Permissions and Enforcement

```java
PermissionService permissionService = new PermissionService(config);
permissionService.getPermissionsByRole("admin");

EnforcerService enforcerService = new EnforcerService(config);
boolean allowed = enforcerService.enforce("my-organization/read-data", "", "", "", "",
        new Object[]{"my-organization/alice", "data1", "read"});
```

### Enforcers and Policies

```java
PolicyService policyService = new PolicyService(config);
Enforcer enforcer = enforcerService.getEnforcer("my-enforcer");

policyService.getPolicies("my-enforcer", "");
policyService.getFilteredPolicies("my-organization/my-enforcer", new PolicyFilter("p", 0, "alice"));
policyService.addPolicy(enforcer, policy);
policyService.updatePolicy(enforcer, oldPolicy, newPolicy);
policyService.removePolicy(enforcer, policy);
```

### Billing: Products, Orders, Payments and Transactions

```java
OrderService orderService = new OrderService(config);

// Place an order of products for a user and pay it with a payment provider
Order order = orderService.placeOrder(new ProductInfo[]{new ProductInfo("my-product", 1)}, "alice");
Payment payment = orderService.payOrder(order.name, "my-payment-provider");
orderService.cancelOrder(order.name);

orderService.getUserOrders("alice");
new PaymentService(config).getUserPayments("alice");
new TransactionService(config).getUserTransactions("alice");

// Validate a transaction (e.g. the balance) without saving it
new TransactionService(config).addTransactionWithDryRun(transaction, true);
```

### Email, SMS and Notifications

```java
new EmailService(config).sendEmail("Hello", "Hello world", "Casdoor", "alice@example.com");
new EmailService(config).sendEmailByProvider("Hello", "Hello world", "Casdoor", "my-email-provider", "alice@example.com");
new SmsService(config).sendSms("123456", "+12025550123");
new SmsService(config).sendSmsByProvider("123456", "my-sms-provider", "+12025550123");
new NotificationService(config).sendNotification("Hello", "alice");
```

### Resources (File Upload)

```java
ResourceService resourceService = new ResourceService(config);

// data is the file URL, data2 is the resource name
CasdoorResponse<String, Object> response = resourceService.uploadResource("alice", "avatar", "", "/avatar/alice.png", file);

resourceService.getResources("my-organization", "alice", "", "", "", "");
resourceService.getPaginationResources("my-organization", "alice", "", "", 10, 1, "", "");
resourceService.deleteResourceWithTag(resource, "Direct");
```

### LDAP

```java
LdapService ldapService = new LdapService(config);
ldapService.getLdaps();
ldapService.getLdapUsers("<ldap id>");
ldapService.syncLdapUsersFromServer("<ldap id>"); // fetch and sync all the LDAP users
```

### Multi-Factor Authentication

```java
MfaService mfaService = new MfaService(config);
java.util.Map<String, Object> setup = mfaService.initiate("my-organization", MfaService.APP, "alice").getData();
mfaService.verify("my-organization", MfaService.APP, "alice", (String) setup.get("secret"), "<passcode>");
mfaService.enable("my-organization", MfaService.APP, "alice", (String) setup.get("secret"), "<recovery code>");
mfaService.setPreferred("my-organization", MfaService.APP, "alice", "");
mfaService.delete("my-organization", "alice");
```

## 📚 API Reference

| Service                  | Methods                                                                                                                       |
|--------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| **AuthService**          | `getOAuthToken`, `getOAuthTokenByPassword`, `impersonateUser`, `refreshOAuthToken`, `parseJwtToken`, `getSigninUrl`, `getSignupUrl`, `getUserProfileUrl`, `getMyProfileUrl`, `getLogoutUrl`, `logout`, `logoutCurrentSession` |
| **UserService**          | CRUD + pagination, `getAccount`, `getUserByEmail`, `getUserByPhone`, `getUserByUserId`, `getSortedUsers`, `getGlobalUsers`, `getUserCount`, `updateUserForColumns`, `updateUserById`, `updateUserByUserId`, `checkUserPassword` |
| **AccountService**       | `setPassword`, `getAccount`                                                                                                   |
| **OrganizationService**  | CRUD, `getOrganizationNames`                                                                                                  |
| **ApplicationService**   | CRUD, `getOrganizationApplications`                                                                                           |
| **GroupService**         | CRUD + pagination                                                                                                             |
| **CertService**          | CRUD, `getGlobalCerts`                                                                                                        |
| **ProviderService**      | CRUD + pagination                                                                                                             |
| **RoleService**          | CRUD + pagination, `updateRoleForColumns`                                                                                     |
| **PermissionService**    | CRUD + pagination, `updatePermissionForColumns`, `getPermissionsByRole`                                                       |
| **ModelService / AdapterService / EnforcerService** | CRUD + pagination, `enforce`, `batchEnforce`                                                       |
| **PolicyService**        | `getPolicies`, `getFilteredPolicies`, `addPolicy`, `updatePolicy`, `removePolicy`                                             |
| **SessionService**       | CRUD + pagination, `updateSessionForColumns`                                                                                  |
| **TokenService**         | CRUD + pagination, `updateTokenForColumns`, `introspectToken`                                                                 |
| **ProductService**       | CRUD + pagination, `buyProduct`                                                                                               |
| **OrderService**         | CRUD + pagination, `getUserOrders`, `placeOrder`, `payOrder`, `cancelOrder`                                                   |
| **PaymentService**       | CRUD + pagination, `getUserPayments`, `notifyPayment`, `invoicePayment`                                                       |
| **PlanService / PricingService / SubscriptionService** | CRUD + pagination                                                                               |
| **TransactionService**   | CRUD + pagination, `getUserTransactions`, `addTransactionWithDryRun`                                                          |
| **InvitationService**    | CRUD + pagination, `updateInvitationForColumns`, `getInvitationInfo`                                                          |
| **LdapService**          | CRUD, `getLdapUsers`, `syncLdapUsers`, `syncLdapUsersFromServer`                                                              |
| **SyncerService / WebhookService** | CRUD + pagination                                                                                                   |
| **ResourceService**      | `getResources`, `getPaginationResources`, `getResource`, `getResourceEx`, `addResource`, `updateResource`, `uploadResource`, `uploadResourceEx`, `deleteResource`, `deleteResourceWithTag` |
| **RecordService**        | `getRecords`, `getPaginationRecords`, `getRecord`, `addRecord`                                                                |
| **EmailService / SmsService / NotificationService** | `sendEmail`, `sendEmailByProvider`, `sendSms`, `sendSmsByProvider`, `sendNotification`                  |
| **MfaService**           | `initiate`, `verify`, `enable`, `setPreferred`, `delete`                                                                      |

## 🛠 Development

The tests run against a real Casdoor server. CI starts one with Docker and the data in [.ci/casdoor/init_data.json](.ci/casdoor/init_data.json):

```bash
docker run -d --name casdoor -p 8000:8000 \
  -e driverName=sqlite \
  -e dataSourceName='file:casdoor.db?cache=shared' \
  -e initDataFile=/init_data.json \
  -v "$PWD/.ci/casdoor/init_data.json:/init_data.json:ro" \
  casbin/casdoor-all-in-one

mvn test
```

Set `CASDOOR_TEST_ENDPOINT`, `CASDOOR_TEST_CLIENT_ID`, `CASDOOR_TEST_CLIENT_SECRET`, `CASDOOR_TEST_ORGANIZATION` and `CASDOOR_TEST_APPLICATION` to run the tests against another server.

Releases are published to Maven Central automatically by semantic-release when commits are pushed to `master`.

## 📖 Documentation

- [Casdoor Documentation](https://casdoor.ai/docs/overview)
- [Casdoor Java SDK Documentation](https://casdoor.ai/docs/how-to-connect/sdk)
- [Casdoor API Documentation](https://door.casdoor.com/swagger)
- [Javadoc](https://www.javadoc.io/doc/org.casbin/casdoor-java-sdk)
- [Casdoor GitHub Repository](https://github.com/casdoor/casdoor)

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
