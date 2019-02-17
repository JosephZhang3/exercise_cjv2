class EmployeeTest
{
    public static void main(String[] args)
    {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("Harry",35000);
        staff[1] = new Employee("Carl",75000);
        staff[2] = new Employee("Tony",49000);

        for(Employee e : staff)
            e.raiseSalary(10);
        for(Employee e : staff)
            e.print();
    }
}
