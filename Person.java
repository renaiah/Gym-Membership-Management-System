
abstract class Person {
    private String name;
    private int age;

    public Person(String personName, int personAge) {
        this.name = personName;
        this.age = personAge;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    public abstract void showDetails();
}