# casdoor-java-sdk

[![GitHub Actions](https://github.com/casdoor/casdoor-java-sdk/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/casdoor/casdoor-java-sdk/actions/workflows/maven-ci.yml)
[![codebeat badge](https://codebeat.co/badges/e3e92eff-8b71-4903-9764-5126e855b3b6)](https://codebeat.co/projects/github-com-casdoor-casdoor-java-sdk-master)
[![codecov](https://codecov.io/gh/casdoor/casdoor-java-sdk/branch/master/graph/badge.svg?token=1C2FSTN4J8)](https://codecov.io/gh/casdoor/casdoor-java-sdk)
[![Javadocs](https://www.javadoc.io/badge/org.casbin/casdoor-java-sdk.svg)](https://www.javadoc.io/doc/org.casbin/casdoor-java-sdk)
[![Maven Central](https://img.shields.io/maven-central/v/org.casbin/casdoor-java-sdk.svg)](https://mvnrepository.com/artifact/org.casbin/casdoor-java-sdk/latest)
[![Release](https://img.shields.io/github/release/casdoor/casdoor-java-sdk.svg)](https://github.com/casdoor/casdoor-java-sdk/releases/latest)
[![Discord](https://img.shields.io/discord/1022748306096537660?logo=discord&label=discord&color=5865F2)](https://discord.gg/5rPsrAzK7S)

This is Casdoor's SDK for java, which will allow you to easily connect your application to the Casdoor authentication system without having to implement it from scratch.

Casdoor SDK is very simple to use. We will show you the steps below.

## Step1. Init Config

Initialization requires 5 parameters, which are all string type:

| Name (in order)  | Must | Description                                         |
|------------------|------|-----------------------------------------------------|
| endpoint         | Yes  | Casdoor Server Url, such as `http://localhost:8000` |
| clientId         | Yes  | Client ID for the Casdoor application               |
| clientSecret     | Yes  | Client secret for the Casdoor application           |
| certificate      | Yes  | The public key for the Casdoor application's cert   |
| organizationName | Yes  | The name for the Casdoor organization               |
| applicationName  | No   | The name for the Casdoor application                |

```java
CasdoorConfig config = new CasdoorConfig(endpoint, clientId, clientSecret, certificate, organizationName, applicationName);
```

## Step2. Get Service and use

Now provide two services: ``CasdoorUserService``, ``CasdoorAuthService``

You can create them like

```Java
CasdoorUserService casdoorUserService = new CasdoorUserService(config);
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

```java
String token = authService.getOAuthToken(code, state);

CasdoorUser user = authService.parseJwtToken(token);
```

2. **Set Session in your app**

`user` contains the basic information about the user provided by casdoor, you can use it as a keyword to set the session in your application, like this:

```java
HttpSession session = request.getSession();
session.setAttribute("user", user);
```

## SpringBoot Support

If you use SpingBoot for your application, you can use [casdoor-spring-boot-starter](https://github.com/casdoor/casdoor-spring-boot-starter)
