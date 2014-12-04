# RSMVC

RSMVC is a prototype of a MVC web framework based on JAX-RS.

## Basic page rendering

Any JAX-RS resource can act as your controller. Just return an instance of `ModelAndView` refering to a JSP
file to render.

```java
@Path("/helloworld")
public class SimpleController {

    @GET
    public ModelAndView execute() {
        return new ModelAndView("/helloworld.jsp");
    }

}
```

The JSP may look like this:

```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Test</title>
    </head>
    <body>
        Hello World!
    </body>
</html>
```

## Passing a model

Typically you will need a model to render your view. You can use `with()` to add values to your model
in a fluent way.

```java
@Path("/hello")
public class SimpleController {

    @GET
    @Path("/{name}")
    public ModelAndView execute(@PathParam("name") String name) {
        return new ModelAndView("/hello.jsp")
                .with("name", name);
    }

}
```

You can then access the model in your JSP using EL expressions:

```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Test</title>
    </head>
    <body>
        Hello ${name}!
    </body>
</html>
```

## Declaring your view via annotations

Instead of passing the view name into the constructor of `ModelAndView` you can also add the
annotation `@View` to your resource method or class. When using `@View` you can return `Model`
instead of `ModelAndView` from your resource method:

```java
@Path("/hello")
public class SimpleController {

    @GET
    @Path("/{name}")
    @View("/helloworld.jsp")
    public Model execute(@PathParam("name") String name) {
        return new Model()
                .with("name", name);
    }

}
```

## Processing form data

You can use the standard JAX-RS API for processing form data. Typically you will have to create
a class for your form data and annotate the fields with `@FormParam`.

Take this JSP file containg a form as an example:

```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Test</title>
    </head>
    <body>
        <form action="${pageContext.servletContext.contextPath}/form" method="post">
            <p>
                <label for="firstname">Firstname: </label>
                <input id="firstname" name="firstname" placeholder="Your name" />
            </p>
            <p>
                <input type="submit" value="Submit" />
            </p>
        </form>
    </body>
</html>
```

In this simple example the form class could look like this:

```java
public class SimpleForm {

    @FormParam("firstname")
    public String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

}
```

A controller processing the form submissions could look like this:

```java
@Path("/form")
@View("/form.jsp")
public class FormController {

    @GET
    public Model get() {
        return new Model();
    }

    @POST
    public Model post(@BeanParam SimpleForm form) {

        String message = "Hello " + form.getFirstname();

        // more code

    }

}
```

Easy, isn't it. :)

## Validation

RSMVC integrates with Bean Validation for validating forms. You will typically add the constraint annotations
directly to the fields of your form class:

```java
public class ValidationForm {

    @FormParam("name")
    @Size(min = 3, message = "The name must have at least 3 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

To validate the form data in your resource method, inject an instance of `FormValidator` and use it's
fluent API to describe what should happen if the validation fails.

```java
@Path("/form")
public class ValidationController {

    @Inject
    private FormValidator validator;

    @POST
    @View("/form.jsp")
    public Model post(@BeanParam ValidationForm form) {

        validator.onError(form).render();

        // some business code that will only be executed
        // if the validation of the form passes.


    }

}
```

In case of validation errors the execution of the resource method will be aborted
and the view declared via `@View` will be rendered. The validation errors will be available
via `${errors}` in the JSP page. So you can render them like this:

```xml
<c:if test="${ not empty errors}">
    <ul style="color: red;">
        <c:forEach var="error" items="${errors}">
            <li>${error.message}</li>
        </c:forEach>
    </ul>
</c:if>
```



