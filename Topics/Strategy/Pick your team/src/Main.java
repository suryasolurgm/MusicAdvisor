import java.util.Scanner;

class SelectionContext {

    private PersonSelectionAlgorithm algorithm;

    public void setAlgorithm(PersonSelectionAlgorithm algorithm) {
        // write your code here
        this.algorithm = algorithm;
    }

    public Person[] selectPersons(Person[] persons) {
        // write your code here
       return algorithm.select(persons);
    }
}

interface PersonSelectionAlgorithm {

    Person[] select(Person[] persons);
}

class TakePersonsWithStepAlgorithm implements PersonSelectionAlgorithm {
    int step;
    int length;
    public TakePersonsWithStepAlgorithm(int step) {
        // write your code here
        this.step =step;
    }

    @Override
    public Person[] select(Person[] persons) {
        // write your code here
        this.length=persons.length%step ==0 ? persons.length/step : persons.length/step +1 ;
        Person[] newPersons = new Person[length];
        int j=0;
        for(int i=0;i<persons.length;i++){
            if(i%step==0){
                newPersons[j++]=persons[i];
            }
        }
        return newPersons;
    }
}


class TakeLastPersonsAlgorithm implements PersonSelectionAlgorithm {
    int count;
    int length;
    public TakeLastPersonsAlgorithm(int count) {
        // write your code here
        this.count = count;
    }

    @Override
    public Person[] select(Person[] persons) {
        // write your code here
        if(count==1){
            length=1;
        }else {
            length=count;
        }
        Person[] newPersons = new Person[length];
        int j=0;
        for(int i= persons.length-count;i<persons.length;i++){
            newPersons[j++]=persons[i];
        }
        return newPersons;
    }
}

class Person {

    String name;

    public Person(String name) {
        this.name = name;
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final int count = Integer.parseInt(scanner.nextLine());
        final Person[] persons = new Person[count];

        for (int i = 0; i < count; i++) {
            persons[i] = new Person(scanner.nextLine());
        }

        final String[] configs = scanner.nextLine().split("\\s+");

        final PersonSelectionAlgorithm alg = create(configs[0], Integer.parseInt(configs[1]));
        SelectionContext ctx = new SelectionContext();
        ctx.setAlgorithm(alg);

        final Person[] selected = ctx.selectPersons(persons);
        for (Person p : selected) {
            System.out.println(p.name);
        }
    }

    public static PersonSelectionAlgorithm create(String algType, int param) {
        switch (algType) {
            case "STEP": {
                return new TakePersonsWithStepAlgorithm(param);
            }
            case "LAST": {
                return new TakeLastPersonsAlgorithm(param);
            }
            default: {
                throw new IllegalArgumentException("Unknown algorithm type " + algType);
            }
        }
    }
}