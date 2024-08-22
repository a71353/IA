import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Triple implements Ilayout, Cloneable {

    int number;
    int cost;

    public Triple(int number, int cost) throws IllegalStateException {
        this.number = number;
        this.cost = cost;
    }

    public boolean equals(Object o) {
        Triple obj = (Triple) o;
        return this.number == obj.number;
    }


    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();

        Triple add = new Triple(number + 1, 1);
        children.add(add);

        Triple sub = new Triple(number - 1, 2);
        children.add(sub);

        Triple mult = new Triple(number * 2, 3);
        children.add(mult);

        return children;
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }


    @Override
    public int getG() {
        return this.cost;
    }

    public String toString() {
        return String.valueOf(number);
    }

}
