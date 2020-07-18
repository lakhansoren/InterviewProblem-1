package main.machine;

public class Outlets {
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Outlets{");
        sb.append("number=").append(number);
        sb.append('}');
        return sb.toString();
    }
}
