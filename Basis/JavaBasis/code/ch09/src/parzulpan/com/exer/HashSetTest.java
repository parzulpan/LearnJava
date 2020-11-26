package parzulpan.com.exer;

import java.util.HashSet;
import java.util.Objects;

/**
 * @Author : parzulpan
 * @Time : 2020-11-26
 * @Desc :
 */

public class HashSetTest {
    public static void main(String[] args) {
        HashSet set = new HashSet();
        PersonA p1 = new PersonA(1001,"AA");
        PersonA p2 = new PersonA(1002,"BB");
        set.add(p1);
        set.add(p2);
        p1.name = "CC";
        set.remove(p1);
        System.out.println(set);  // [PersonA{id=1002, name='BB'}, PersonA{id=1001, name='CC'}]
        set.add(new PersonA(1001,"CC"));
        System.out.println(set);  // [PersonA{id=1002, name='BB'}, PersonA{id=1001, name='CC'}, PersonA{id=1001, name='CC'}]
        set.add(new PersonA(1001,"AA"));
        System.out.println(set);  // [PersonA{id=1002, name='BB'}, PersonA{id=1001, name='CC'}, PersonA{id=1001, name='CC'}, PersonA{id=1001, name='AA'}]
    }
}

class PersonA {
    public int id;
    public String name;

    public PersonA() {
    }

    public PersonA(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonA)) return false;

        PersonA personA = (PersonA) o;

        if (id != personA.id) return false;
        return Objects.equals(name, personA.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}