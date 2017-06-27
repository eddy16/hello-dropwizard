package com.edcg.employee;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

/**
 * ClassName: EmployeeRestController <br>
 * Date: 27/06/17 <br>
 * <p>
 * TODO [Agregar documentación de la clase] <br>
 *
 * @author Edgar Cirilo González (ecirilo@legosoft.com.mx)<br>
 * @version 1.0 <br>
 */
@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeRestController {

    private final Validator validator;

    public EmployeeRestController(Validator validator) {
        this.validator = validator;
    }

    @GET
    public Response getEmployees() {
        return Response.ok(EmployeeDB.getEmployees()).build();
    }

    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeDB.getEmployee(id);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createEmployee(Employee employee) throws URISyntaxException {
        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Employee e = EmployeeDB.getEmployee(employee.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<Employee> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            EmployeeDB.updateEmployee(employee.getId(), employee);
            return Response.created(new URI("/employees/" + employee.getId()))
                    .build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {
        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Employee e = EmployeeDB.getEmployee(employee.getId());
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Employee> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(validationMessages).build();
        }
        if (e != null) {
            employee.setId(id);
            EmployeeDB.updateEmployee(id, employee);
            return Response.ok(employee).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeEmployeeById(@PathParam("id") Integer id) {
        Employee employee = EmployeeDB.getEmployee(id);
        if (employee != null) {
            EmployeeDB.removeEmployee(id);
            return Response.ok().build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}
