## JREST client

[![CircleCI](https://circleci.com/gh/Kortex/jrest-client.svg?style=svg)](https://circleci.com/gh/Kortex/jrest-client)

[![Known Vulnerabilities](https://snyk.io/test/github/Kortex/simple-rest-client/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/Kortex/simple-rest-client?targetFile=build.gradle)

The following project acts as a simple abstraction wrapping calls to Java's `HttpClient` and exposing a simple and easy
to use API offering methods to perform RESTful API calls.

The project uses only a few external dependencies to perform its job and relies heavily on the standard JDK offered `HttpClient`.

For more regarding this you can refer to the following documentation links:

* [OpenJDK - Introduction to the HTTP Client](https://openjdk.java.net/groups/net/httpclient/intro.html)
* [JDK Documentation - HttpClient](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html)

## Getting started

### Prerequisites

To build and test the project you will need to have the following in place:

* [AdoptOpenJDK 11 (11.0.5+10)](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=openj9) - Preferably with the OpenJ9 VM

### Building the project

To build the project just run the following command:

```shell script
./gradlew clean assemble
```

### Running unit tests

To run the project's unit tests just run the following command:

```shell script
./gradlew test 
```

### Running integration tests

To run the project's integration tests just run the following command:

```shell script
./gradlew integrationTest
```

### Using the RestClient

Using the RestClient is a simple and straightforward procedure. The first process involves around obtaining the RestClient instance
via:

```java
RestClient.getInstance()
```

Upon having a reference to the instance you can perform API calls with the following HTTP methods:

* `GET`
* `POST`
* `PUT`
* `DELETE`
* `PATCH`

Each of the above HTTP methods is represented via a method accepting various parameters and returning a `RestResponse` object
containing the expected response, error as well as the two fields describing the HTTP code and status of the response.

The belo listed example show 

#### Performing a GET request

To perform mapping the response to a custom POJO is very easy and can be done in the following manner:

* Given the following POJO classes:

```java
public class SingleUserResource {

    private UserResource data;
    public UserResource getData() { return data; }
    public void setData(UserResource data) { this.data = data; }

    @Override
    public String toString() {
	return "SingleUserResource{" +
	    "data=" + data +
	    '}';
    }

}
```

and

```java
public class UserResource {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @JsonProperty("first_name")
    private String firstName;
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @JsonProperty("last_name")
    private String lastName;
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    private String avatar;
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    @Override
    public String toString() {
        return "UserResource{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", avatar='" + avatar + '\'' +
            '}';
    }

}
```

Calling an endpoint and obtaining a response can be done easily in the following manner:

```java
var response = RestClient
    .getInstance()
    .GET("https://reqres.in/api/users/2", SingleUserResource.class, Void.class, Collections.emptyMap());
```

The above will result in the following `RestResponse` object being returned:

```text
RestResponse{code=200, 
    isSuccess=true, 
    response=SingleUserResource{
        data=UserResource{
            id=2, 
            email='janet.weaver@reqres.in', 
            firstName='Janet', 
            lastName='Weaver', 
             avatar='https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg'
        }
    }, error=null
}
```

#### Performing a POST request:

Performing a POST request is equally easy and straightforward as performing a GET request. Thus given the following POJO one can
simply do:

Request POJO:

```java
public class UserModificationResource {

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String job;
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    @Override
    public String toString() {
        return "UserModificationResource{" +
            "name='" + name + '\'' +
            ", job='" + job + '\'' +
            '}';
    }

}
```
Response POJO:

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModificationResponse {

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String job;
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Date createdAt;
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    private Date updatedAt;
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
	return "UserModificationResponse{" +
	    "name='" + name + '\'' +
	    ", job='" + job + '\'' +
	    ", id=" + id +
	    ", createdAt=" + createdAt +
	    ", updatedAt=" + updatedAt +
	    '}';
    }


```

A POST call can be done as easily as:

```java
var restResponse = RestClient
    .getInstance()
    .POST("https://reqres.in/api/users", payload, UserModificationResponse.class, Void.class, Collections.emptyMap());
```

Usage of the client is the same for other HTTP methods as well (`PUT`, `DELETE`, `PATCH`).
