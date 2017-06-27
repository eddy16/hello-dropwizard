package com.edcg.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: EmployeeDB <br>
 * Date: 27/06/17 <br>
 * <p>
 * TODO [Agregar documentación de la clase] <br>
 *
 * @author Edgar Cirilo González (ecirilo@legosoft.com.mx)<br>
 * @version 1.0 <br>
 */
public class EmployeeDB  {

    public static HashMap<Integer, Employee> employees = new HashMap<>();

    static{
        employees.put(1, new Employee(1, "Lokesh", "Gupta", "India"));
        employees.put(2, new Employee(2, "John", "Gruber", "USA"));
        employees.put(3, new Employee(3, "Melcum", "Marshal", "AUS"));
    }

    public static List<Employee> getEmployees(){
        return new ArrayList<Employee>(employees.values());
    }

    public static Employee getEmployee(Integer id){
        return employees.get(id);
    }

    public static void updateEmployee(Integer id, Employee employee){
        employees.put(id, employee);
    }

    public static void removeEmployee(Integer id){
        employees.remove(id);
    }
}
