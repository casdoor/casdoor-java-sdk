# casdoor-java-sdk

This is Casdoor's SDK for java, which will allow you to easily connect your application to the Casdoor authentication system without having to implement it from scratch.

Casdoor SDK is very simple to use. We will show you the steps below.

## Step1. Init Config

Initialization requires 5 parameters, which are all string type:

| Name (in order)  | Must | Description                                         |
| ---------------- | ---- | --------------------------------------------------- |
| endpoint         | Yes  | Casdoor Server Url, such as `http://localhost:8000` |
| clientId         | Yes  | Application.client_id                               |
| clientSecret     | Yes  | Application.client_secret                           |
| jwtSecret        | Yes  | Same as Casdoor JWT secret.                         |
| organizationName | Yes  | Application.organization                            |

```go
CasdoorConfig casdoorConfig = new CasdoorConfig(endpoint, clientId, clientSecret, jwtSecret, organizationName);
```

## Step2. Get Service and use

Now provide two services: ``CasdoorUserService``, ``CasdoorAuthService``

You can create them like

```Java
CasdoorUserService casdoorUserService = new CasdoorUserService(casdoorConfig);
```

## UserService

``CasdoorUserService`` support basic user operations, like:

- `GetUser(name string)`, get one user by user name.
- `GetUsers()`, get all users.
- `UpdateUser(auth.User)/AddUser(auth.User)/DeleteUser(auth.User)`, write user to database.

## AuthService

1. **Get token and parse**

After casdoor verification passed, it will be redirected to your application with code and state, like `http://forum.casbin.org?code=xxx&state=yyyy`.

Your web application can get the `code`,`state` and call `GetOAuthToken(code, state)`, then parse out jwt token.

The general process is as follows:

```go
token = casdoorAuthService.getOAuthToken(code, state)

casdoorUser = casdoorAuthService.parseJwtToken(token)
```

2. **Set Session in your app**

`casdoorUser` contains the basic information about the user provided by casdoor, you can use it as a keyword to set the session in your application, like this:

```go
req.getSession.setAttribute("user", casdoorUser)
```

## SpringBoot Support

If you use SpingBoot for your application, you can use [casdoor-spring-boot-starter](https://github.com/casdoor/casdoor-spring-boot-starter)

