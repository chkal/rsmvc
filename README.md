# RSMVC

RSMVC is a prototype of a MVC web framework based on JAX-RS. It is inspired by by
[Jersey MVC](https://jersey.java.net/documentation/latest/mvc.html),
[Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html),
[Htmleasy](http://code.google.com/p/htmleasy/) and
[VRaptor4](http://www.vraptor.org/).

## Contents

  * [Installation](#install)
  * [Basic page rendering](#basic-rendering)
  * [Preparing the model](#prepare-model)
  * [Declaring your view via annotations](#view-annotation)
  * [Processing form data](#form-data)
  * [Form Validation](#form-validation)

<a name="install"></a>
## Installation

As RSMVC is currently a prototype. So you will have to build the project yourself:

```
$ git clone https://github.com/chkal/rsmvc.git
$ cd rsmvc && mvn install
```

Now you can add the RSMVC dependency to your project:

```xml
<dependency>
    <groupId>de.chkal.rsmvc</groupId>
    <artifactId>rsmvc-core</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

RSMVC runs fine on any Java EE 7 compatible container like [Wildfly 8.2.0.Final](http://wildfly.org/).
If you want to use some plain Servlet container like Tomcat, you will have to add the following
required dependencies yourself:

  * A JAX-RS implementation (tested with RESTeasy)
  * A CDI implementation (Weld, OpenWebBeans, ...)
  * A Bean Validation implementation (Hibernate Validator, ...)

<a name="basic-rendering"></a>
## Basic page rendering

Any JAX-RS resource can act as your controller. Just return an instance of `ModelAndView` refering to a JSP
file to render.

```java
@Path("/helloworld")
public class HelloWorldController {

    @GET
    public ModelAndView execute() {
        return new ModelAndView("/helloworld.jsp");
    }

}
```

The JSP may look like this:

```xml
<html>
    <head>
        <title>RSMVC demo</title>
    </head>
    <body>
        Hello world!
    </body>
</html>
```

<a name="prepare-model"></a>
## Preparing the model

Typically you will need a model to render your view. You can use `with()` to add values to your model
in a fluent way. See this example:

```java
@Path("/hello")
public class HelloController {

    @GET
    @Path("/{name}")
    public ModelAndView execute(@PathParam("name") String name) {
        return new ModelAndView("/hello.jsp")
                .with("name", name);
    }

}
```

You can access the model values in your JSP using EL expressions:

```xml
<html>
    <head>
        <title>RSMVC Demo</title>
    </head>
    <body>
        Hello ${name}!
    </body>
</html>
```

<a name="view-annotation"></a>
## Declaring your view via annotations

Instead of using `ModelAndView` to tell RSMVC about the view you want to render you can also add the
annotation `@View` to your resource method (or resource class). If you use `@View`, you should return `Model`
instead of `ModelAndView` from your resource method:

```java
@Path("/hello")
public class HelloController {

    @GET
    @Path("/{name}")
    @View("/hello.jsp")
    public Model execute(@PathParam("name") String name) {
        return new Model()
                .with("name", name);
    }

}
```

<a name="form-data"></a>
## Processing form data

You can use the standard JAX-RS API for processing form data. Typically you should create
a separate class for your form data and annotate the fields with `@FormParam`.

Take this JSP file containing a form as an example:

```xml
<html>
    <head>
        <title>Test</title>
    </head>
    <body>
        <form action="${pageContext.servletContext.contextPath}/form" method="post">
            <p>
                <label for="firstname">Firstname:</label>
                <input id="firstname" name="firstname" />
            </p>
            <p>
                <input type="submit" value="Submit" />
            </p>
        </form>
    </body>
</html>
```

A form class should have fields annotated with `@FormParam` to capture the submitted data.
For the example shown above the form class could look like this:

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

A controller processing the form submissions should declare an instance of the form class
as a resource method parameter annotated with `@BeanParam`:

```java
@Path("/form")
public class FormController {

    @POST
    @View("/form.jsp")
    public Model post(@BeanParam SimpleForm form) {

        String message = "Hello " + form.getFirstname();

        // more code

    }

}
```

Easy, isn't it. :)

<a name="form-validation"></a>
## Form Validation

RSMVC uses Bean Validation for validating forms. Unfortunately the JAX-RS way of integrating with
Bean Validation doesn't work well for MVC applications. So RSMVC provides it's own way for validation
of form data.

You can add the constraint annotations directly to the fields of your form class:

```java
public class ValidationForm {

    @FormParam("firstname")
    @Size(min = 3, message = "The firstname must have at least 3 characters")
    public String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

        validator.validate(form).onError().render();

        // business code that will only be executed if validation passes

    }

}
```

Please note that you must **NOT** add `@Valid` to the resource method parameter, because this would
trigger the default JAX-RS validation.

In case of validation errors the execution of the resource method will be aborted
and the view declared via `@View` will be rendered. The corresponding `ConstraintViolations`
will be stored in the model using the key `errors`. So you can render the validation
errors in the JSP page like this:

```xml
<c:if test="${ not empty errors }">
    <ul>
        <c:forEach var="error" items="${errors}">
            <li>${error.message}</li>
        </c:forEach>
    </ul>
</c:if>
```

See the following examples for how to use the fluent validation API:

```java
// abort processing and render the view in case of validation errors
validator.validate(form).onError().render();

// shortcut for the version above
validator.onError(form).render();

// check if there are validation errors
boolean failed = validator.validate(form).hasErrors();

// render a different view in case of errors
validator.onError(form).render("/other-view.jsp");

// add other values to the model before rendering
validator.onError(form)
        .with("someKey", someValue)
        .render();

// use some other model for rendering
validator.onError(form)
        .with(baseModel)
        .render();
```

Especially the last example is very common as you typically have a base model which you will always
need to render the page. You can then use this base model when rerendering the page in case of
validation errors AND if you rerender the page after successful processing of the submission.

See this common usage example:

```java
@Path("/form")
public class ValidationController {

    @Inject
    private FormValidator validator;

    @POST
    @View("/validation.jsp")
    public Viewable post(@BeanParam ValidationForm form) {

        Model baseModel = new Model()
                .with("title", "Hello world");

        validator.onError(form)
                .with(baseModel)
                .render();

        // your business code here

        return baseModel
                .with("msg", "The submission has been processed");

    }

}
```

In case of validation errors the model used for rendering will contain:

  * `title`
  * `errors`

If no validation errors were detected and the business code was successfully executed, the model
will contain:

  * `title`
  * `msg`

Please note that I18N is currently on the roadmap.